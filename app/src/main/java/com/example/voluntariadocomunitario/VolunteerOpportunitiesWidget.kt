package com.example.voluntariadocomunitario

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class VolunteerOpportunitiesWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val views = RemoteViews(context.packageName, R.layout.widget_volunteer_opportunities)

        // Launch a coroutine to fetch data from Firebase
        CoroutineScope(Dispatchers.IO).launch {
            val firebaseHelper = FirebaseHelper()
            val events = firebaseHelper.getVoluntaryActs()
            val upcomingEvents = filterUpcomingEvents(events)
            val titles = upcomingEvents.joinToString("\n") { it.title }

            views.setTextViewText(R.id.widget_title, "Upcoming Volunteer Opportunities")
            views.setTextViewText(R.id.widget_item, titles)

            // Create an Intent to launch MainActivity
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            views.setOnClickPendingIntent(R.id.widget_title, pendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    private fun filterUpcomingEvents(events: List<VoluntaryAct>): List<VoluntaryAct> {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return events.filter {
            val eventDate = dateFormat.parse(it.date)
            eventDate != null && eventDate.after(currentDate)
        }
    }
}