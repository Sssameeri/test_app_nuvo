package com.drewrick.coinmarketapp.view.adapter.listadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.drewrick.coinmarketapp.databinding.CurrencyItemBinding
import com.drewrick.coinmarketapp.model.models.Currency
import com.drewrick.coinmarketapp.view.adapter.listadapter.holder.CurrencyHolder
import java.util.*
import kotlin.collections.ArrayList

class CurrencyAdapter(
    private var list: ArrayList<Currency>,
    private val callback: OnItemClickListener
) :
    RecyclerView.Adapter<CurrencyHolder>(), Filterable {

    var currencyFilterList = ArrayList<Currency>()

    init {
        currencyFilterList = list
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
        val currency = currencyFilterList[position]
        holder.bind(currency, callback)
    }

    interface OnItemClickListener {
        fun onItemClicked(currency: Currency?)
    }

    override fun getItemCount(): Int {
        return currencyFilterList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val search = constraint.toString()
                currencyFilterList = if (search.isEmpty()) {
                    list
                } else {
                    val result = ArrayList<Currency>()
                    for (item in list) {
                        if ((item.name.toLowerCase(Locale.ROOT)
                                .contains(search.toLowerCase(Locale.ROOT)) ||
                                    (item.symbol.toLowerCase(Locale.ROOT)
                                        .contains(search.toLowerCase(Locale.ROOT))))
                        ) {
                            result.add(item)
                        }
                    }
                    result
                }
                val filterResult = FilterResults()
                filterResult.values = currencyFilterList
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                currencyFilterList = results?.values as ArrayList<Currency>
                notifyDataSetChanged()
            }

        }
    }
}