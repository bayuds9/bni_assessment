package id.flowerencee.qrpaymentapp.data.model.response.failed

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class Errors(
    @SerializedName("status")
    var status: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("details")
    var details: JsonObject? = null
)
