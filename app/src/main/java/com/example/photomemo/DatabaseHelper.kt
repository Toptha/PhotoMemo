package com.example.photomemo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "PhotoMemo.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_USERS = "users"

        private const val COL_EMAIL = "email"
        private const val COL_PASSWORD = "password"
        private const val COL_FULLNAME = "fullName"
        private const val COL_PHONE = "phone"
        private const val COL_GENDER = "gender"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_USERS (
                $COL_EMAIL TEXT PRIMARY KEY,
                $COL_PASSWORD TEXT,
                $COL_FULLNAME TEXT,
                $COL_PHONE TEXT,
                $COL_GENDER TEXT
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    /** Insert new user (returns false if email exists) */
    fun insertUser(email: String, password: String, fullName: String, phone: String, gender: String): Boolean {
        if (checkEmailExists(email)) return false

        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_EMAIL, email)
            put(COL_PASSWORD, password)
            put(COL_FULLNAME, fullName)
            put(COL_PHONE, phone)
            put(COL_GENDER, gender)
        }
        val result = db.insert(TABLE_USERS, null, values)
        db.close()
        return result != -1L
    }

    /** Check if email already exists */
    private fun checkEmailExists(email: String): Boolean {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COL_EMAIL),
            "$COL_EMAIL=?",
            arrayOf(email),
            null, null, null
        )
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    /** Check user credentials for login */
    fun checkUser(email: String, password: String): Boolean {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COL_EMAIL),
            "$COL_EMAIL=? AND $COL_PASSWORD=?",
            arrayOf(email, password),
            null, null, null
        )
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    /** Get full user details for ProfileActivity */
    fun getUserDetails(email: String): User? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COL_FULLNAME, COL_PHONE, COL_GENDER),
            "$COL_EMAIL=?",
            arrayOf(email),
            null, null, null
        )

        var user: User? = null
        if (cursor.moveToFirst()) {
            val fullName = cursor.getString(cursor.getColumnIndexOrThrow(COL_FULLNAME))
            val phone = cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE))
            val gender = cursor.getString(cursor.getColumnIndexOrThrow(COL_GENDER))
            user = User(fullName, phone, gender)
        }

        cursor.close()
        db.close()
        return user
    }

    /** Data class for profile */
    data class User(
        val fullName: String,
        val phone: String,
        val gender: String
    )
}
