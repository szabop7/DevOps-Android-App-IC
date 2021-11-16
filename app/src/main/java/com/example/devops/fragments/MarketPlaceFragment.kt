package com.example.devops.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import com.example.devops.R


class MarketPlaceFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_market_place, container, false)
        view.findViewById<ImageView>(R.id.picture_market_place).setOnClickListener(this::onClickListener)
        return view

    }

    public fun onClickListener(view_: View){
        view?.findNavController()?.navigate(R.id.action_MarketPlaceFragment_to_detailViewFragment)
    }

}