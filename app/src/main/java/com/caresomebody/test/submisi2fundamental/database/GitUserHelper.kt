package com.caresomebody.test.submisi2fundamental.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.caresomebody.test.submisi2fundamental.database.GitUserContract.UserColumns.Companion.ID
import com.caresomebody.test.submisi2fundamental.database.GitUserContract.UserColumns.Companion.TABLE_NAME
import java.sql.SQLException

class GitUserHelper(context: Context) {
    private var dbHelper: DBHelper = DBHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE : GitUserHelper? = null
        fun getInstance(context: Context): GitUserHelper =
                INSTANCE ?: synchronized(this){
                    INSTANCE ?: GitUserHelper(context)
                }
    }

    init {
        dbHelper = DBHelper(context)
    }

    @Throws(SQLException::class)
    fun open(){
        database = dbHelper.writableDatabase
    }

    fun close(){
        dbHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                "$ID ASC")
    }

    fun queryById(id: String): Cursor {
        return database.query(
                DATABASE_TABLE,
                null,
                "$ID= ?",
                arrayOf(id),
                null,
                null,
                null,
                null)
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$ID = ?", arrayOf(id))
    }

    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$ID= '$id'", null)
    }
}