package com.example.notespace

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class NotesActivity : AppCompatActivity() {

    private val notes = ArrayList<String>()

    private lateinit var notesList: LinearLayout
    private lateinit var etSearch: EditText

    private val addNoteLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == RESULT_OK) {
                val note = result.data?.getStringExtra("NOTE_RESULT")

                if (!note.isNullOrBlank()) {
                    notes.add(note)
                    NoteStorage.saveNotes(this, notes)

                    LogHelper.writeLog(
                        this,
                        "NotesActivity",
                        "Новая заметка добавлена"
                    )

                    showNotes(etSearch.text.toString())
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        LogHelper.writeLog(
            this,
            "NotesActivity",
            "Список заметок открыт"
        )

        notes.addAll(NoteStorage.getNotes(this))

        val btnBack = findViewById<TextView>(R.id.btnBack)
        etSearch = findViewById(R.id.etSearchNotes)
        notesList = findViewById(R.id.notesList)

        val btnAddNote = findViewById<Button>(R.id.btnAddNote)

        btnBack.setOnClickListener {
            LogHelper.writeLog(
                this,
                "NotesActivity",
                "Возврат на главный экран"
            )

            finish()
        }

        val searchText = intent.getStringExtra("SEARCH_TEXT") ?: ""
        etSearch.setText(searchText)

        showNotes(searchText)

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
                showNotes(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        btnAddNote.setOnClickListener {
            LogHelper.writeLog(
                this,
                "NotesActivity",
                "Открытие формы создания заметки"
            )

            val intent = Intent(this, DetailActivity::class.java)
            addNoteLauncher.launch(intent)
        }
    }

    private fun showNotes(query: String) {
        notesList.removeAllViews()

        for (note in notes) {
            if (note.contains(query, ignoreCase = true)) {
                val noteView = TextView(this)

                noteView.text = note
                noteView.textSize = 18f
                noteView.setTextColor(getColor(R.color.text_color))
                noteView.setBackgroundColor(getColor(R.color.note_yellow))
                noteView.setPadding(24, 24, 24, 24)

                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

                params.bottomMargin = 16
                notesList.addView(noteView, params)
            }
        }

        if (notesList.childCount == 0) {
            val emptyView = TextView(this)

            emptyView.text = getString(R.string.empty_notes)
            emptyView.textSize = 16f
            emptyView.setTextColor(
                getColor(R.color.secondary_text_color)
            )
            emptyView.setPadding(8, 16, 8, 16)

            notesList.addView(emptyView)
        }
    }

    override fun onDestroy() {
        LogHelper.writeLog(
            this,
            "NotesActivity",
            "Экран списка заметок закрыт"
        )
        super.onDestroy()
    }
}
