package com.etasdemir.ethinspector.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.*
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Installation @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var sID: String? = null
    private val INSTALLATION = "INSTALLATION"

    @Synchronized
    fun id(): String {
        if (sID == null) {
            val installation = File(context.filesDir, INSTALLATION)
            sID = try {
                if (!installation.exists()) writeInstallationFile(installation)
                readInstallationFile(installation)
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
        return sID!!
    }

    @Throws(IOException::class)
    private fun readInstallationFile(installation: File): String? {
        val f = RandomAccessFile(installation, "r")
        val bytes = ByteArray(f.length().toInt())
        f.readFully(bytes)
        f.close()
        return String(bytes)
    }

    @Throws(IOException::class)
    private fun writeInstallationFile(installation: File) {
        val out = FileOutputStream(installation)
        val id = UUID.randomUUID().toString()
        out.write(id.toByteArray())
        out.close()
    }
}