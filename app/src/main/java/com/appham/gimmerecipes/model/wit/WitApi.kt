package com.appham.gimmerecipes.model.wit

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
        const val MESSAGE_FEED = "message"

    }
}
