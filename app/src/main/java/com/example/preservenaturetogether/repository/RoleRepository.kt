package com.example.preservenaturetogether.repository

import android.annotation.SuppressLint
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.example.preservenaturetogether.data.DatabaseHelper
import com.example.preservenaturetogether.data.Role
import java.io.IOException

@SuppressLint("Range")
class RoleRepository(context: Context) {
    private var roleList: List<Role> = listOf()

    fun getRole(roleId: Int): Role = roleList[roleId - 1]

    init {
        val databaseHelper = DatabaseHelper(context)
        val database: SQLiteDatabase
        try {
            databaseHelper.updateDataBase()
        } catch (mIOException: IOException) {
            throw mIOException
        }
        try {
            database = databaseHelper.readableDatabase
        } catch (mSQLException: SQLException) {
            throw mSQLException
        }
        database.rawQuery(
            "SELECT * FROM Role", null
        ).use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    roleList += Role(
                        id = cursor.getInt(cursor.getColumnIndex("_id")),
                        name = cursor.getString(cursor.getColumnIndex("_name")),
                    )
                } while (cursor.moveToNext())
            }
        }
    }

    companion object {
        @Volatile
        private var instance: RoleRepository? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: RoleRepository(context = context).also { instance = it }
            }
    }
}