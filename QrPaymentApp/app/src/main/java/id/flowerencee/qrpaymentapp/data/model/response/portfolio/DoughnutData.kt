package id.flowerencee.qrpaymentapp.data.model.response.portfolio

import com.google.gson.annotations.SerializedName

data class DoughnutData(
    @SerializedName("data")
    var data: List<Doughnut>? = listOf(),
    @SerializedName("label")
    var label: String? = "",
    @SerializedName("percentage")
    var percentage: String? = ""
)