package com.beauterare.rare

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.beauterare.rare.models.User

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "users.db"
        const val DATABASE_VERSION = 4 // Increment version to force an update
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
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_ADDRESS TEXT NOT NULL
            )
        """
        db.execSQL(createTable)
        Log.d("DatabaseHelper", "Table created: $TABLE_NAME")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d("DatabaseHelper", "Upgrading database from version $oldVersion to $newVersion")
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

        try {
            val result = db.insert(TABLE_NAME, null, values)
            Log.d("DatabaseHelper", "Insert result: $result for user: $values")
            return result != -1L
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error inserting user", e)
            return false
        } finally {
            db.close()
        }
    }

    // Check if email exists
    fun isEmailExists(email: String): Boolean {
        val db = readableDatabase
        val query = "SELECT $COLUMN_EMAIL FROM $TABLE_NAME WHERE $COLUMN_EMAIL = ?"
        val cursor: Cursor = db.rawQuery(query, arrayOf(email))
        val exists = cursor.moveToFirst()
        Log.d("DatabaseHelper", "isEmailExists result: $exists for email: $email")
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
        Log.d("DatabaseHelper", "validateUser result: $isValid for email: $email")
        cursor.close()
        db.close()
        return isValid
    }

    fun getUserByEmail(email: String): User? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_EMAIL = ?"
        val cursor = db.rawQuery(query, arrayOf(email))

        return if (cursor.moveToFirst()) {
            val fullName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val nameParts = fullName.split(" ")
            val fname = nameParts.firstOrNull() ?: ""
            val lname = nameParts.drop(1).joinToString(" ")
            val user = User(
                email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)),
                fname = fname,
                lname = lname,
                address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS))
            )
            cursor.close()
            db.close()
            user
        } else {
            cursor.close()
            db.close()
            null
        }
    }

    fun updateUserProfile(email: String, fname: String, lname: String, address: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, "$fname $lname")
            put(COLUMN_ADDRESS, address)
        }
        val result = db.update(TABLE_NAME, values, "$COLUMN_EMAIL = ?", arrayOf(email))
        db.close()
        return result > 0
    }


}
