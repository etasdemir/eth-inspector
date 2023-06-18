package com.etasdemir.ethinspector.mappers.local_to_domain

import com.etasdemir.ethinspector.data.domain_model.*
import com.etasdemir.ethinspector.data.local.entity.UserEntity
import timber.log.Timber

fun mapUserEntityToDomain(userEntity: UserEntity): User? {
    return try {
        User(
            userEntity.installationId,
            AvailableThemes.getFromCode(userEntity.theme)!!,
            userEntity.useSystemTheme,
            AvailableLanguages.getFromISOCode(userEntity.language)!!,
            userEntity.useSystemLanguage,
        )
    } catch (ex: NullPointerException) {
        Timber.e("Either language or theme code saved wrong. ${ex.stackTraceToString()}")
        null
    }
}