package id.flowerencee.qrpaymentapp.data.model.response.promo

import com.google.gson.annotations.SerializedName

data class PromoListResponseItem(
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
    var descPromo: Any? = null,
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
    var namePromo: Any? = null,
    @SerializedName("published_at")
    var publishedAt: String? = null,
    @SerializedName("Title")
    var title: Any? = null,
    @SerializedName("updated_at")
    var updatedAt: String? = null
)