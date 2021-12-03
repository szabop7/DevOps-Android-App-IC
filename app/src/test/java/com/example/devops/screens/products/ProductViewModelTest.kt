package com.example.devops.screens.products

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Rule
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