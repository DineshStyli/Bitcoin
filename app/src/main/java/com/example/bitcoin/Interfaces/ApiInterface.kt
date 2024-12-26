package com.example.bitcoin.Interfaces

import com.example.bitcoin.Data.CoinData
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

@GET("v3/ac7d7699-a7f7-488b-9386-90d1a60c4a4b")
suspend fun getCoinData():Response<CoinData>
}