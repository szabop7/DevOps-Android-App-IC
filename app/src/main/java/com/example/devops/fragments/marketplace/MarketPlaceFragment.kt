package com.example.devops.fragments.marketplace

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.devops.R
import com.example.devops.database.devops.DevOpsDatabase
import com.example.devops.databinding.FragmentMarketPlaceBinding

import com.example.devops.database.devops.product.Product


class MarketPlaceFragment : Fragment() {

    private lateinit var binding: FragmentMarketPlaceBinding
    private lateinit var viewModel : MarketPlaceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_market_place, container, false)

        // binding.pictureMarketPlace.setOnClickListener(this::onClickListener)
        //Get an instance of the appContext to setup the database
        val appContext = requireNotNull(this.activity).application
        val dataSource = DevOpsDatabase.getInstance(appContext).productDao

        //use a factory to pass the database reference to the viewModel
        val viewModelFactory = MarketPlaceViewModelFactory(dataSource, appContext)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MarketPlaceViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = ProductAdapter(ProductListener { productId: Long ->
            Toast.makeText(context, productId.toString(), Toast.LENGTH_SHORT).show()
            onClickListener()
        })

        binding.productList.adapter = adapter

        viewModel.products.observe(viewLifecycleOwner, Observer{
             adapter.submitList(it)
        })

        return binding.root
    }

    fun onClickListener(){
        view?.findNavController()?.navigate(R.id.action_MarketPlaceFragment_to_detailViewFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val textViewText = requireActivity().getSharedPreferences("shopping_cart", Context.MODE_PRIVATE)
            .getString("cart_latest_item", "default value")

        // view.findViewById<TextView>(R.id.latestItemCart).text = textViewText
    }

}