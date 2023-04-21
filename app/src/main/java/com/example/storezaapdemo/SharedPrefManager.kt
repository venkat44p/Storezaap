package com.example.storezaapdemo

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.storezaapdemo.model.User
import java.util.Date

class SharedPrefManager(private val context: Context) {
    private val SHARED_PREF_NAME = "thecodingshef"
    private  val USER_LOGGED_IN_TIME = "user_time"
    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    fun saveUser(user: User) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
        editor!!.putString("id", user.id)
        editor!!.putString("username", user.username)
        editor!!.putString("email", user.email)
        editor!!.putBoolean("logged", true)
        editor!!.apply()
    }

    fun isLoggedIn(): Boolean {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences!!.getBoolean("logged", false)
    }

    fun getUser(): User {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return User(
            sharedPreferences!!.getString("id", null),
            sharedPreferences!!.getString("username", null),
            sharedPreferences!!.getString("email", null)
        )
    }

    fun logout() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
        editor!!.clear()
        editor!!.apply()
    }

    fun clear() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
        editor!!.clear()
        editor!!.apply()
    }

    fun getUserLoggedInTime(): Date {

       val mills= context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)?.
        getLong(USER_LOGGED_IN_TIME,System.currentTimeMillis())?:System.currentTimeMillis()
        return Date(mills)
    }

    fun setUserLoggedInTime(date: Date){
         context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)?.edit {
             putLong(USER_LOGGED_IN_TIME,date.time)
             apply()
         }

    }


}