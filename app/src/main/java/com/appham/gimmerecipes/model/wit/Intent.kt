package com.appham.gimmerecipes.model.wit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Generated pojo with http://www.jsonschema2pojo.org/ and converted to Kotlin
 */
class Intent {

    @SerializedName("confidence")
    @Expose
    var confidence: Double? = null
    @SerializedName("value")
    @Expose
    var value: String? = null

}
