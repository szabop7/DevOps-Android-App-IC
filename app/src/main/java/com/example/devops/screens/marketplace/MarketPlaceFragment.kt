package com.example.devops.screens.marketplace

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.devops.R
import com.example.devops.database.devops.DevOpsDatabase
import com.example.devops.databinding.FragmentMarketPlaceBinding

class MarketPlaceFragment : Fragment() {

    private val viewModel: MarketPlaceViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, MarketPlaceViewModelFactory(activity.application))
            .get(MarketPlaceViewModel::class.java)
    }

    private lateinit var binding: FragmentMarketPlaceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_market_place, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = ProductAdapter(ProductListener { productId: Long ->
            onClickListener(productId)
        })

        binding.productList.adapter = adapter

        viewModel.products.observe(viewLifecycleOwner, Observer {
            Log.i("Test", it.toString())
            adapter.submitList(it)
        })

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(qString: String): Boolean {
                viewModel.setFilter(qString)
                return true
            }
            override fun onQueryTextSubmit(qString: String): Boolean {
                return true
            }
        })
        return binding.root
    }

    fun onClickListener(productId: Long) {
        val action = MarketPlaceFragmentDirections.actionMarketPlaceFragmentToDetailViewFragment(productId)
        view?.findNavController()?.navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textViewText = requireActivity().getSharedPreferences("shopping_cart", Context.MODE_PRIVATE)
            .getString("cart_latest_item", "default value")

        // view.findViewById<TextView>(R.id.latestItemCart).text = textViewText
    }
}