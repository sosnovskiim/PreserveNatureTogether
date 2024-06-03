package com.example.preservenaturetogether.repository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.example.preservenaturetogether.data.DatabaseHelper
import com.example.preservenaturetogether.data.User
import java.io.IOException

@SuppressLint("Range")
class UserRepository(context: Context) {
    private lateinit var database: SQLiteDatabase
    private var userList: List<User> = listOf()

    fun getUserByLoginAndPassword(userLogin: String, userPassword: String): User? {
        userList.forEach { user ->
            if (user.login == userLogin && user.password == userPassword) {
                return user
            }
        }
        return null
    }

    fun getUserByLogin(userLogin: String): User? {
        userList.forEach { user ->
            if (user.login == userLogin) {
                return user
            }
        }
        return null
    }

    fun getUserById(userId: Int): User = userList[userId - 1]

    fun addUser(userLogin: String, userPassword: String) {
        val user = User(
            id = userList.size + 1,
            roleId = 3,
            login = userLogin,
            password = userPassword,
        )
        val values = ContentValues()
        values.put("_id", user.id)
        values.put("_roleId", user.roleId)
        values.put("_login", user.login)
        values.put("_password", user.password)
        database.insert("User", null, values)
        userList += user
    }

    init {
        val databaseHelper = DatabaseHelper(context)
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
            "SELECT * FROM User", null
        ).use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    userList += User(
                        id = cursor.getInt(cursor.getColumnIndex("_id")),
                        roleId = cursor.getInt(cursor.getColumnIndex("_roleId")),
                        login = cursor.getString(cursor.getColumnIndex("_login")),
                        password = cursor.getString(cursor.getColumnIndex("_password")),
                    )
                } while (cursor.moveToNext())
            }
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: UserRepository(context = context).also { instance = it }
            }
    }
}