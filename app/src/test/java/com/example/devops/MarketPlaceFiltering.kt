package com.example.devops

import com.example.devops.domain.Product
import com.example.devops.domain.Tag
import com.example.devops.screens.marketplace.MarketPlaceViewModel

import com.example.devops.screens.marketplace.MarketplaceFilter

import org.junit.Test

import org.junit.Assert.assertEquals
import org.junit.Before

class MarketPlaceFiltering {
    var products: List<Product> = emptyList()

    fun filterList(filter: MarketplaceFilter?, products: List<Product>): List<Product> {
        return products.filter {
            MarketPlaceViewModel.productIsFiltered(it, filter)
        }
    }

    @Before
    fun beforeEach() {
        products = listOf(
            Product(0, "A"),
            Product(1, "B", productDescription = "T"),
            Product(2, "C", tagList = listOf(Tag(1, "1"))),
            Product(3, "D", tagList = listOf(Tag(2, "2"))),
            Product(4, "E", tagList = listOf(Tag(2, "2")), productDescription = "U"),
            Product(5, "F", tagList = listOf(Tag(3, "3"))),
            Product(6, "G", tagList = listOf(Tag(3, "3"), Tag(4, "4")))
        )
    }

    @Test
    fun productsFilter_NullFilter_ReturnsAll() {
        assertEquals(products, filterList(null, products))
    }

    @Test
    fun productsFilter_EmptyFilter_ReturnsAll() {
        val filter = MarketplaceFilter()
        assertEquals(products, filterList(filter, products))
    }

    @Test
    fun productsFilter_TextFilterName_ReturnsFullMatch() {
        val filter = MarketplaceFilter(text = "A")
        assertEquals(products.filter { it.productId == 0L }, filterList(filter, products))
    }

    @Test
    fun productsFilter_TextFilterDescription_ReturnsFullMatch() {
        val filter = MarketplaceFilter(text = "T")
        assertEquals(products.filter { it.productId == 1L }, filterList(filter, products))
    }

    @Test
    fun productsFilter_TagFilter_ReturnsFullMatch() {
        val filter = MarketplaceFilter(tags = listOf(1L))
        assertEquals(products.filter { it.productId == 2L }, filterList(filter, products))
    }

    @Test
    fun productsFilter_TagFilter_ReturnsPartialMatch() {
        val filter = MarketplaceFilter(tags = listOf(3L))
        assertEquals(products.filter { (5L..6L).contains(it.productId) }, filterList(filter, products))
    }

    @Test
    fun productsFilter_TagFilter_ReturnsMultipleMatch() {
        val filter = MarketplaceFilter(tags = listOf(3L, 4L))
        assertEquals(products.filter { it.productId == 6L }, filterList(filter, products))
    }

    @Test
    fun productsFilter_TextTagFilter_ReturnsMatch() {
        val filter = MarketplaceFilter(text = "F", tags = listOf(3L))
        assertEquals(products.filter { it.productId == 5L }, filterList(filter, products))
    }

    @Test
    fun productsFilter_BadTextTagFilter_ReturnsEmpty() {
        val filter = MarketplaceFilter(text = "X", tags = listOf(3L))
        assertEquals(emptyList<Product>(), filterList(filter, products))
    }
}