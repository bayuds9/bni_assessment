package id.flowerencee.qrpaymentapp.data.model.response.portfolio

import com.google.gson.annotations.SerializedName

data class LineChart(
    @SerializedName("month")
    var month : List<Int> = listOf()
)
