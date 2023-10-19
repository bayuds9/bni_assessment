package id.flowerencee.qrpaymentapp

import android.app.Application
import com.google.android.material.color.DynamicColors
import id.flowerencee.qrpaymentapp.dependency.accountUseCaseModule
import id.flowerencee.qrpaymentapp.dependency.apiModule
import id.flowerencee.qrpaymentapp.dependency.dataSourceModule
import id.flowerencee.qrpaymentapp.dependency.databaseModule
import id.flowerencee.qrpaymentapp.dependency.portfolioUseCaseModule
import id.flowerencee.qrpaymentapp.dependency.promoUseCaseModule
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
            modules(
                listOf(
                    apiModule,
                    databaseModule,
                    dataSourceModule,
                    repositoryModule,
                    transactionUseCaseModule,
                    accountUseCaseModule,
                    promoUseCaseModule,
                    portfolioUseCaseModule,
                    viewModelModule
                )
            )
        }
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}