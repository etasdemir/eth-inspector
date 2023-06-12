package com.etasdemir.ethinspector.data.cache

import com.etasdemir.ethinspector.data.ResponseResult

/**
 * Response = T, Entity = K, Domain = V
 * */
class RemoteFirstStrategy<T, K, V>(
    private val responseToDomain: (ResponseResult<T>) -> ResponseResult<V>,
    private val localToDomain: (K) -> V,
    private val domainToLocal: (V) -> K,
) : CacheStrategy<T, K, V>(responseToDomain, localToDomain, domainToLocal) {

    override suspend fun execute(
        fetchFromService: suspend () -> ResponseResult<T>,
        fetchFromLocal: suspend () -> K?,
        persistResponse: suspend (K) -> Unit
    ): ResponseResult<V> {
        val response = fetchFromService()
        return if (response is ResponseResult.Success && response.data != null) {
            val domainObjectResult = responseToDomain(response)
            val domainObject = domainObjectResult.data!!
            persistResponse(domainToLocal(domainObject))
            ResponseResult.Success(domainObject)
        } else {
            val persistedObject = fetchFromLocal()
            if (persistedObject != null) {
                val persistedDomainObj = localToDomain(persistedObject)
                ResponseResult.Success(persistedDomainObj)
            } else {
                ResponseResult.Error(response.errorMessage!!)
            }
        }
    }
}