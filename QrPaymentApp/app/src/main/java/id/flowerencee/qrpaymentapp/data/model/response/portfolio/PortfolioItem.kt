package id.flowerencee.qrpaymentapp.data.model.response.portfolio

data class PortfolioItem(
    var type: String? = "",
    var doughnut: ArrayList<DoughnutData> = arrayListOf(),
    var lineCart: LineCart? = null
)