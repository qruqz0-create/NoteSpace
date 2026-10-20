package com.example.notespace

import android.content.Context
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object LogHelper {

    private const val FILE_NAME = "logs.txt"

    fun writeLog(
        context: Context,
        tag: String,
        message: String
    ) {
        val dateFormat = SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss",
            Locale.getDefault()
        )

        val date = dateFormat.format(Date())
        val logMessage = "$date [$tag] $message\n"

        try {
            context.openFileOutput(
                FILE_NAME,
                Context.MODE_APPEND
            ).use { output ->
                output.write(logMessage.toByteArray(Charsets.UTF_8))
            }

            Log.d(tag, message)
        } catch (e: Exception) {
            Log.e(tag, "Ошибка записи в logs.txt", e)
        }
    }
}
