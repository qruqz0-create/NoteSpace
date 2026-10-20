package com.example.notespace

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etSearch: EditText
    private var isOpeningNotes = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LogHelper.writeLog(
            this,
            "MainActivity",
            "Главный экран создан"
        )

        etSearch = findViewById(R.id.etSearch)
        val btnNotes = findViewById<Button>(R.id.btnNotes)

        btnNotes.setOnClickListener {
            openNotes(etSearch.text.toString())
        }

        etSearch.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                // Поиск выполняется после нажатия кнопки.
                // Это предотвращает открытие нового экрана
                // при каждом введённом символе.
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun openNotes(query: String) {
        if (isOpeningNotes) return

        isOpeningNotes = true

        LogHelper.writeLog(
            this,
            "MainActivity",
            "Открытие списка заметок с поиском: $query"
        )

        val intent = Intent(this, NotesActivity::class.java)
        intent.putExtra("SEARCH_TEXT", query)
        startActivity(intent)

        isOpeningNotes = false
    }

    override fun onStart() {
        super.onStart()
        LogHelper.writeLog(this, "MainActivity", "Экран виден")
    }

    override fun onResume() {
        super.onResume()
        isOpeningNotes = false
        LogHelper.writeLog(this, "MainActivity", "Экран активен")
    }

    override fun onPause() {
        LogHelper.writeLog(
            this,
            "MainActivity",
            "Экран приостановлен"
        )
        super.onPause()
    }

    override fun onStop() {
        LogHelper.writeLog(this, "MainActivity", "Экран скрыт")
        super.onStop()
    }

    override fun onDestroy() {
        LogHelper.writeLog(this, "MainActivity", "Экран уничтожен")
        super.onDestroy()
    }
}
