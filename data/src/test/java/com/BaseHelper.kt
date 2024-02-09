package com

import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.StringWriter

object BaseHelper {
    private val gson = Gson()
    fun getJsonFile(fileName: String): String {
        val inputStream =
            this::class.java.classLoader?.getResourceAsStream(fileName)
        return getStringFromInputStream(inputStream)
    }

    @Throws(IOException::class)
    private fun getStringFromInputStream(stream: InputStream?): String {
        var n = 0
        val buffer = CharArray(1024 * 4)
        val reader = InputStreamReader(stream, "UTF8")
        val writer = StringWriter()
        while (-1 != reader.read(buffer).also { n = it }) writer.write(buffer, 0, n)
        return writer.toString()
    }

    fun <T> convertJsonToModel(jsonString: String, classT: Class<T>): T {
        return gson.fromJson(jsonString, classT)
    }
}
