package com.example.devops.screens.marketplace

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.devops.databinding.ListItemProductBinding
import com.example.devops.domain.Product
import com.example.devops.network.BASE_URL


class ProductAdapter(val clickListener: ProductListener) : ListAdapter<Product, ViewHolder>(ProductDiffCallback()) {

    // fill up the item you need (e.g. set texts and images)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
}

class ViewHolder(val binding: ListItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(clickListener: ProductListener, item: Product) {
        binding.productDescriptionTextview.text = item.productDescription
        binding.productTitleTextview.text = item.productName

        binding.product = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
        item.productImgPath = BASE_URL + item.productImgPath
    }

    // this way the viewHolder knows how to inflate.
    // better than having this in the adapter.
    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemProductBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding)
        }
    }
}

class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.productId == newItem.productId
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
        // works perfectly because it's a dataclass.
    }
}

class ProductListener(val clickListener: (productId: Long) -> Unit) {
    fun onClick(productDatabase: Product) = clickListener(productDatabase.productId)
}
