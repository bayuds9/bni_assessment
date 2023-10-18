package id.flowerencee.qrpaymentapp.data.model.response.promo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PromoItem(
    @SerializedName("alt")
    var alt: String? = null,
    @SerializedName("count")
    var count: Int? = null,
    @SerializedName("created_at")
    var created_At: String? = null,
    @SerializedName("createdAt")
    var createdAt: String? = null,
    @SerializedName("desc")
    var desc: String? = null,
    @SerializedName("desc_promo")
    var descPromo: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("img")
    var img: Img? = null,
    @SerializedName("latitude")
    var latitude: String? = null,
    @SerializedName("lokasi")
    var lokasi: String? = null,
    @SerializedName("longitude")
    var longitude: String? = null,
    @SerializedName("nama")
    var nama: String? = null,
    @SerializedName("name_promo")
    var namePromo: String? = null,
    @SerializedName("published_at")
    var publishedAt: String? = null,
    @SerializedName("Title")
    var title: String? = null,
    @SerializedName("updated_at")
    var updatedAt: String? = null
) : Parcelable