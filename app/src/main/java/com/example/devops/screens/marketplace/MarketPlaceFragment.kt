package com.example.devops.screens.marketplace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.devops.R
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
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_market_place, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = ProductMarketPlaceAdapter(ProductListener { productId: Long ->
            onClickListener(productId)
        })

        binding.productList.adapter = adapter

        viewModel.productsAndFilter.observe(viewLifecycleOwner, Observer {
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

    private fun onClickListener(productId: Long) {
        val action = MarketPlaceFragmentDirections.actionMarketPlaceFragmentToDetailViewFragment(productId)
        view?.findNavController()?.navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}