package com.etasdemir.ethinspector.data.domain_model

import android.annotation.SuppressLint
import android.content.Context

data class User(
    val installationId: String,
    var theme: AvailableThemes = AvailableThemes.Light,
    var language: AvailableLanguages = AvailableLanguages.Turkish
)

enum class AvailableThemes(val code: String) {
    Dark("dark"),
    Light("light")
}

@SuppressLint("DiscouragedApi")
enum class AvailableLanguages(val iso639Code: String) {
    English("en"),
    Turkish("tr");

    fun getLocalizedName(context: Context): String {
        val resId = context.resources.getIdentifier(
            this.iso639Code,
            "string",
            context.packageName
        )
        return context.getString(resId)
    }

    fun getIndex(): Int {
        return AvailableLanguages.values().indexOf(this)
    }

    companion object {
        fun getAvailableLocalizedNames(context: Context): List<String> {
            val res = context.resources
            val packageName = context.packageName
            return AvailableLanguages.values().map {
                val resId = res.getIdentifier(it.iso639Code, "string", packageName)
                context.getString(resId)
            }
        }
    }
}
