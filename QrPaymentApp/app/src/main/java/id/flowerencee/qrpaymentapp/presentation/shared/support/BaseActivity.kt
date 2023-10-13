package id.flowerencee.qrpaymentapp.presentation.shared.support

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

open class BaseActivity : AppCompatActivity() {
    lateinit var activityLauncher: BetterActivityResult<Intent, ActivityResult>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLauncher = BetterActivityResult.registerActivityForResult(this)
    }

    fun goToDeviceSetting() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri =
            Uri.fromParts(
                "package",
                this@BaseActivity.packageName,
                null
            )
        intent.data = uri
        activityLauncher.launch(intent){
            DeLog.d("BaseActivity", "Permission Result ${it.resultCode}")
        }
    }

    fun initToolbar(toolbar: Toolbar, title: String? = null, iconBack: Drawable? = null, onBackPress : (() -> Unit)? = null) {
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