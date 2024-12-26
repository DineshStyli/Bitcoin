package com.example.bitcoin.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bitcoin.Fragments.CoinListFragment
import com.example.bitcoin.R


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.activiyContainer, CoinListFragment.newInstance())
            .commit()


    }
}