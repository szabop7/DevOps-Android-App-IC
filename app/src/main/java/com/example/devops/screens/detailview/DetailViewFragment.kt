package com.example.devops.screens.detailview

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.devops.R
import com.example.devops.adapters.SliderAdapterDetailView
import com.example.devops.databinding.FragmentDetailViewBinding
import com.example.devops.domain.Product
import com.google.android.material.chip.Chip
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView

/**
 * Fragment of the detail view
 */
class DetailViewFragment : Fragment() {

    private var sliderView: SliderView? = null
    private var adapter: SliderAdapterDetailView? = null
    private lateinit var binding: FragmentDetailViewBinding
    private val args: DetailViewFragmentArgs by navArgs()
    private var product: Product? = null

    private val viewModel: DetailViewViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, DetailViewViewModelFactory(args.productId, activity.application))
            .get(DetailViewViewModel::class.java)
    }

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_view, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        sliderView = binding.imageSlider

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

        viewModel.product.observe(viewLifecycleOwner, Observer {
            Log.i("Product", it.toString())
            it?.let { setProductDetails(it) }
        })

        viewModel.isInCart.observe(viewLifecycleOwner, Observer {
            binding.addToCartButton.text = if (it) {
                "Already in cart"
            } else {
                "Add to cart"
            }
        })

        return binding.root
    }

    /**
     * Sets a [Product] details into the detail slider view
     */
    private fun setProductDetails(p: Product) {
        product = p
        binding.detailViewPriceText.text = p.productPrice.toString() + " €"
        val sliderItemDetailView = SliderItemDetailView(
            p.productDescription,
            p.productImgPath
        )
        binding.tagList.removeAllViews()
        for (tag in p.tagList) {
            val chip = Chip(context)
            chip.text = tag.tagName
            binding.tagList.addView(chip)
        }
        adapter!!.renewItems(mutableListOf(sliderItemDetailView))
                adapter!!.renewItems(mutableListOf(
                       sliderItemDetailView,
                        SliderItemDetailView(
                                drawableId = R.drawable.more_soon
                                    ),
                    ))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addToCartButton.setOnClickListener {
            if (product != null) {
                viewModel.addToCart(product!!)
            }
        }
    }
}
