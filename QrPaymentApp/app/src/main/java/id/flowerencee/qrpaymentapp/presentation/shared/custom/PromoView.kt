package id.flowerencee.qrpaymentapp.presentation.shared.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.data.model.response.promo.PromoItem
import id.flowerencee.qrpaymentapp.databinding.ItemPromoBinding
import id.flowerencee.qrpaymentapp.databinding.LayoutPromoBinding
import id.flowerencee.qrpaymentapp.presentation.shared.extension.animateShimmer
import id.flowerencee.qrpaymentapp.presentation.shared.extension.loadImage
import id.flowerencee.qrpaymentapp.presentation.shared.extension.toHide
import id.flowerencee.qrpaymentapp.presentation.shared.extension.toSHow
import id.flowerencee.qrpaymentapp.presentation.shared.support.DeLog

class PromoView : ConstraintLayout {
    companion object {
        private val TAG = PromoView::class.java.simpleName

        class AdapterPromo(
            private val listener: (PromoItem) -> Unit
        ) : RecyclerView.Adapter<AdapterPromo.ViewHolder>() {
            private val data = ArrayList<PromoItem>()
            private var loading = true

            inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                fun bind(item: PromoItem) = with(itemView) {
                    val bindData = ItemPromoBinding.bind(this)
                    item.img?.let { img ->
                        img.formats?.let {
                            val imgUrl = when {
                                it.small?.url != null -> it.small?.url
                                it.medium?.url != null -> it.medium?.url
                                it.thumbnail?.url != null -> it.thumbnail?.url
                                it.large?.url != null -> it.large?.url
                                else -> img.url
                            }
                            if (imgUrl != null) context.loadImage(imgUrl, bindData.imgPromo, bindData.viewLoading)
                        }
                    }
                    bindData.imgPromo.setOnClickListener {
                        listener(item)
                    }
                }

                fun bindLoading() = with(itemView) {
                    val bindData = ItemPromoBinding.bind(this)
                    bindData.viewLoading.toSHow()
                    bindData.viewLoading.animateShimmer()
                }
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                return ViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_promo, parent, false)
                )
            }

            override fun getItemCount(): Int = data.size

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                when (loading) {
                    true -> holder.bindLoading()
                    false -> holder.bind(data[position])
                }
            }

            fun setData(list: ArrayList<PromoItem>, load: Boolean = false) {
                loading = load
                val cbDiff = PromoCallback(data, list)
                val resultDiff = DiffUtil.calculateDiff(cbDiff)
                data.clear()
                data.addAll(list)
                resultDiff.dispatchUpdatesTo(this)
            }

            inner class PromoCallback(
                private val oldList: ArrayList<PromoItem>,
                private val newList: ArrayList<PromoItem>
            ) : DiffUtil.Callback() {
                override fun getOldListSize(): Int = oldList.size

                override fun getNewListSize(): Int = newList.size

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return oldList[oldItemPosition] === newList[newItemPosition]
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    val (_, value0, nameOld) = oldList[oldItemPosition]
                    val (_, value1, nameNew) = newList[newItemPosition]
                    return nameOld == nameNew && value0 == value1
                }

                @Nullable
                override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                    return super.getChangePayload(oldItemPosition, newItemPosition)
                }
            }
        }
    }

    private lateinit var mContext: Context
    private lateinit var binding: LayoutPromoBinding
    private var promoAdapter: AdapterPromo? = null
    private var listener: PromoListener? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        init(context, attributeSet)
    }

    private fun init(context: Context, attributeSet: AttributeSet? = null) {
        mContext = context
        binding = LayoutPromoBinding.bind(
            LayoutInflater.from(mContext)
                .inflate(R.layout.layout_promo, this, true)
        )
        initAdapter()
        initNoRecord()
    }

    private fun initNoRecord() {
        binding.promoRecords.root.toHide()
        with(binding.promoRecords) {
            tvNoRecord.text = mContext.getString(R.string.no_promo_to_show)
        }
    }

    private fun initAdapter() {
        promoAdapter = AdapterPromo {
            listener?.onClickPromo(it)
        }
        binding.rvPromo.apply {
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = promoAdapter
        }
    }

    fun setLoading() {
        val list = ArrayList<PromoItem>()
        repeat(5) {
            list.add(PromoItem())
        }
        arrayListOf(PromoItem())
        promoAdapter?.setData(list, true)
    }

    fun setData(list: ArrayList<PromoItem>) {
        DeLog.d(TAG, list.toString())
        when (list.isEmpty()) {
            true -> binding.promoRecords.root.toSHow()
            false -> binding.promoRecords.root.toHide()
        }
        promoAdapter?.setData(list)
    }

    fun setListener(promoListener: PromoListener) {
        listener = promoListener
    }

    interface PromoListener {
        fun onClickPromo(item: PromoItem) {}
    }
}