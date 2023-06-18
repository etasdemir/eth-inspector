package com.etasdemir.ethinspector.mappers.domain_to_local

import com.etasdemir.ethinspector.data.domain_model.User
import com.etasdemir.ethinspector.data.local.entity.UserEntity

fun mapUserToEntity(user: User): UserEntity {
    return UserEntity(
        user.installationId,
        user.theme.code,
        user.useSystemTheme,
        user.language.iso639Code,
        user.useSystemLanguage,
    )
}