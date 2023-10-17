package id.flowerencee.qrpaymentapp.data.model.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "user_account")
data class UserAccount(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "account_id")
    var id: Int? = null,
    @ColumnInfo("account_name")
    var accountOwner: String? = null,
    @ColumnInfo("account_number")
    var accountNumber: String? = null,
    @ColumnInfo("account_balance")
    var balance: Double? = null
) : Parcelable
