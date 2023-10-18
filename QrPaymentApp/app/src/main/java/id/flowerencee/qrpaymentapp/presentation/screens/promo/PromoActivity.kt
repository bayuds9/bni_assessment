package id.flowerencee.qrpaymentapp.presentation.screens.promo

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.data.model.response.promo.PromoItem
import id.flowerencee.qrpaymentapp.databinding.ActivityPromoBinding
import id.flowerencee.qrpaymentapp.presentation.shared.extension.toHide
import id.flowerencee.qrpaymentapp.presentation.shared.parcelable
import id.flowerencee.qrpaymentapp.presentation.shared.support.BaseActivity

class PromoActivity : BaseActivity() {
    companion object {
        private const val EXTRA_PROMO = "extra_promo"
        fun myIntent(context: Context, promo: PromoItem) = Intent(context, PromoActivity::class.java).apply {
            putExtra(EXTRA_PROMO, promo)
        }
    }
    private lateinit var binding: ActivityPromoBinding
    private var promo : PromoItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPromoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBundle()
        initUi()
        initData()
    }

    private fun initBundle() {
        promo = intent.parcelable(EXTRA_PROMO)
    }

    private fun initData() {
        promo?.img?.let { img ->
            img.formats?.let {
                val imgUrl = when {
                    it.large?.url != null -> it.large?.url
                    it.medium?.url != null -> it.medium?.url
                    it.small?.url != null -> it.small?.url
                    it.thumbnail?.url != null -> it.thumbnail?.url
                    else -> img.url
                }
                Glide.with(this)
                    .load(imgUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(true)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>,
                            isFirstResource: Boolean
                        ): Boolean {
                            binding.imgPromo.setImageDrawable(ContextCompat.getDrawable(this@PromoActivity, R.drawable.round_article))
                            return true
                        }

                        override fun onResourceReady(
                            resource: Drawable,
                            model: Any,
                            target: Target<Drawable>?,
                            dataSource: DataSource,
                            isFirstResource: Boolean
                        ): Boolean {
                            binding.imgLoading.toHide()
                            return false
                        }

                    })
                    .into(binding.imgPromo)
            }
        }
    }

    private fun initUi() {

    }
}