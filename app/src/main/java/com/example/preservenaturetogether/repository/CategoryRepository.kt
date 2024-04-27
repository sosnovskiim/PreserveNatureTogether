package com.example.preservenaturetogether.repository

import android.annotation.SuppressLint
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.example.preservenaturetogether.data.Category
import com.example.preservenaturetogether.data.DatabaseHelper
import com.example.preservenaturetogether.data.EcoCondition
import java.io.IOException

@SuppressLint("Range")
class CategoryRepository(context: Context) {
    private var categoryList: List<Category> = listOf()

    fun getCategoryList(): List<Category> = categoryList

    fun getCategory(categoryId: Int): Category = categoryList[categoryId - 1]

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
            "SELECT * FROM Category", null
        ).use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    categoryList += Category(
                        id = cursor.getInt(cursor.getColumnIndex("_id")),
                        name = cursor.getString(cursor.getColumnIndex("_name")),
                    )
                } while (cursor.moveToNext())
            }
        }
    }

    companion object {
        @Volatile
        private var instance: CategoryRepository? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CategoryRepository(context = context).also { instance = it }
            }
    }
}