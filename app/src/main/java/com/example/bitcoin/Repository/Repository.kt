package com.example.bitcoin.Repository

import com.example.bitcoin.Utils.AppConstants
import com.example.bitcoin.Data.CoinData
import com.example.bitcoin.Interfaces.ApiInterface
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class Repository {

  private val BASE_URL = AppConstants.api_key

    private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    private val apiInterface = retrofit.create(ApiInterface::class.java)
    var res : List<CoinData.CryptoCurrency> = emptyList()
    suspend fun getCoinData(): List<CoinData.CryptoCurrency> {

        val call : Response<CoinData> =  apiInterface.getCoinData()
        try {
            if(call.isSuccessful)
                res = call.body()!!.cryptocurrencies
        }
        catch (e:Exception){
            print(e.message)
        }
        return res
    }
}