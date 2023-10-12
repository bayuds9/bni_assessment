package id.flowerencee.qrpaymentapp.presentation.shared.support

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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