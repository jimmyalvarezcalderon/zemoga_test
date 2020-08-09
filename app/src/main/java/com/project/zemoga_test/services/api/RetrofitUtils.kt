package com.project.zemoga_test.services.api

import arrow.syntax.function.memoize
import com.google.gson.Gson
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUtils {

    var BASE_URL = "https://jsonplaceholder.typicode.com"
    private val gson: Gson = Gson()

    fun getRetrofitClient(url: String): Single<Retrofit> {
        return Single.defer { Single.just(retrofitClient(url)) }
    }

    private val retrofitClient: (String) -> Retrofit = { url: String ->
        buildRetrofit(url, OkHttpClient.Builder().build())
    }.memoize()

    private fun buildRetrofit(url: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}