package id.flowerencee.qrpaymentapp.data.model.response.failed

import com.google.gson.annotations.SerializedName

data class StatusResponses(
    @SerializedName("data")
    var data: Any? = null,
    @SerializedName("error")
    var error: Errors? = null
)
