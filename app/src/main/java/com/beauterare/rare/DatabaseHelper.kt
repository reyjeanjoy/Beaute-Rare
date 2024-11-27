package com.beauterare.rare

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "users.db"
        const val DATABASE_VERSION = 2
        const val TABLE_NAME = "users"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_NAME = "name"
        const val COLUMN_ADDRESS = "address"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_EMAIL TEXT PRIMARY KEY,
                $COLUMN_PASSWORD TEXT NOT NULL,
                $COLUMN_NAME TEXT,
                $COLUMN_ADDRESS TEXT
            )
        """
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME") // Drop the existing table
        onCreate(db) // Recreate the table with the updated schema
    }


    // Insert user into SQLite
    fun insertUser(email: String, password: String, name: String, address: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_EMAIL, email)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_NAME, name)
            put(COLUMN_ADDRESS, address)
        }

        // Log the values to debug
        Log.d("DatabaseHelper", "Inserting user: $values")

        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result != -1L
    }

    // Check if email exists
    fun isEmailExists(email: String): Boolean {
        val db = readableDatabase
        val query = "SELECT 1 FROM $TABLE_NAME WHERE $COLUMN_EMAIL = ?"
        val cursor: Cursor = db.rawQuery(query, arrayOf(email))
        val exists = cursor.moveToFirst()
        cursor.close()
        db.close()
        return exists
    }

    // Validate user credentials
    fun validateUser(email: String, password: String): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(email, password))
        val isValid = cursor.moveToFirst()
        cursor.close()
        db.close()
        return isValid
    }
}
