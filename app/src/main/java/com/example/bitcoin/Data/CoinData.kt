package com.example.bitcoin.Data

data class CoinData(
    val cryptocurrencies : List<CryptoCurrency>
) {
    data class CryptoCurrency(
        val is_active: Boolean,
        val is_new: Boolean,
        val name: String,
        val symbol: String,
        val type: String
    )
}