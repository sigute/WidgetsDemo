package com.github.sigute.widgets

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DemoWidgetConfigurationActivity : AppCompatActivity() {

    private var widgetId: Int = AppWidgetManager.INVALID_APPWIDGET_ID

    private lateinit var widgetName: EditText
    private lateinit var saveButton: Button

    private lateinit var preferences: WidgetPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration)

        widgetName = findViewById(R.id.widget_name_edit_text)
        saveButton = findViewById(R.id.save_button)
        saveButton.setOnClickListener {
            save()
        }

        preferences = WidgetPreferences(this)

        val intent = intent
        val extras = intent.extras
        if (extras != null) {
            widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
        }

        if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            //something went wrong
            finish()
        }
    }

    private fun save() {
        if (widgetName.text.toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.error_widget_name_not_entered), Toast.LENGTH_SHORT).show()
            return
        }

        preferences.setWidgetValues(widgetId, widgetName.text.toString())
        val appWidgetManager = AppWidgetManager.getInstance(this)
        DemoWidgetProvider.updateWidgetUI(this, appWidgetManager, preferences, widgetId)
        finishWithResult()
    }

    private fun finishWithResult() {
        val resultValue = Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
        setResult(Activity.RESULT_OK, resultValue)
        finish()
    }
}
