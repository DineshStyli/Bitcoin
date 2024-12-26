package com.example.bitcoin.Viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitcoin.Data.AdapterCoinData
import com.example.bitcoin.R
import com.example.bitcoin.Repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoinListViewModel : ViewModel() {

    private val repository = Repository()

    private val coinData = MutableLiveData<List<AdapterCoinData>>()
    val observeCoinData: LiveData<List<AdapterCoinData>> = coinData

    private val _filter1Applied = MutableLiveData(false)
    val filter1Applied: LiveData<Boolean> = _filter1Applied

    private val _filter2Applied = MutableLiveData(false)
    val filter2Applied: LiveData<Boolean> = _filter2Applied

    private val _filter3Applied = MutableLiveData(false)
    val filter3Applied: LiveData<Boolean> = _filter3Applied

    private val _filter4Applied = MutableLiveData(false)
    val filter4Applied: LiveData<Boolean> = _filter4Applied

    private val _filterOption5Applied = MutableLiveData(false)
    val filter5Applied: LiveData<Boolean> = _filterOption5Applied

    private var filter1: Boolean = false
    private var filter2: Boolean = false
    private var filter3: Boolean = false
    private var filter4: Boolean = false
    private var filter5: Boolean = false

    private var currentFilteredData = mutableListOf<AdapterCoinData>()
    private var originalCoinData = emptyList<AdapterCoinData>()

    fun applyFilterActiveCoins() {
        if (filter1) {
            filter1 = false
            _filter1Applied.value = false

            val notActiveList = originalCoinData.filter { it.is_active.not() }
            currentFilteredData =
                (currentFilteredData + notActiveList).toMutableList().sortedBy { it.name }
                    .toMutableList()
            coinData.value = currentFilteredData
        } else {
            filter1 = true
            _filter1Applied.value = true

            currentFilteredData =
                currentFilteredData.filter { it.is_active }.toMutableList().sortedBy { it.name }
                    .toMutableList()
            coinData.value = currentFilteredData
        }
    }

    fun applyFilterInactiveCoins() {
        if (filter2) {
            filter2 = false
            _filter2Applied.value = false

            val notInActiveList = originalCoinData.filter { it.is_active }
            currentFilteredData =
                (currentFilteredData + notInActiveList).toMutableList().sortedBy { it.name }
                    .toMutableList()
            coinData.value = currentFilteredData
        } else {
            filter2 = true
            _filter2Applied.value = true

            currentFilteredData = currentFilteredData.filter { it.is_active.not() }.toMutableList()
                .sortedBy { it.name }.toMutableList()
            coinData.value = currentFilteredData
        }
    }

    fun applyFilterNewCoins() {
        if (filter3) {
            filter3 = false
            _filter3Applied.value = false

            val notNewList = originalCoinData.filter { it.is_new.not() }
            currentFilteredData =
                (currentFilteredData + notNewList).toMutableList().sortedBy { it.name }
                    .toMutableList()
            coinData.value = currentFilteredData
        } else {
            filter3 = true
            _filter3Applied.value = true

            currentFilteredData =
                currentFilteredData.filter { it.is_new }.toMutableList().sortedBy { it.name }
                    .toMutableList()
            coinData.value = currentFilteredData
        }
    }

    fun applyFilterOnlyCoins() {
        if (filter4) {
            filter4 = false
            _filter4Applied.value = false

            val notCoinList = originalCoinData.filter { (it.type == "coin").not() }
            currentFilteredData =
                (currentFilteredData + notCoinList).toMutableList().sortedBy { it.name }
                    .toMutableList()
            coinData.value = currentFilteredData
        } else {
            filter4 = true
            _filter4Applied.value = true

            currentFilteredData = currentFilteredData.filter { it.type == "coin" }.toMutableList()
                .sortedBy { it.name }.toMutableList()
            coinData.value = currentFilteredData
        }
    }

    fun applyFilterOnlyTokens() {
        if (filter5) {
            filter5 = false
            _filterOption5Applied.value = false

            val notTokenList = originalCoinData.filter { (it.type == "token").not() }
            currentFilteredData =
                (currentFilteredData + notTokenList).toMutableList().sortedBy { it.name }
                    .toMutableList()
            coinData.value = currentFilteredData
        } else {
            filter5 = true
            _filterOption5Applied.value = true

            currentFilteredData = currentFilteredData.filter { it.type == "token" }.toMutableList()
                .sortedBy { it.name }.toMutableList()
            coinData.value = currentFilteredData
        }
    }

    fun getCoinData() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getCoinData()

            val adapterList = mutableListOf<AdapterCoinData>()
            for (it in result) {
                val id = when {
                    it.type == "coin" && it.is_active -> {
                        R.drawable.coin_active
                    }

                    it.type == "coin" && !it.is_active -> {
                        R.drawable.inactive
                    }

                    it.type == "token" && it.is_active -> {
                        R.drawable.token_active
                    }

                    it.type == "token" && !it.is_active -> {
                        R.drawable.inactive
                    }

                    else -> {
                        R.drawable.inactive
                    }
                }
                adapterList.add(
                    AdapterCoinData(
                        is_active = it.is_active,
                        is_new = it.is_new,
                        name = it.name,
                        symbol = it.symbol,
                        type = it.type,
                        drawableId = id,
                    )
                )
            }

            val list = adapterList.sortedBy { it.name }

            currentFilteredData = list.toMutableList()
            originalCoinData = list

            coinData.postValue(list)
        }
    }

    fun searchData(newText: String? = null) {
        if (newText == null) {
            coinData.value = currentFilteredData
            return
        }

        val searchList = currentFilteredData.filter {
                it.name.startsWith( newText,true) || it.symbol.startsWith(newText, true)
            }
        coinData.value = searchList
    }
}
