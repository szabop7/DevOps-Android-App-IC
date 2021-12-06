package com.example.devops.screens.detailview

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.devops.R
import com.example.devops.adapters.SliderAdapterDetailView
import com.example.devops.models.SliderItemDetailView
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView

class DetailViewFragment : Fragment() {

    var sliderView: SliderView? = null
    private var adapter: SliderAdapterDetailView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_detail_view, container, false)
        sliderView = view.findViewById(R.id.imageSlider)

        adapter = SliderAdapterDetailView(requireContext())

        sliderView?.setSliderAdapter(adapter!!)
        sliderView?.setIndicatorAnimation(IndicatorAnimationType.WORM) // set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView?.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        sliderView?.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH)
        sliderView?.setIndicatorSelectedColor(Color.WHITE)
        sliderView?.setIndicatorUnselectedColor(Color.GRAY)
        sliderView?.setScrollTimeInSec(3)
        sliderView?.setAutoCycle(true)
        sliderView?.startAutoCycle()
        renewItems()

        return view
    }

    fun renewItems() {
        var items: MutableList<SliderItemDetailView> = mutableListOf(SliderItemDetailView(),
            SliderItemDetailView()
        )

        adapter?.renewItems(items)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.addToCartButton).setOnClickListener {
            requireActivity().getSharedPreferences("shopping_cart", Context.MODE_PRIVATE).edit()
                .apply {
                    putString("cart_amount", "twenty dollars")
                    putString("cart_tax", "twenty dollars")
                    putString("cart_quantity", "twenty dollars")
                    putString("cart_latest_item", "la mona lisa")
                }.apply()
        }
    }
}
