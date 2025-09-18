package com.example.photomemo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "photomemo.db"
        private const val DATABASE_VERSION = 3 // bumped since schema changed

        // User table
        private const val TABLE_USERS = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PASSWORD = "password"
        private const val COLUMN_PHONE = "phone"
        private const val COLUMN_GENDER = "gender"

        // Notes table
        private const val TABLE_NOTES = "notes"
        private const val COLUMN_NOTE_ID = "note_id"
        private const val COLUMN_NOTE_EMAIL = "user_email"
        private const val COLUMN_NOTE_DATE = "date"
        private const val COLUMN_NOTE_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUsersTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_EMAIL TEXT UNIQUE,
                $COLUMN_PASSWORD TEXT,
                $COLUMN_PHONE TEXT,
                $COLUMN_GENDER TEXT
            )
        """
        db.execSQL(createUsersTable)

        val createNotesTable = """
            CREATE TABLE $TABLE_NOTES (
                $COLUMN_NOTE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOTE_EMAIL TEXT,
                $COLUMN_NOTE_DATE TEXT,
                $COLUMN_NOTE_CONTENT TEXT,
                FOREIGN KEY($COLUMN_NOTE_EMAIL) REFERENCES $TABLE_USERS($COLUMN_EMAIL)
            )
        """
        db.execSQL(createNotesTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NOTES")
        onCreate(db)
    }

    // Insert user
    fun insertUser(name: String, email: String, password: String, phone: String, gender: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_EMAIL, email)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_PHONE, phone)
            put(COLUMN_GENDER, gender)
        }
        val result = db.insert(TABLE_USERS, null, values)
        return result != -1L
    }

    // User login
    fun checkUser(email: String, password: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_USERS WHERE $COLUMN_EMAIL=? AND $COLUMN_PASSWORD=?",
            arrayOf(email, password)
        )
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    // Get user details (full User)
    fun getUserDetails(email: String): User? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT $COLUMN_EMAIL, $COLUMN_PASSWORD, $COLUMN_NAME, $COLUMN_PHONE, $COLUMN_GENDER FROM $TABLE_USERS WHERE $COLUMN_EMAIL=?",
            arrayOf(email)
        )
        var user: User? = null
        if (cursor.moveToFirst()) {
            val userEmail = cursor.getString(0)
            val password = cursor.getString(1)
            val fullName = cursor.getString(2)
            val phone = cursor.getString(3)
            val gender = cursor.getString(4)
            user = User(userEmail, password, fullName, phone, gender)
        }
        cursor.close()
        return user
    }

    // Insert note
    fun insertNote(userEmail: String, date: String, content: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOTE_EMAIL, userEmail)
            put(COLUMN_NOTE_DATE, date)
            put(COLUMN_NOTE_CONTENT, content)
        }
        val result = db.insert(TABLE_NOTES, null, values)
        return result != -1L
    }

    // Get all notes for a user
    fun getNotes(userEmail: String): List<Note> {
        val db = readableDatabase
        val notes = mutableListOf<Note>()
        val cursor = db.rawQuery(
            "SELECT $COLUMN_NOTE_ID, $COLUMN_NOTE_DATE, $COLUMN_NOTE_CONTENT FROM $TABLE_NOTES WHERE $COLUMN_NOTE_EMAIL=? ORDER BY $COLUMN_NOTE_ID DESC",
            arrayOf(userEmail)
        )
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val date = cursor.getString(1)
                val content = cursor.getString(2)
                notes.add(Note(id, userEmail, date, content))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return notes
    }
}

// Data classes
data class Note(val id: Int, val userEmail: String, val date: String, val content: String)
