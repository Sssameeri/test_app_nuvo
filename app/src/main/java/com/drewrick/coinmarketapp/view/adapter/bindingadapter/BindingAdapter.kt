package com.drewrick.coinmarketapp.view.adapter.bindingadapter

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.drewrick.coinmarketapp.R
import com.drewrick.coinmarketapp.beautyDate
import com.drewrick.coinmarketapp.utils.Constants

@BindingAdapter("app:setBeautifulText")
fun setBeautifulText(textView: TextView, date: String) {
    textView.text = date.beautyDate(textView.context)
}

@BindingAdapter("app:setBeautifulExchange")
fun setBeautifulExchange(textView: TextView, exchange: Double) {
    textView.text = String.format(textView.context.getString(R.string.actual_exchange), exchange)
}

@BindingAdapter("app:setUsdExchange")
fun setUsdExchange(textView: TextView, exchange: Double) {
    textView.text =
        String.format(textView.context.getString(R.string.actual_usd_exchange), exchange)
}

@BindingAdapter("app:setBtcExchange")
fun setBtcExchange(textView: TextView, exchange: Double) {
    textView.text =
        String.format(textView.context.getString(R.string.actual_btc_exchange), exchange)
}

@BindingAdapter("app:setTotalCoins")
fun setTotalCoinsAvailable(textView: TextView, totalCoins: Double) {
    if (totalCoins == .0) {
        textView.visibility = View.GONE
    } else {
        textView.text =
            String.format(textView.context.getString(R.string.total_coins_available), totalCoins)
    }
}

@BindingAdapter("app:setMaxCoins")
fun setMaxCoinsAvailable(textView: TextView, maxCoins: Double) {
    if (maxCoins == .0) {
        textView.visibility = View.GONE
    } else {
        textView.text =
            String.format(textView.context.getString(R.string.max_coins_available), maxCoins)
    }
}

@BindingAdapter("app:setTotalCapitalization")
fun setTotalCapitalization(textView: TextView, totalCapitalize: Double) {
    if (totalCapitalize == .0) {
        textView.visibility = View.GONE
    } else {
        textView.text =
            String.format(
                textView.context.getString(R.string.total_capitalization),
                totalCapitalize
            )
    }
}

@BindingAdapter("app:setPercentChange", "app:type")
fun setPercentChange(textView: TextView, percentChange: Double, type: String) {
    if (percentChange < 0) {
        textView.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            R.drawable.ic_baseline_keyboard_arrow_down_24,
            0
        )
    } else {
        textView.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            R.drawable.ic_baseline_keyboard_arrow_up_24,
            0
        )
    }

    when (type) {
        Constants.HOUR -> {
            textView.text =
                String.format(
                    textView.context.getString(R.string.percent_change_to_last_hour),
                    percentChange
                )
        }
        Constants.DAY -> {
            textView.text =
                String.format(
                    textView.context.getString(R.string.percent_change_to_last_day),
                    percentChange
                )
        }
        else -> {
            textView.text =
                String.format(
                    textView.context.getString(R.string.percent_change_to_last_week),
                    percentChange
                )
        }
    }


}