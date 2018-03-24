package com.appham.gimmerecipes.api

import com.appham.gimmerecipes.MvpContract
import com.appham.gimmerecipes.model.Recipe
import com.appham.gimmerecipes.model.RecipeResponse
import com.appham.gimmerecipes.model.RecipesList
import com.appham.gimmerecipes.model.WitResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * The Recipes API including api key
 *
 * @author thomas
 */

interface WitApi {

    @Headers("Authorization: $USER $TOKEN")
    @GET(MESSAGE_FEED)
    fun getEntities(@Query("q") q:String): Observable<WitResponse>

    companion object {

        const val BASE_URL = "https://api.wit.ai/"
        const val TOKEN = "HUX6XDY6HJU3UOFMKJUARRNENDQBYAGQ"
        const val USER = "Bearer"
        const val APP_ID = "5ab3de67-435b-4aa9-956a-d8257ae6270c"
        const val MESSAGE_FEED = "message"

    }
}
