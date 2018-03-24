package com.appham.gimmerecipes.api

import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * @author thomas
 */

object ApiFactory {

    private var retrofitRecipes: Retrofit? = null

    private var recipesApi: RecipesApi? = null

    private var retrofitWit: Retrofit? = null

    private var witApi: WitApi? = null

    /**
     * Make a retrofit recipes API implementation or re-use already existing one
     * @return RecipesApi
     */
    fun createRecipesApi(): RecipesApi {

        //re-use already created api
        recipesApi?.let {
            return it
        }

        //re-use already created retrofit
        retrofitRecipes?.let {
            return it.create(RecipesApi::class.java)
        }

        // build retrofit & api
        retrofitRecipes = Retrofit.Builder()
                .baseUrl(RecipesApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOkHttp())
                .build()
        recipesApi = retrofitRecipes?.create(RecipesApi::class.java)
        return recipesApi as RecipesApi
    }

    /**
     * Make a retrofit recipes API implementation or re-use already existing one
     * @return RecipesApi
     */
    fun createWitApi(): WitApi {

        //re-use already created api
        witApi?.let {
            return it
        }

        //re-use already created retrofit
        retrofitWit?.let {
            return it.create(WitApi::class.java)
        }

        // build retrofit & api
        retrofitWit = Retrofit.Builder()
                .baseUrl(WitApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOkHttp())
                .build()
        witApi = retrofitWit?.create(WitApi::class.java)
        return witApi as WitApi
    }

    /**
     * Create a OkHttpClient with logging activated
     */
    private fun createOkHttp(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
    }

    /**
     * Create a OkHttpClient for Wit with authentication
     */
    private fun createOkHttpWit(): OkHttpClient {

        val credentials = Credentials.basic(WitApi.USER, WitApi.TOKEN);

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
    }

}
