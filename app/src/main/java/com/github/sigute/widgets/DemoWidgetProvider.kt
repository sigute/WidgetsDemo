package com.github.sigute.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

class DemoWidgetProvider : AppWidgetProvider() {
    companion object {
        fun updateWidgets(context: Context) {
            val intent = Intent(context, DemoWidgetProvider::class.java)
            intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            val widgetIDs = AppWidgetManager.getInstance(context).getAppWidgetIds(ComponentName(context, DemoWidgetProvider::class.java))
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIDs)
            context.sendBroadcast(intent)
        }

        fun getRemoteViews(context: Context, widgetName: String): RemoteViews {
            val remoteViews = RemoteViews(context.packageName, R.layout.widget)
            remoteViews.setTextViewText(R.id.name, widgetName)
            return remoteViews
        }

        fun updateWidgetUI(context: Context, appWidgetManager: AppWidgetManager, widgetPreferences: WidgetPreferences, widgetId: Int) {
            val widgetName = widgetPreferences.getWidgetName(widgetId)
            if (widgetName != null) {
                appWidgetManager.updateAppWidget(widgetId, getRemoteViews(context, widgetName))
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
