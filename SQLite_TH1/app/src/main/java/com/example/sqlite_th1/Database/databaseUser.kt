package com.example.sqlite_th1.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class databaseUser:SQLiteOpenHelper {
    constructor(context: Context) : super(context, "user.db", null, 3) {
        this.context = context
    }

    private val context: Context
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE user (name TEXT, phone TEXT)")
        db?.execSQL("INSERT INTO user (name, phone) VALUES ('Nguyen Van A', '0123456789')")
        db?.execSQL("INSERT INTO user (name, phone) VALUES ('Nguyen Van B', '0987654321')")
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        TODO("Not yet implemented")
    }
    fun showData(): ArrayList<String> {
        val list = ArrayList<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM user", null)
        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(0)
                val phone = cursor.getString(1)
                list.add("$name - $phone")
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun addUser(name: String, phone: String) {
        val db = this.writableDatabase
        db.execSQL("INSERT INTO user (name, phone) VALUES ('$name', '$phone')")
        db.close()
    }
    fun deleteUser(phone: String) {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM user WHERE phone = '$phone'")
        db.close()
    }
    fun updateUser(name: String, phone: String) {
        val db = this.writableDatabase
        db.execSQL("UPDATE user SET name = '$name' WHERE phone = '$phone'")
        db.close()
    }
}