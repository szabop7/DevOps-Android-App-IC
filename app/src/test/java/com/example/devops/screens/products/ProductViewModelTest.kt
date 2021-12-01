package com.example.devops.screens.products

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.devops.fragments.marketplace.MarketPlaceViewModel
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsNot.not

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductViewModelTest{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    /*@Test
    fun test_liveData(){
        val marketPlaceViewModel = marketPlaceViewModel()

        marketPlaceViewModel.changeCurrentJoke()

        val value = marketPlaceViewModel.currentJoke.getOrAwaitValue()

        assertThat(value, not(nullValue()))
    }*/

}