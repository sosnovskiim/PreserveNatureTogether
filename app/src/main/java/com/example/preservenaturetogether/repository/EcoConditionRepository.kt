package com.example.preservenaturetogether.repository

import android.annotation.SuppressLint
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.example.preservenaturetogether.data.DatabaseHelper
import com.example.preservenaturetogether.data.EcoCondition
import java.io.IOException

@SuppressLint("Range")
class EcoConditionRepository(context: Context) {
    private var ecoConditionList: List<EcoCondition> = listOf()

    fun getEcoCondition(ecoConditionId: Int): EcoCondition = ecoConditionList[ecoConditionId - 1]

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
            "SELECT * FROM EcoCondition", null
        ).use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    ecoConditionList += EcoCondition(
                        id = cursor.getInt(cursor.getColumnIndex("_id")),
                        name = cursor.getString(cursor.getColumnIndex("_name")),
                    )
                } while (cursor.moveToNext())
            }
        }
    }

    companion object {
        @Volatile
        private var instance: EcoConditionRepository? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: EcoConditionRepository(context = context).also { instance = it }
            }
    }
}