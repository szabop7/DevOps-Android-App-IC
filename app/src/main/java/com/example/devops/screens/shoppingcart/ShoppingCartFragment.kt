package com.example.devops.screens.shoppingcart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.devops.R
import com.example.devops.databinding.FragmentShoppingCartBinding

class ShoppingCartFragment : Fragment() {
    private val viewModel: ShoppingCartViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, ShoppingCartViewModelFactory(activity.application))
            .get(ShoppingCartViewModel::class.java)
    }
    private lateinit var binding: FragmentShoppingCartBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shopping_cart, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = ProductShoppingCartAdapter(ProductListener { productId ->
            onClickListener(productId)
        })
        binding.productList.adapter = adapter
        viewModel.shoppingCart.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        return binding.root
    }

    private fun onClickListener(productId: Long) {
        viewModel.removeFromShoppingCart(productId)
    }
}