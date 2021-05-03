package com.prateekcode.githubbrowser.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.Uri
import android.view.inputmethod.InputMethodManager
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.min


object Utils {
    fun hideKeyboard(activity: Activity) {
        val view = activity.currentFocus
        if (view != null) {
            val iMM = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            iMM.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            context.applicationContext.packageName,
            Context.MODE_PRIVATE
        )
    }

    fun formatDateTime(dateStr: String): String {
        val date = Date(dateStr)
        val sdf = SimpleDateFormat("YYYY-MM-dd", Locale.ENGLISH)
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }


    fun getDateToMilliSeconds(date: String): Long {
        var timeInMilliseconds:Long=0
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        try {
            val mDate: Date = sdf.parse(date)
            timeInMilliseconds = mDate.getTime()
            println("Date in milli :: $timeInMilliseconds")
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return timeInMilliseconds
    }

    fun getFormattedDateFromTimestamp(timestampInMilliSeconds: Long): String? {
        val date = Date()
        date.time = timestampInMilliSeconds
        return SimpleDateFormat("yyyy-MM-dd").format(date)
    }

    fun getSixLetterString(commitStr: String):String?{
        return commitStr.substring(0, min(commitStr.length, 6))
    }

    fun clearSharedPreferences(context: Context) {
        getSharedPreferences(context).edit().clear().apply()
    }

    fun startActivity(context: Context, activity: Class<*>) {
        val intent = Intent(context, activity)
        context.startActivity(intent)
    }

    fun goToURL(context: Context, url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    }

    fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }


}