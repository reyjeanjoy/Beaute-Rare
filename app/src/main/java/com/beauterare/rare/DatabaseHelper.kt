    package com.beauterare.rare

    import android.content.ContentValues
    import android.content.Context
    import android.database.Cursor
    import android.database.sqlite.SQLiteDatabase
    import android.database.sqlite.SQLiteOpenHelper
    import android.util.Log
    import com.beauterare.rare.models.User
    import com.beauterare.rare.models.Appointment

    class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        companion object {
            const val DATABASE_NAME = "users.db"
            const val DATABASE_VERSION = 5 // Increment version to add appointments table

            // Users table
            const val TABLE_NAME_USERS = "users"
            const val COLUMN_EMAIL = "email"
            const val COLUMN_PASSWORD = "password"
            const val COLUMN_NAME = "name"
            const val COLUMN_ADDRESS = "address"

            // Appointments table
            const val TABLE_NAME_APPOINTMENTS = "appointments"
            const val COLUMN_ID = "id"
            const val COLUMN_CUSTOMER_NAME = "customer_name"
            const val COLUMN_MAKEUP_NAME = "makeup_name"
            const val COLUMN_MAKEUP_ARTIST = "makeup_artist"
            const val COLUMN_APPOINTMENT_TIME = "appointment_time"
            const val COLUMN_APPOINTMENT_DATE = "appointment_date"
        }

        override fun onCreate(db: SQLiteDatabase) {
            // Create users table
            val createUsersTable = """
                CREATE TABLE $TABLE_NAME_USERS (
                    $COLUMN_EMAIL TEXT PRIMARY KEY,
                    $COLUMN_PASSWORD TEXT NOT NULL,
                    $COLUMN_NAME TEXT NOT NULL,
                    $COLUMN_ADDRESS TEXT NOT NULL
                )
            """
            db.execSQL(createUsersTable)

            // Create appointments table
            val createAppointmentsTable = """
                CREATE TABLE $TABLE_NAME_APPOINTMENTS (
                    $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    $COLUMN_CUSTOMER_NAME TEXT NOT NULL,
                    $COLUMN_MAKEUP_NAME TEXT NOT NULL,
                    $COLUMN_MAKEUP_ARTIST TEXT NOT NULL,
                    $COLUMN_APPOINTMENT_TIME TEXT NOT NULL,
                    $COLUMN_APPOINTMENT_DATE TEXT NOT NULL
                )
            """
            db.execSQL(createAppointmentsTable)

            Log.d("DatabaseHelper", "Tables created: $TABLE_NAME_USERS, $TABLE_NAME_APPOINTMENTS")
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            Log.d("DatabaseHelper", "Upgrading database from version $oldVersion to $newVersion")
            db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_USERS")
            db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_APPOINTMENTS")
            onCreate(db)
        }

        // ---------------------- Users Table Methods ----------------------
        fun insertUser(email: String, password: String, name: String, address: String): Boolean {
            val db = writableDatabase
            val values = ContentValues().apply {
                put(COLUMN_EMAIL, email)
                put(COLUMN_PASSWORD, password)
                put(COLUMN_NAME, name)
                put(COLUMN_ADDRESS, address)
            }

            return try {
                val result = db.insert(TABLE_NAME_USERS, null, values)
                Log.d("DatabaseHelper", "Insert result: $result for user: $values")
                result != -1L
            } catch (e: Exception) {
                Log.e("DatabaseHelper", "Error inserting user", e)
                false
            } finally {
                db.close()
            }
        }

        fun isEmailExists(email: String): Boolean {
            val db = readableDatabase
            val query = "SELECT $COLUMN_EMAIL FROM $TABLE_NAME_USERS WHERE $COLUMN_EMAIL = ?"
            val cursor: Cursor = db.rawQuery(query, arrayOf(email))
            val exists = cursor.moveToFirst()
            cursor.close()
            db.close()
            return exists
        }

        fun validateUser(email: String, password: String): Boolean {
            val db = readableDatabase
            val query = "SELECT * FROM $TABLE_NAME_USERS WHERE $COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
            val cursor = db.rawQuery(query, arrayOf(email, password))
            val isValid = cursor.moveToFirst()
            cursor.close()
            db.close()
            return isValid
        }

        fun getUserByEmail(email: String): User? {
            val db = readableDatabase
            val query = "SELECT * FROM $TABLE_NAME_USERS WHERE $COLUMN_EMAIL = ?"
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
            val result = db.update(TABLE_NAME_USERS, values, "$COLUMN_EMAIL = ?", arrayOf(email))
            db.close()
            return result > 0
        }

        // ---------------------- Appointments Table Methods ----------------------
        fun insertAppointment(
            customerName: String,
            makeupName: String,
            makeupArtist: String,
            appointmentTime: String,
            appointmentDate: String
        ): Boolean {
            val db = writableDatabase
            val values = ContentValues().apply {
                put(COLUMN_CUSTOMER_NAME, customerName)
                put(COLUMN_MAKEUP_NAME, makeupName)
                put(COLUMN_MAKEUP_ARTIST, makeupArtist)
                put(COLUMN_APPOINTMENT_TIME, appointmentTime)
                put(COLUMN_APPOINTMENT_DATE, appointmentDate)
            }
            val result = db.insert(TABLE_NAME_APPOINTMENTS, null, values)
            db.close()
            return result != -1L
        }

        fun getAppointments(): List<Appointment> {
            val appointments = mutableListOf<Appointment>()
            val db = readableDatabase
            val cursor = db.rawQuery("SELECT * FROM appointments", null)

            if (cursor.moveToFirst()) {
                do {
                    val appointment = Appointment(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        customerName = cursor.getString(cursor.getColumnIndexOrThrow("customer_name")),
                        makeupName = cursor.getString(cursor.getColumnIndexOrThrow("makeup_name")),
                        makeupArtist = cursor.getString(cursor.getColumnIndexOrThrow("makeup_artist")),
                        appointmentTime = cursor.getString(cursor.getColumnIndexOrThrow("appointment_time")),
                        appointmentDate = cursor.getString(cursor.getColumnIndexOrThrow("appointment_date"))
                    )
                    appointments.add(appointment)
                } while (cursor.moveToNext())
            }

            cursor.close()
            db.close()
            return appointments
        }

        fun deleteAppointment(appointmentId: Int): Boolean {
            val db = writableDatabase
            val result = db.delete("appointments", "id = ?", arrayOf(appointmentId.toString()))
            db.close()
            return result > 0
        }
    }
