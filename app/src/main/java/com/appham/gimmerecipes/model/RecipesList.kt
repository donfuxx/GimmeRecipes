package com.appham.gimmerecipes.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Generated pojo with http://www.jsonschema2pojo.org/ and converted to Kotlin
 */
class RecipesList() : Parcelable {

    @SerializedName("count")
    @Expose
    var count: Int? = null
    @SerializedName("recipes")
    @Expose
    var recipes: MutableList<Recipe>? = null

    constructor(parcel: Parcel) : this() {
        count = parcel.readValue(Int::class.java.classLoader) as? Int
        recipes = parcel.createTypedArrayList(Recipe.CREATOR)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(count)
        parcel.writeTypedList(recipes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecipesList> {
        override fun createFromParcel(parcel: Parcel): RecipesList {
            return RecipesList(parcel)
        }

        override fun newArray(size: Int): Array<RecipesList?> {
            return arrayOfNulls(size)
        }
    }

}
