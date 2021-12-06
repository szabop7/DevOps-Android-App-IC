package com.example.devops.database.devops

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.util.Log
import com.example.devops.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

private const val TAG = "DictionaryDatabase"

// The columns we'll include in the dictionary table
const val COL_WORD = "WORD"
const val COL_DEFINITION = "DEFINITION"

private const val DATABASE_NAME = "DICTIONARY"
private const val FTS_VIRTUAL_TABLE = "FTS"
private const val DATABASE_VERSION = 1

private const val FTS_TABLE_CREATE =
    "CREATE VIRTUAL TABLE $FTS_VIRTUAL_TABLE USING fts3 ($COL_WORD, $COL_DEFINITION)"

class DatabaseTable(context: Context) {

    private val databaseOpenHelper: DatabaseOpenHelper

    init {
        databaseOpenHelper = DatabaseOpenHelper(context)
    }
    fun getWordMatches(query: String, columns: Array<String>?): Cursor? {
        val selection = "$COL_WORD MATCH ?"
        val selectionArgs = arrayOf("$query*")

        return query(selection, selectionArgs, columns)
    }

    private fun query(
        selection: String,
        selectionArgs: Array<String>,
        columns: Array<String>?
    ): Cursor? {
        val cursor: Cursor? = SQLiteQueryBuilder().run {
            tables = FTS_VIRTUAL_TABLE
            query(databaseOpenHelper.readableDatabase,
                columns, selection, selectionArgs, null, null, null)
        }

        return cursor?.run {
            if (!moveToFirst()) {
                close()
                null
            } else {
                this
            }
        } ?: null
    }
    private class DatabaseOpenHelper internal constructor(private val helperContext: Context) :
        SQLiteOpenHelper(helperContext, DATABASE_NAME, null, DATABASE_VERSION) {
        private lateinit var mDatabase: SQLiteDatabase

        override fun onCreate(db: SQLiteDatabase) {
            mDatabase = db
            mDatabase.execSQL(FTS_TABLE_CREATE)
            mDatabase = db
            mDatabase.execSQL(FTS_TABLE_CREATE)
            loadDictionary()
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            Log.w(
                TAG,
                "Upgrading database from version $oldVersion to $newVersion , which will " +
                        "destroy all old data"
            )

            db.execSQL("DROP TABLE IF EXISTS $FTS_VIRTUAL_TABLE")
            onCreate(db)
        }
        private fun loadDictionary() {
            Thread(Runnable {
                try {
                    loadWords()
                } catch (e: IOException) {
                    throw RuntimeException(e)
                }
            }).start()
        }

        @Throws(IOException::class)
        private fun loadWords() {
            val inputStream = helperContext.resources.openRawResource(R.raw.definitions)

            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    val strings: List<String> = line.split("-").map { it.trim() }
                    if (strings.size < 2) continue
                    val id = addWord(strings[0], strings[1])
                    if (id < 0) {
                        Log.e(TAG, "unable to add word: ${strings[0]}")
                    }
                    line = reader.readLine()
                }
            }
        }

        fun addWord(word: String, definition: String): Long {
            val initialValues = ContentValues().apply {
                put(COL_WORD, word)
                put(COL_DEFINITION, definition)
            }

            return mDatabase.insert(FTS_VIRTUAL_TABLE, null, initialValues)
        }
    }
}
