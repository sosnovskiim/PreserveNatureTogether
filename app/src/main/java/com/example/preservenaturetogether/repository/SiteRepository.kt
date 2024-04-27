package com.example.preservenaturetogether.repository

import android.annotation.SuppressLint
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.example.preservenaturetogether.data.DatabaseHelper
import com.example.preservenaturetogether.data.Site
import java.io.IOException

@SuppressLint("Range")
class SiteRepository(context: Context) {
    private var siteList: List<Site> = listOf()

    fun getSiteListByDistrict(districtId: Int): List<Site> =
        siteList.filter { it.districtId == districtId }

    fun getSiteListByCategory(categoryId: Int): List<Site> =
        siteList.filter { it.categoryId == categoryId }

    fun getSite(siteId: Int): Site = siteList[siteId - 1]

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
            "SELECT * FROM Site", null,
        ).use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    siteList += Site(
                        id = cursor.getInt(cursor.getColumnIndex("_id")),
                        districtId = cursor.getInt(cursor.getColumnIndex("_districtId")),
                        categoryId = cursor.getInt(cursor.getColumnIndex("_categoryId")),
                        ecoConditionId = cursor.getInt(cursor.getColumnIndex("_ecoConditionId")),
                        name = cursor.getString(cursor.getColumnIndex("_name")),
                        description = cursor.getString(cursor.getColumnIndex("_description")),
                        suggestion = cursor.getString(cursor.getColumnIndex("_suggestion")) ?: "",
                        latitude = cursor.getDouble(cursor.getColumnIndex("_latitude")),
                        longitude = cursor.getDouble(cursor.getColumnIndex("_longitude")),
                        photo1 = cursor.getString(cursor.getColumnIndex("_photo1")),
                        photo2 = cursor.getString(cursor.getColumnIndex("_photo2")) ?: "",
                    )
                } while (cursor.moveToNext())
            }
        }
    }

    companion object {
        @Volatile
        private var instance: SiteRepository? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: SiteRepository(context = context).also { instance = it }
            }
    }
}