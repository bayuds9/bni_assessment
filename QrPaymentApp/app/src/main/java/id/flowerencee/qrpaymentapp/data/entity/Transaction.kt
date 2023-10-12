package id.flowerencee.qrpaymentapp.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity("transaction")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transaction_id")
    var id: Int? = null,
    @ColumnInfo(name = "transaction_name")
    var transactionName: String? = null,
    @ColumnInfo(name = "transaction_amount")
    var transactionAmount: Double? = null,
    @ColumnInfo(name = "transaction_source")
    var transactionSource: Int? = null,
    @ColumnInfo(name = "transaction_code")
    var transactionCode: Int? = null,
    @ColumnInfo(name = "transaction_destination")
    var transactionDestination: String? = null,
    @ColumnInfo(name = "transaction_time")
    var transactionTime: Long? = null,
    @ColumnInfo(name = "transaction_status")
    var transactionStatus: Boolean? = null
) : Parcelable