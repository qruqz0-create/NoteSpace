package com.example.notespace

import android.content.Context
import org.json.JSONArray

object NoteStorage {

    private const val PREFS_NAME = "notes_preferences"
    private const val NOTES_KEY = "saved_notes"

    fun getNotes(context: Context): ArrayList<String> {
        val preferences = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )

        val savedData = preferences.getString(NOTES_KEY, null)
        val notes = ArrayList<String>()

        if (savedData == null) {
            notes.add("Учёба\nМои учебные задачи и планы")
            notes.add("Мои идеи\nМысли, которые хочу сохранить")
            notes.add("Планы на неделю")
            notes.add("Покупки")

            saveNotes(context, notes)
        } else {
            try {
                val jsonArray = JSONArray(savedData)

                for (i in 0 until jsonArray.length()) {
                    notes.add(jsonArray.getString(i))
                }
            } catch (e: Exception) {
                LogHelper.writeLog(
                    context,
                    "NoteStorage",
                    "Ошибка загрузки заметок: ${e.message}"
                )
            }
        }

        return notes
    }

    fun saveNotes(context: Context, notes: ArrayList<String>) {
        try {
            val jsonArray = JSONArray()

            for (note in notes) {
                jsonArray.put(note)
            }

            val preferences = context.getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
            )

            preferences.edit()
                .putString(NOTES_KEY, jsonArray.toString())
                .apply()

            LogHelper.writeLog(
                context,
                "NoteStorage",
                "Список заметок сохранён"
            )
        } catch (e: Exception) {
            LogHelper.writeLog(
                context,
                "NoteStorage",
                "Ошибка сохранения заметок: ${e.message}"
            )
        }
    }
}
