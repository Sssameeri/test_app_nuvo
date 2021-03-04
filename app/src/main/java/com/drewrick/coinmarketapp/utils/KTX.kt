package com.drewrick.coinmarketapp

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

fun String.beautyDate(ctx: Context): String {
    val date: Date?
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault())
    simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
    date = simpleDateFormat.parse(this)
    val time = (System.currentTimeMillis() - date.time) / 1000
    return when {
        time < 60 -> {
            ctx.getString(R.string.few_moments_ago)
        }
        time < 60 * 60 -> {
            if ((time / 60).toFloat().roundToInt() == 1) ctx.getString(
                R.string.minute_ago,
                (time / 60).toFloat().roundToInt()
            ) else ctx.getString(R.string.minutes_ago, (time / 60).toFloat().roundToInt())
        }
        time < 60 * 60 * 24 -> {
            if ((time / 60 / 60).toFloat().roundToInt() == 1) ctx.getString(
                R.string.hour_ago,
                (time / 60 / 60).toFloat().roundToInt()
            ) else ctx.getString(R.string.hours_ago, (time / 60 / 60).toFloat().roundToInt())
        }
        time < 60 * 60 * 24 * 7 -> {
            if ((time / 60 / 60 / 24).toFloat().roundToInt() == 1) ctx.getString(
                R.string.day_ago,
                (time / 60 / 60 / 24).toFloat().roundToInt()
            ) else ctx.getString(R.string.days_ago, (time / 60 / 60 / 24).toFloat().roundToInt())
        }
        else ->
            SimpleDateFormat("MM.dd.yyyy HH:mm", Locale.getDefault()).format(date.time)
    }
}