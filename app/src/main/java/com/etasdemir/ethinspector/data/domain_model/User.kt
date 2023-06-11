package com.etasdemir.ethinspector.data.domain_model

data class User(
    val installationId: String,
    var theme: AvailableThemes = AvailableThemes.Light,
    var language: AvailableLanguages = AvailableLanguages.Turkish
)

enum class AvailableThemes(val code: String) {
    Dark("dark"),
    Light("light")
}

enum class AvailableLanguages(val iso639Code: String) {
    English("en"),
    Turkish("tr")
}
