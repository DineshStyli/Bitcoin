package com.example.bitcoin.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bitcoin.Data.AdapterCoinData
import com.example.bitcoin.R

class DataAdapter : ListAdapter<AdapterCoinData, DataViewHolder>(DiffCall()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_coin, parent, false),
        )
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }
}

class DataViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
    private val coinName: TextView = parent.findViewById(R.id.txt_coin_name)
    private val coinSymbol: TextView = parent.findViewById(R.id.txt_coin_symbol)
    private val coinType: ImageView = parent.findViewById(R.id.txt_coin_type)
    private val coinNew: ImageView = parent.findViewById(R.id.txt_coin_new)

    fun bind(item: AdapterCoinData) {
        coinName.text = item.name
        coinSymbol.text = item.symbol
        coinNew.isVisible = item.is_new
        coinType.setImageResource(item.drawableId)
    }
}

class DiffCall : DiffUtil.ItemCallback<AdapterCoinData>() {
    override fun areItemsTheSame(
        oldItem: AdapterCoinData,
        newItem: AdapterCoinData,
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: AdapterCoinData,
        newItem: AdapterCoinData,
    ): Boolean {
        return oldItem == newItem
    }
}
