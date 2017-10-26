package com.github.sigute.widgets

import android.app.IntentService
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent

class DemoWidgetPinnedService : IntentService("DemoWidgetPinnedService") {
    companion object {
        const val WIDGET_NAME = "WIDGET_NAME"

        fun getIntent(context: Context, widgetName: String): Intent {
            val intent = Intent(context, DemoWidgetPinnedService::class.java)
            intent.putExtra(WIDGET_NAME, widgetName)
            return intent
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) {
            return
        }

        val widgetName = intent.getStringExtra(WIDGET_NAME)
        val widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1)

        if (widgetId == -1) {
            return
        }

        val widgetPreferences = WidgetPreferences(this)
        widgetPreferences.setWidgetValues(widgetId, widgetName)
    }
}
