package id.flowerencee.qrpaymentapp.data.model.response.failed


import com.google.gson.annotations.SerializedName

data class StatusResponse(
    @SerializedName("error")
    var error: String? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("statusCode")
    var statusCode: Int? = null,
    var success: Boolean = false
)