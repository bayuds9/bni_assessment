package id.flowerencee.qrpaymentapp.data.model.response.portfolio

import com.google.gson.annotations.SerializedName

data class Doughnut(
    @SerializedName("nominal")
    var nominal: Int? = null,
    @SerializedName("trx_date")
    var trxDate: String? = null
)