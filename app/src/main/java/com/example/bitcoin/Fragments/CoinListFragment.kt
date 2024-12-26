package com.example.bitcoin.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bitcoin.Viewmodels.CoinListViewModel
import com.example.bitcoin.Adapters.DataAdapter
import com.example.bitcoin.R

class CoinListFragment : Fragment() {

    private lateinit var viewModel: CoinListViewModel

    private lateinit var rv: RecyclerView
    private lateinit var sv: SearchView
    private lateinit var btnActive: TextView
    private lateinit var btnInactive: TextView
    private lateinit var btnNewCoins: TextView
    private lateinit var btnOnlyCoins: TextView
    private lateinit var btnOnlyTokens: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_coin_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv = view.findViewById(R.id.rv_list)
        sv = view.findViewById(R.id.searchView)
        btnActive = view.findViewById(R.id.btn_active_coins)
        btnInactive = view.findViewById(R.id.btn_inactive_coins)
        btnNewCoins = view.findViewById(R.id.btn_new_coins)
        btnOnlyCoins = view.findViewById(R.id.btn_only_coins)
        btnOnlyTokens = view.findViewById(R.id.btn_only_tokens)

        val adapter = DataAdapter()
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(view.context)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory(),
        )[CoinListViewModel::class.java]

        addClickListeners()
        viewModel.getCoinData()

        viewModel.observeCoinData.observe(viewLifecycleOwner) {
            rv.scrollToPosition(0)
            adapter.submitList(it)
        }

        viewModel.filter1Applied.observe(viewLifecycleOwner) { isApplied ->
            setFilterBackground(btnActive, isApplied)
        }

        viewModel.filter2Applied.observe(viewLifecycleOwner) { isApplied ->
            setFilterBackground(btnInactive, isApplied)
        }

        viewModel.filter3Applied.observe(viewLifecycleOwner) { isApplied ->
            setFilterBackground(btnNewCoins, isApplied)
        }

        viewModel.filter4Applied.observe(viewLifecycleOwner) { isApplied ->
            setFilterBackground(btnOnlyCoins, isApplied)
        }

        viewModel.filter5Applied.observe(viewLifecycleOwner) { isApplied ->
            setFilterBackground(btnOnlyTokens, isApplied)
        }
    }

    private fun addClickListeners() {
        btnActive.setOnClickListener {
            viewModel.applyFilterActiveCoins()
        }

        btnInactive.setOnClickListener {
            viewModel.applyFilterInactiveCoins()
        }

        btnNewCoins.setOnClickListener {
            viewModel.applyFilterNewCoins()
        }

        btnOnlyCoins.setOnClickListener {
            viewModel.applyFilterOnlyCoins()
        }

        btnOnlyTokens.setOnClickListener {
            viewModel.applyFilterOnlyTokens()
        }

        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (newText.isNotEmpty()) {
                        viewModel.searchData(newText)
                    } else {
                        viewModel.searchData(null)
                    }
                }
                return true
            }
        })
    }

    private fun setFilterBackground(filterBtn: TextView, isApplied: Boolean) {
        if (isApplied) {
            filterBtn.background = ResourcesCompat.getDrawable(resources,
                R.drawable.bg_selected_filter, resources.newTheme())
        } else {
            filterBtn.background = ResourcesCompat.getDrawable( resources,
                R.drawable.bg_unselected_filter, resources.newTheme())
        }
    }

    companion object {
        fun newInstance() = CoinListFragment()
    }
}