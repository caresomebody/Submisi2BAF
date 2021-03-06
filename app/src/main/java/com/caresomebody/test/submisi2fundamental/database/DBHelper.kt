package com.caresomebody.test.submisi2fundamental.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.caresomebody.test.submisi2fundamental.database.GitUserContract.UserColumns.Companion.TABLE_NAME

class DBHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "dbgituser"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_NOTE = "CREATE TABLE $TABLE_NAME" +
                " (${GitUserContract.UserColumns.ID} TEXT NOT NULL UNIQUE," +
                " ${GitUserContract.UserColumns.USERNAME} TEXT NOT NULL," +
                " ${GitUserContract.UserColumns.AVATAR} TEXT NOT NULL," +
                " ${GitUserContract.UserColumns.COMPANY} TEXT NOT NULL," +
                " ${GitUserContract.UserColumns.LOCATION} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_NOTE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}