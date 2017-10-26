package com.github.sigute.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class WidgetPreferences(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("widget_preferences", Context.MODE_PRIVATE)

    //using commit instead of apply as value is needed straight away
    @SuppressLint("ApplySharedPref")
    fun setWidgetValues(widgetId: Int, name: String) {
        val editor = preferences.edit()
        editor.putString("" + widgetId, name)
        editor.commit()
    }

    fun getWidgetName(widgetId: Int): String? {
        return preferences.getString("" + widgetId, null)
    }

    fun removeWidget(widgetId: Int) {
        val editor = preferences.edit()
        editor.remove("" + widgetId)
        editor.apply()
    }
}
