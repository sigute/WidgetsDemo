package com.github.sigute.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import android.widget.RemoteViews

class DemoWidgetProvider : AppWidgetProvider() {
    companion object {
        fun updateWidgetUI(context: Context, appWidgetManager: AppWidgetManager, widgetPreferences: WidgetPreferences, widgetId: Int) {
            Log.d("DemoWidgetProvider", "Configuring widget id: " + widgetId)
            val widgetName = widgetPreferences.getWidgetName(widgetId)
            if (widgetName != null) {
                Log.d("DemoWidgetProvider", "Widget name: " + widgetName)
                //setting widget name
                val remoteViews = RemoteViews(context.packageName, R.layout.widget)
                remoteViews.setTextViewText(R.id.name, widgetName)
                appWidgetManager.updateAppWidget(widgetId, remoteViews)
            }
        }
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        val widgetPreferences = WidgetPreferences(context)
        for (widgetId: Int in appWidgetIds) {
            updateWidgetUI(context, appWidgetManager, widgetPreferences, widgetId)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        super.onDeleted(context, appWidgetIds)
        val widgetPreferences = WidgetPreferences(context)
        for (appWidgetId: Int in appWidgetIds) {
            widgetPreferences.removeWidget(appWidgetId)
        }
    }
}