package id.flowerencee.qrpayapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.flowerencee.qrpayapp.data.database.dao.TransactionDao
import id.flowerencee.qrpayapp.data.database.dao.UserAccountDao
import id.flowerencee.qrpayapp.data.entity.Transaction
import id.flowerencee.qrpayapp.data.entity.UserAccount

@Database(
    entities = [
        UserAccount::class,
        Transaction::class
    ],
    version = 1,
    exportSchema = true
)
abstract class QrPayDatabase : RoomDatabase() {
    abstract val userAccountDao: UserAccountDao
    abstract val transactionDao: TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: QrPayDatabase? = null

        fun getInstance(context: Context): QrPayDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        QrPayDatabase::class.java,
                        "qr_pay_database"
                    ).build()
                }
                return instance
            }
        }
    }
}