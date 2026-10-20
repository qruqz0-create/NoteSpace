package com.example.notespace

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        LogHelper.writeLog(
            this,
            "DetailActivity",
            "Форма создания заметки открыта"
        )

        val etTitle = findViewById<EditText>(R.id.etTitle)
        val etText = findViewById<EditText>(R.id.etText)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val text = etText.text.toString().trim()

            if (title.isEmpty()) {
                etTitle.error = getString(R.string.title_required)

                LogHelper.writeLog(
                    this,
                    "DetailActivity",
                    "Ошибка: название заметки не введено"
                )
            } else {
                val result = "$title\n$text"

                intent.putExtra("NOTE_RESULT", result)
                setResult(RESULT_OK, intent)

                LogHelper.writeLog(
                    this,
                    "DetailActivity",
                    "Заметка передана в список"
                )

                finish()
            }
        }
    }

    override fun onDestroy() {
        LogHelper.writeLog(
            this,
            "DetailActivity",
            "Форма создания заметки закрыта"
        )
        super.onDestroy()
    }
}
