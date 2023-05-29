package com.etasdemir.ethinspector.data.remote

import com.etasdemir.ethinspector.data.ResponseResult
import com.etasdemir.ethinspector.data.remote.entity.blockchair.AddressResponse
import com.etasdemir.ethinspector.data.remote.entity.blockchair.BlockchairResponse
import com.squareup.moshi.*
import okhttp3.ResponseBody
import org.json.JSONObject


object CustomResponseParser {

    /**
     * Blockchair address endpoints return dynamic keys. Erase dynamic keys.
     * example: {"0x3282791d6fd713f1e94f4bfd565eaa78b3a0599d": values}
     * */
    fun parseBlockchairAddressResponse(response: String): String? {
        val root = JSONObject(response)
        val data = root.getJSONObject("data")
        val dynamicKey = data.names()?.get(0) as String?
        dynamicKey?.let {
            val addressResponse = data.getJSONObject(it)
            data.remove(dynamicKey)
            root.put("data", addressResponse)
            return root.toString()
        }
        return null
    }

    /**
     * Take ResponseResult<JSON String> as parameter and convert it to Kotlin Object.
     * */
    inline fun <reified T> addressJsonConverter(responseBodyResult: ResponseResult<ResponseBody>)
            : ResponseResult<BlockchairResponse<AddressResponse<T>>> {
        if (responseBodyResult is ResponseResult.Success && responseBodyResult.data != null) {
            val json = responseBodyResult.data.string()
            return try {
                val moshi: Moshi = Moshi.Builder().build()
                val addressType =
                    Types.newParameterizedType(AddressResponse::class.java, T::class.java)
                val types = Types.newParameterizedType(BlockchairResponse::class.java, addressType)
                val jsonAdapter: JsonAdapter<BlockchairResponse<AddressResponse<T>>> =
                    moshi.adapter(types)
                val obj = jsonAdapter.fromJson(json)
                if (obj != null) {
                    ResponseResult.Success(obj)
                } else {
                    ResponseResult.Error("Error while parsing address json. Returned null.")
                }
            } catch (exception: Exception) {
                ResponseResult.Error("Unexpected error at: ${exception.stackTraceToString()}")
            }
        } else {
            return ResponseResult.Error(
                responseBodyResult.errorMessage
                    ?: "Error at: addressJsonConverter<${T::class.java}>"
            )
        }
    }
}