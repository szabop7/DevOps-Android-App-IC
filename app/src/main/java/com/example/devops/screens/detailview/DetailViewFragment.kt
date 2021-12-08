package com.example.devops.screens.detailview

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.devops.R
import com.example.devops.adapters.SliderAdapterDetailView
import com.example.devops.databinding.FragmentDetailViewBinding
import com.example.devops.domain.Product
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView

class DetailViewFragment : Fragment() {

    var sliderView: SliderView? = null
    private var adapter: SliderAdapterDetailView? = null
    private lateinit var binding: FragmentDetailViewBinding
    val args: DetailViewFragmentArgs by navArgs()
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

        viewModel.products.observe(viewLifecycleOwner, Observer {
            val product = it.find { product -> product.productId == args.productId }
            if (product != null) {
                setProductDetails(product)
            }
        })

        return binding.root
    }

    fun setProductDetails(p: Product) {
        product = p
        binding.detailViewTitleText.text = p.productName
        binding.detailViewDescriptionText.text = p.productDescription
        binding.detailViewPriceText.text = p.productPrice.toString()
        val sliderItemDetailView = SliderItemDetailView(
            p.productDescription,
            p.productImgPath
        )
        adapter!!.renewItems(mutableListOf(sliderItemDetailView))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addToCartButton.setOnClickListener {
            if (product != null) {
                viewModel.addToCart(product!!)
            }

            /*requireActivity().getSharedPreferences("shopping_cart", Context.MODE_PRIVATE).edit()
                .apply {
                    putString("cart_amount", "twenty dollars")
                    putString("cart_tax", "twenty dollars")
                    putString("cart_quantity", "twenty dollars")
                    putString("cart_latest_item", "la mona lisa")

                }.apply()*/
        }
    }
}
