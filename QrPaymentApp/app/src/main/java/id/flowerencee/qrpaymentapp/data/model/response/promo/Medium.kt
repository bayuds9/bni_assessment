package id.flowerencee.qrpaymentapp.data.model.response.promo


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Medium(
    @SerializedName("ext")
    var ext: String? = null,
    @SerializedName("hash")
    var hash: String? = null,
    @SerializedName("height")
    var height: Int? = null,
    @SerializedName("mime")
    var mime: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("path")
    var path: String? = null,
    @SerializedName("size")
    var size: Double? = null,
    @SerializedName("url")
    var url: String? = null,
    @SerializedName("width")
    var width: Int? = null
) : Parcelable