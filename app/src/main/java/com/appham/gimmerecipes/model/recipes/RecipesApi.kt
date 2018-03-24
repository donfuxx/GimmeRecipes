package com.appham.gimmerecipes.model.recipes

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * The Recipes API including api key
 *
 * @author thomas
 */

interface RecipesApi {

    @get:GET(SEARCH_FEED)
    val recipes: Observable<RecipesList>

    @GET(SEARCH_FEED)
    fun getRecipesByQuery(@Query("q") q:String): Observable<RecipesList>

    @GET(GET_FEED)
    fun getRecipeById(@Query("rId") id:String): Observable<RecipeResponse>

    companion object {

        const val BASE_URL = "http://food2fork.com/api/"
        const val KEY = "461990df5d8e47e63445351edfd44a83"
        const val SEARCH_FEED = "search?key=${KEY}"
        const val GET_FEED = "get?key=${KEY}"

    }
}
