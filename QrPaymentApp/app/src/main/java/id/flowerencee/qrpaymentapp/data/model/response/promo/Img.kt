package id.flowerencee.qrpaymentapp.data.model.response.promo


import com.google.gson.annotations.SerializedName

data class Img(
    @SerializedName("alternativeText")
    var alternativeText: String? = null,
    @SerializedName("caption")
    var caption: String? = null,
    @SerializedName("created_at")
    var createdAt: String? = null,
    @SerializedName("ext")
    var ext: String? = null,
    @SerializedName("formats")
    var formats: Formats? = null,
    @SerializedName("hash")
    var hash: String? = null,
    @SerializedName("height")
    var height: Int? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("mime")
    var mime: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("previewUrl")
    var previewUrl: Any? = null,
    @SerializedName("provider")
    var provider: String? = null,
    @SerializedName("provider_metadata")
    var providerMetadata: Any? = null,
    @SerializedName("size")
    var size: Double? = null,
    @SerializedName("updated_at")
    var updatedAt: String? = null,
    @SerializedName("url")
    var url: String? = null,
    @SerializedName("width")
    var width: Int? = null
)