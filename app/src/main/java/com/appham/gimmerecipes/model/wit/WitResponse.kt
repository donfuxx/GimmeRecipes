package com.appham.gimmerecipes.model.wit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Generated pojo with http://www.jsonschema2pojo.org/ and converted to Kotlin
 */
class WitResponse {

    @SerializedName("_text")
    @Expose
    var text: String? = null
    @SerializedName("entities")
    @Expose
    var entities: Entities? = null
    @SerializedName("msg_id")
    @Expose
    var msgId: String? = null

}
