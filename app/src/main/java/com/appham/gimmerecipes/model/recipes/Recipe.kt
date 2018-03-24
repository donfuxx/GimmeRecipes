package com.appham.gimmerecipes.model.recipes

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Generated pojo with http://www.jsonschema2pojo.org/ and converted to Kotlin
 */
class Recipe() : Parcelable {

    @SerializedName("publisher")
    @Expose
    var publisher: String? = null
    @SerializedName("f2f_url")
    @Expose
    var f2fUrl: String? = null
    @SerializedName("ingredients")
    @Expose
    var ingredients: List<String>? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("source_url")
    @Expose
    var sourceUrl: String? = null
    @SerializedName("recipe_id")
    @Expose
    var recipeId: String? = null
    @SerializedName("image_url")
    @Expose
    var imageUrl: String? = null
    @SerializedName("social_rank")
    @Expose
    var socialRank: Double? = null
    @SerializedName("publisher_url")
    @Expose
    var publisherUrl: String? = null

    constructor(parcel: Parcel) : this() {
        publisher = parcel.readString()
        f2fUrl = parcel.readString()
        parcel.readList(ingredients, java.lang.String::class.java.classLoader)
        title = parcel.readString()
        sourceUrl = parcel.readString()
        recipeId = parcel.readString()
        imageUrl = parcel.readString()
        socialRank = parcel.readValue(Double::class.java.classLoader) as? Double
        publisherUrl = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(publisher)
        parcel.writeString(f2fUrl)
        parcel.writeList(ingredients);
        parcel.writeString(title)
        parcel.writeString(sourceUrl)
        parcel.writeString(recipeId)
        parcel.writeString(imageUrl)
        parcel.writeValue(socialRank)
        parcel.writeString(publisherUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Recipe> {
        override fun createFromParcel(parcel: Parcel): Recipe {
            return Recipe(parcel)
        }

        override fun newArray(size: Int): Array<Recipe?> {
            return arrayOfNulls(size)
        }
    }

}
