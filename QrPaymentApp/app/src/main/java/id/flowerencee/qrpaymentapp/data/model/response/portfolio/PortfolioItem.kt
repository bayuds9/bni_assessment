package id.flowerencee.qrpaymentapp.data.model.response.portfolio

import com.google.gson.JsonObject

data class PortfolioItem(
    var type: String? = "",
    var doughnut: ArrayList<DoughnutData> = arrayListOf(),
    var lineCart: JsonObject = JsonObject()
)