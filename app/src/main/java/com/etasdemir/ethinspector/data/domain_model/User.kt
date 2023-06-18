package com.etasdemir.ethinspector.data.domain_model

import android.annotation.SuppressLint
import android.content.Context

data class User(
    val installationId: String,
    var theme: AvailableThemes,
    var useSystemTheme: Boolean,
    var language: AvailableLanguages,
    var useSystemLanguage: Boolean
)

@SuppressLint("DiscouragedApi")
enum class AvailableThemes(val code: String) {
    Dark("dark"),
    Light("light");

    fun getLocalizedName(context: Context): String {
        val resId = context.resources.getIdentifier(
            "theme_${this.code}",
            "string",
            context.packageName
        )
        return context.getString(resId)
    }

    fun getIndex(): Int {
        return AvailableThemes.values().indexOf(this)
    }

    companion object {
        fun getAvailableLocalizedNames(context: Context): List<String> {
            val res = context.resources
            val packageName = context.packageName
            return AvailableThemes.values().map {
                val resId = res.getIdentifier("theme_${it.code}", "string", packageName)
                context.getString(resId)
            }
        }

        fun getFromCode(code: String): AvailableThemes? {
            val filtered = AvailableThemes.values().filter {
                it.code == code
            }
            return if (filtered.size == 1) {
                filtered.first()
            } else {
                null
            }
        }
    }
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

        fun getFromISOCode(iso639Code: String): AvailableLanguages? {
            val filtered = AvailableLanguages.values().filter {
                it.iso639Code == iso639Code
            }
            return if (filtered.size == 1) {
                filtered.first()
            } else {
                null
            }
        }
    }
}
