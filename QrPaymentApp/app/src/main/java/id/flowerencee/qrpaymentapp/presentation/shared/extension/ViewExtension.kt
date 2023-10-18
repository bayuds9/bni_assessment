package id.flowerencee.qrpaymentapp.presentation.shared.extension

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.animation.LinearInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import id.flowerencee.qrpaymentapp.R

val FragmentManager.animatedTransaction: FragmentTransaction
    get() = this.beginTransaction().setCustomAnimations(
        R.anim.enter_from_right,
        R.anim.exit_to_right,
        R.anim.enter_from_right,
        R.anim.exit_to_right
    )

fun Activity.hideSoftKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun View.toHide() {
    if (this.visibility != View.GONE) this.visibility = View.GONE
}

fun View.toSHow() {
    if (this.visibility != View.VISIBLE) this.visibility = View.VISIBLE
}

fun View.animateShimmer() {
    val animator = ObjectAnimator.ofFloat(this, "alpha", 0.2f, 1.0f, 0.2f)
    animator.duration = 2000
    animator.repeatCount = ValueAnimator.INFINITE
    animator.interpolator = LinearInterpolator()
    animator.start()
}

fun View.stopShimmer() {
    val animator = ObjectAnimator.ofFloat(this, "alpha", 0.2f, 1.0f, 0.2f)
    animator.cancel()
}

fun Context.loadImage(
    source: String,
    targetView: ImageView,
    replaced: View? = null,
    defaultImage: Int? = null
) {
    Glide.with(this)
        .load(source)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .skipMemoryCache(true)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>,
                isFirstResource: Boolean
            ): Boolean {
                defaultImage?.let {
                    targetView.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@loadImage,
                            it
                        )
                    )
                }
                return true
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>?,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                replaced?.stopShimmer()
                replaced?.toHide()
                return false
            }

        })
        .into(targetView)
}