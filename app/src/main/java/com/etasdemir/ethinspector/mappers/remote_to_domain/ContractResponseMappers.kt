package com.etasdemir.ethinspector.mappers.remote_to_domain

import com.etasdemir.ethinspector.data.ResponseResult
import com.etasdemir.ethinspector.data.domain_model.*
import com.etasdemir.ethinspector.data.remote.service.BlockchairContractResponse

fun mapContractResponseToContract(response: ResponseResult<BlockchairContractResponse>): ResponseResult<Contract> {
    val responseData = response.data
    val data = responseData?.data
    if (response is ResponseResult.Success && responseData != null && data != null) {
        val transactions = arrayListOf<AddressTransaction>()
        data.calls?.forEach {
            transactions.add(
                AddressTransaction(
                    it.transactionHash,
                    it.value,
                    it.blockId,
                    it.time
                )
            )
        }

        val address = data.address
        val contractDetails = address.contractDetails
        val contractInfo = ContractInfo(
            contractDetails.creatingAddress,
            contractDetails.creatingTime,
            address.transactionCount,
            address.balanceWei,
            address.balanceUsd
        )

        val contract = Contract(contractInfo, transactions)
        return ResponseResult.Success(contract)
    } else {
        val errorMessage = response.errorMessage ?: "Error at mapContractResponseToState"
        return ResponseResult.Error(errorMessage)
    }
}