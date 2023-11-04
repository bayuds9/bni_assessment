package id.flowerencee.qrpaymentapp.presentation.shared.support

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.data.model.response.failed.StatusResponse
import id.flowerencee.qrpaymentapp.presentation.shared.custom.PopUpInterface
import id.flowerencee.qrpaymentapp.presentation.shared.custom.showPopup
import id.flowerencee.qrpaymentapp.presentation.shared.`object`.DialogData

open class BaseActivity : AppCompatActivity() {
    lateinit var activityLauncher: BetterActivityResult<Intent, ActivityResult>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLauncher = BetterActivityResult.registerActivityForResult(this)
        Firebase.messaging.isAutoInitEnabled = true
        FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(true)
    }

    fun showStatusPopup(statusResponse: StatusResponse, listener: PopUpInterface? = null) {
        if (!isFinishing) {
            val title =
                statusResponse.statusCode.toString() + " - " + statusResponse.error.toString()
            val data =
                DialogData(title, statusResponse.message.toString(), getString(R.string.retry), getString(R.string.okay))
            showPopup(data, listener)
        }
    }

    fun initToolbar(
        toolbar: Toolbar,
        title: String? = null,
        iconBack: Drawable? = null,
        onBackPress: (() -> Unit)? = null
    ) {
        setSupportActionBar(toolbar)
        title?.let {
            supportActionBar?.title = it
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(false)
        toolbar.navigationIcon = iconBack
        toolbar.setNavigationOnClickListener {
            if (onBackPress == null)
                onBackPressed()
            else
                onBackPress.invoke()
        }
    }
}