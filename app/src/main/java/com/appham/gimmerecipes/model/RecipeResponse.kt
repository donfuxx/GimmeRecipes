package com.appham.gimmerecipes.model

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Generated pojo with http://www.jsonschema2pojo.org/ and converted to Kotlin
 */
class RecipeResponse constructor(parcel: Parcel) : Parcelable {

    @SerializedName("recipe")
    @Expose
    var recipe: Recipe? = null

    init {
        this.recipe = parcel.readValue(Recipe::class.java.classLoader) as Recipe
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(recipe)
    }

    override fun describeContents(): Int {
        return 0
    }


    companion object  CREATOR: Creator<RecipeResponse> {

            override fun createFromParcel(parcel: Parcel): RecipeResponse {
                return RecipeResponse(parcel)
            }


            override fun newArray(size: Int): Array<RecipeResponse?> {
                return arrayOfNulls(size)
            }

    }

}
