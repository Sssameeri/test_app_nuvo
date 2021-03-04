package com.drewrick.coinmarketapp.view.adapter.listadapter.holder

import androidx.recyclerview.widget.RecyclerView
import com.drewrick.coinmarketapp.databinding.CurrencyItemBinding
import com.drewrick.coinmarketapp.model.models.Currency
import com.drewrick.coinmarketapp.view.adapter.listadapter.CurrencyAdapter

class CurrencyHolder(private val binding: CurrencyItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(currency: Currency, callback: CurrencyAdapter.OnItemClickListener) {
        binding.currency = currency
        binding.callback = callback
        binding.executePendingBindings()
    }
}