package id.flowerencee.qrpaymentapp

import android.app.Application
import com.google.android.material.color.DynamicColors
import id.flowerencee.qrpaymentapp.dependency.accountUseCaseModule
import id.flowerencee.qrpaymentapp.dependency.databaseModule
import id.flowerencee.qrpaymentapp.dependency.repositoryModule
import id.flowerencee.qrpaymentapp.dependency.transactionUseCaseModule
import id.flowerencee.qrpaymentapp.dependency.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TheApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TheApp)
            modules(listOf(databaseModule, repositoryModule, transactionUseCaseModule, accountUseCaseModule, viewModelModule))
        }
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}