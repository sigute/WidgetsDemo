package com.github.sigute.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle

class DemoWidgetPinnedReceiver : BroadcastReceiver() {
    companion object {
        const val WIDGET_NAME = "WIDGET_NAME"
        const val BROADCAST_ID = 123456

        fun getPendingIntent(context: Context, widgetName: String): PendingIntent {
            val callbackIntent = Intent(context, DemoWidgetPinnedReceiver::class.java)
            val bundle = Bundle()
            bundle.putString(WIDGET_NAME, widgetName)
            callbackIntent.putExtras(bundle)
            return PendingIntent.getBroadcast(
                    context, BROADCAST_ID, callbackIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) {
            return
        }

        val widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1)
        if (widgetId == -1) {
            return
        }

        val widgetName = intent.getStringExtra(WIDGET_NAME)

        val widgetPreferences = WidgetPreferences(context)
        widgetPreferences.setWidgetValues(widgetId, widgetName)
        DemoWidgetProvider.updateWidgets(context)
    }
}
