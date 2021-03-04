package com.drewrick.coinmarketapp.view.adapter.listadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.drewrick.coinmarketapp.databinding.CurrencyItemBinding
import com.drewrick.coinmarketapp.model.models.Currency
import com.drewrick.coinmarketapp.view.adapter.listadapter.holder.CurrencyHolder

class CurrencyAdapter(val callback: OnItemClickListener) :
    ListAdapter<Currency, CurrencyHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyHolder {
        return CurrencyHolder(
            CurrencyItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CurrencyHolder, position: Int) {
        holder.bind(getItem(position), callback)
    }

    interface OnItemClickListener {
        fun onItemClicked(currency: Currency?)
    }
}