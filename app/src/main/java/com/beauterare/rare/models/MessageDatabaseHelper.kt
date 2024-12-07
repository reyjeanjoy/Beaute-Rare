package com.beauterare.rare

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MessageDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE messages (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                content TEXT NOT NULL,
                isSent INTEGER NOT NULL
            )
        """
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS messages")
        onCreate(db)
    }

    // Add a message to the database
    fun addMessage(message: Message): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("content", message.content)
            put("isSent", if (message.isSent) 1 else 0)
        }
        return db.insert("messages", null, values)
    }

    // Retrieve all messages from the database
    fun getAllMessages(): List<Message> {
        val db = readableDatabase
        val messages = mutableListOf<Message>()
        val cursor: Cursor = db.rawQuery("SELECT * FROM messages", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
                val content = cursor.getString(cursor.getColumnIndexOrThrow("content"))
                val isSent = cursor.getInt(cursor.getColumnIndexOrThrow("isSent")) == 1
                messages.add(Message(id, content, isSent))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return messages
    }

    companion object {
        private const val DATABASE_NAME = "Messages.db"
        private const val DATABASE_VERSION = 1
    }
}

