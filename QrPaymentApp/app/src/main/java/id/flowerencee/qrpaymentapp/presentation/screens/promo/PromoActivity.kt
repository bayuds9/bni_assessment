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
import id.flowerencee.qrpaymentapp.presentation.shared.extension.loadImage
import id.flowerencee.qrpaymentapp.presentation.shared.extension.toHide
import id.flowerencee.qrpaymentapp.presentation.shared.parcelable
import id.flowerencee.qrpaymentapp.presentation.shared.support.BaseActivity
import id.flowerencee.qrpaymentapp.presentation.shared.support.DeLog
import org.koin.androidx.viewmodel.ext.android.viewModel

class PromoActivity : BaseActivity() {
    companion object {
        private const val EXTRA_PROMO = "extra_promo"
        fun myIntent(context: Context, promo: PromoItem) = Intent(context, PromoActivity::class.java).apply {
            putExtra(EXTRA_PROMO, promo)
        }
    }
    private lateinit var binding: ActivityPromoBinding
    private val viewModel : PromoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPromoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBundle()
        initUi()
        initData()
    }

    private fun initBundle() {
        val promo : PromoItem? = intent.parcelable(EXTRA_PROMO)
        promo?.let {
            viewModel.setPromoData(it)
        }
    }

    private fun initData() {
        viewModel.getPromoDetail()
        viewModel.getTitle().observe(this){raw ->
            raw?.let {
                binding.tbToolbar.setTitle(it)
                binding.viewDetail.setHeader(it)
            }
        }
        viewModel.getImage().observe(this){raw ->
            raw?.let {
                loadImage(it, binding.imgPromo, binding.imgLoading, R.drawable.round_article)
            }
        }
        viewModel.promoDetail.observe(this){
            binding.viewDetail.setData(ArrayList(it))
        }
    }

    private fun initUi() {
        binding.tbToolbar.apply {
            initToolbar(this.toolbar(), getString(R.string.promo), iconBack = ContextCompat.getDrawable(this@PromoActivity, R.drawable.round_arrow_back))
        }
    }
}