package id.flowerencee.qrpaymentapp.data.model.response.promo


import com.google.gson.annotations.SerializedName

data class Formats(
    @SerializedName("large")
    var large: Large? = null,
    @SerializedName("medium")
    var medium: Medium? = null,
    @SerializedName("small")
    var small: Small? = null,
    @SerializedName("thumbnail")
    var thumbnail: Thumbnail? = null
)