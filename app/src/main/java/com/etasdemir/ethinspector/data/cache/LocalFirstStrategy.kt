package com.etasdemir.ethinspector.data.cache

import com.etasdemir.ethinspector.data.ResponseResult

/**
 * Response = T, Entity = K, Domain = V
 * */
class LocalFirstStrategy<T, K, V>(
    private val responseToDomain: (ResponseResult<T>) -> ResponseResult<V>,
    private val localToDomain: (K) -> V,
    private val domainToLocal: (V) -> K,
) : CacheStrategy<T, K, V>(responseToDomain, localToDomain, domainToLocal) {

    override suspend fun execute(
        fetchFromService: suspend () -> ResponseResult<T>,
        fetchFromLocal: suspend () -> K?,
        persistResponse: suspend (K) -> Unit
    ): ResponseResult<V> {
        val persistedObject = fetchFromLocal()
        val response = fetchFromService()
        return if ((persistedObject is List<*> && persistedObject.isNotEmpty()) ||
            (persistedObject !is List<*> && persistedObject != null)
        ) {
            val persistedDomainObj = localToDomain(persistedObject)
            updateLocal(response, persistResponse)
            ResponseResult.Success(persistedDomainObj)
        } else {
            if (response is ResponseResult.Success && response.data != null) {
                val domainObjectResult = responseToDomain(response)
                val domainObject = domainObjectResult.data!!
                persistResponse(domainToLocal(domainObject))
                ResponseResult.Success(domainObject)
            } else {
                ResponseResult.Error(response.errorMessage!!)
            }
        }
    }

    private suspend fun updateLocal(
        response: ResponseResult<T>,
        persistResponse: suspend (K) -> Unit
    ) {
        if (response is ResponseResult.Success && response.data != null) {
            val domainObj = responseToDomain(response).data!!
            val entity = domainToLocal(domainObj)
            persistResponse(entity)
        }
    }

}