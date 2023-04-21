package com.example.storezaapdemo

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.storezaapdemo.model.User
import java.util.Date

class SharedPrefManager(private val context: Context) {
    private val SHARED_PREF_NAME = "thecodingshef"
    private  val LAST_TIME_APP_USED = "last_time_app_used"
    private  val IS_LAST_TIME = "is_last_time"
    private val TIME_SHARED_PREF="time_shared_pref"
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
        setIsLastTimeAppUseSaved(context,false)
        editor!!.clear()
        editor!!.apply()
    }

    fun clear() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
        editor!!.clear()
        editor!!.apply()
    }

    fun getLastTimeAppUsed(context: Context): Date {

       val mills= context.getSharedPreferences(TIME_SHARED_PREF, Context.MODE_PRIVATE)?.getLong(LAST_TIME_APP_USED,System.currentTimeMillis())!!
        return Date(mills)
    }

    fun setLastTimeAppUsed(context: Context,date: Date){
         context.getSharedPreferences(TIME_SHARED_PREF, Context.MODE_PRIVATE)?.edit {
             putLong(LAST_TIME_APP_USED,date.time)
             apply()
         }

    }

    fun getIsLastTimeAppUseSaved(context: Context):Boolean
    {
        return context.getSharedPreferences(TIME_SHARED_PREF, Context.MODE_PRIVATE).getBoolean(IS_LAST_TIME,false)
    }
    fun setIsLastTimeAppUseSaved(context: Context,isSaved:Boolean)
    {
        context.getSharedPreferences(TIME_SHARED_PREF, Context.MODE_PRIVATE).edit {
            putBoolean(TIME_SHARED_PREF,isSaved)
            apply()
        }

    }

}