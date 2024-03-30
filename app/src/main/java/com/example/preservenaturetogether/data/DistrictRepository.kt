package com.example.preservenaturetogether.data

import android.annotation.SuppressLint
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import java.io.IOException

@SuppressLint("Range")
class DistrictRepository(context: Context) {
    private var districtList: List<District> = listOf()

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
            "SELECT * FROM District", null
        ).use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    districtList += District(
                        id = cursor.getInt(cursor.getColumnIndex("_id")),
                        name = cursor.getString(cursor.getColumnIndex("_name")),
                        photo = cursor.getString(cursor.getColumnIndex("_photo")),
                    )
                } while (cursor.moveToNext())
            }
        }
    }

    fun getDistrict(districtId: Int): District = districtList[districtId - 1]

    fun getDistrictList(): List<District> = districtList

    companion object {
        @Volatile
        private var instance: DistrictRepository? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: DistrictRepository(context = context).also { instance = it }
            }
    }
}