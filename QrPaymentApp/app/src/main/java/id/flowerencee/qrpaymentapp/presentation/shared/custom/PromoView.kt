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
import id.flowerencee.qrpaymentapp.data.model.response.promo.PromoListResponseItem
import id.flowerencee.qrpaymentapp.databinding.ItemPromoBinding
import id.flowerencee.qrpaymentapp.databinding.LayoutAccountBinding
import id.flowerencee.qrpaymentapp.databinding.LayoutPromoBinding
import id.flowerencee.qrpaymentapp.presentation.shared.support.DeLog

class PromoView : ConstraintLayout {
    companion object {
        private val TAG = PromoView::class.java.simpleName
        class AdapterPromo(
            private val listener: (PromoListResponseItem) -> Unit
        ) : RecyclerView.Adapter<AdapterPromo.ViewHolder>() {
            private val data = ArrayList<PromoListResponseItem>()

            inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                fun bind(item: PromoListResponseItem) = with(itemView) {
                    val bindData = ItemPromoBinding.bind(this)

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
                holder.bind(data[position])
            }

            fun setData(list: ArrayList<PromoListResponseItem>){
                val cbDiff = PromoCallback(data, list)
                val resultDiff = DiffUtil.calculateDiff(cbDiff)
                data.clear()
                data.addAll(list)
                resultDiff.dispatchUpdatesTo(this)
            }

            inner class PromoCallback(
                private val oldList: ArrayList<PromoListResponseItem>,
                private val newList: ArrayList<PromoListResponseItem>
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
    }

    private fun initAdapter() {
        promoAdapter = AdapterPromo{
            listener?.onClickPromo(it)
        }
        binding.rvPromo.apply {
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = promoAdapter
        }
    }

    fun setData(list: ArrayList<PromoListResponseItem>){
        DeLog.d(TAG, list.toString())
        promoAdapter?.setData(list)
    }

    fun setListener(promoListener: PromoListener) {
        listener = promoListener
    }

    interface PromoListener {
        fun onClickPromo(item: PromoListResponseItem) {}
    }
}