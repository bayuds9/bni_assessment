package id.flowerencee.qrpaymentapp.presentation.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import id.flowerencee.qrpaymentapp.presentation.shared.support.BaseActivity

class ComposeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            appMainContent {
                finishAndRemoveTask()
            }
        }
    }
}