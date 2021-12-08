package com.example.devops.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


private const val BASE_API = "http://10.0.2.2:5000/api/"
public const val BASE_URL = "http://10.0.2.2:5000"

// create moshi object
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

private val client = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()

// Scalars Converter = converter for strings to plain text bodies
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_API)
    .client(client)
    .build()

interface DevOpsApiService {

    @GET("product")
    fun getProducts(): Deferred<ApiProductContainer>

    @GET("product/{id}")
    fun getProduct(
        @Path("id") productId: Long
    ): Deferred<ApiProduct>
}

object DevOpsApi {
    // lazy properties = thread safe --> can only be initialized once at a time
    // adds extra safety to our 1 instance we need.
    val retrofitService: DevOpsApiService by lazy {
        retrofit.create(DevOpsApiService::class.java)
    }
}