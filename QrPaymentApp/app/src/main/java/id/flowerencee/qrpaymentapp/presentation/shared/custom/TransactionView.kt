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
import id.flowerencee.qrpaymentapp.data.model.entity.Transaction
import id.flowerencee.qrpaymentapp.data.model.entity.UserAccount
import id.flowerencee.qrpaymentapp.databinding.ItemTransactionBinding
import id.flowerencee.qrpaymentapp.databinding.LayoutTransactionListBinding
import id.flowerencee.qrpaymentapp.presentation.shared.extension.reformatCurrency
import id.flowerencee.qrpaymentapp.presentation.shared.extension.toHide
import java.util.Date

class TransactionView : ConstraintLayout {
    private lateinit var mContext: Context
    private lateinit var binding: LayoutTransactionListBinding
    private var transactionAdapter : TransactionAdapter? = null
    private var listener: TransactionListener? = null

    companion object {
        private val TAG = TransactionView::class.java.simpleName
        class TransactionAdapter(
            private val listener: (Transaction) -> Unit
        ) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {
            private val data = ArrayList<Transaction>()

            inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
                fun bind(item: Transaction) = with(itemView){
                    val bindData = ItemTransactionBinding.bind(this)
                    item.transactionAmount?.let {
                        bindData.tvTransactionAmount.text = it.reformatCurrency("Rp")
                    }
                    item.transactionName?.let {
                        bindData.tvTransactionName.text = it
                    }
                    item.transactionDestination?.let {
                        bindData.tvTransactionDestination.text = it
                    }
                    item.transactionTime?.let {
                        bindData.tvTransactionDate.text = Date(it).toString()
                    }
                    bindData.root.setOnClickListener {
                        listener(item)
                    }
                }
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                return ViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_transaction, parent, false)
                )
            }

            override fun getItemCount(): Int = data.size

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                holder.bind(data[position])
            }

            fun setData(list: ArrayList<Transaction>) {
                val cbDiff = TransactionCallback(data, list)
                val resultDiff = DiffUtil.calculateDiff(cbDiff)
                data.clear()
                data.addAll(list)
                resultDiff.dispatchUpdatesTo(this)
            }

            inner class TransactionCallback(
                private val oldList: ArrayList<Transaction>,
                private val newList: ArrayList<Transaction>
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
        binding = LayoutTransactionListBinding.bind(
            LayoutInflater.from(mContext)
                .inflate(R.layout.layout_transaction_list, this, true)
        )
        initAdapter()
    }

    private fun initAdapter() {
        transactionAdapter = TransactionAdapter{
            listener?.onClickTransaction(it)
        }
        binding.rvItems.apply {
            adapter = transactionAdapter
            layoutManager = LinearLayoutManager(mContext)
        }
    }

    fun setLabel(title: String) {
        binding.tvLabel.text = title
        if (title.isEmpty()) binding.tvLabel.toHide()
    }

    fun setData(list: ArrayList<Transaction>) {
        transactionAdapter?.setData(list)
    }

    fun setListener(transactionListener: TransactionListener) {
        listener = transactionListener
    }

    interface TransactionListener {
        fun onClickTransaction(transaction: Transaction)
    }
}