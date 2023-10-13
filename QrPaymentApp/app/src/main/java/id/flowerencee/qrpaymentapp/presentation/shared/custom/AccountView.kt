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
import id.flowerencee.qrpaymentapp.data.entity.UserAccount
import id.flowerencee.qrpaymentapp.databinding.ItemAccountBinding
import id.flowerencee.qrpaymentapp.databinding.LayoutAccountBinding
import id.flowerencee.qrpaymentapp.presentation.shared.extension.reformatCurrency

class AccountView : ConstraintLayout {
    private lateinit var mContext: Context
    private lateinit var binding: LayoutAccountBinding
    private var listener: AccountListener? = null
    private var accountAdapter: AccountAdapter? = null

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

    companion object {
        private val TAG = AccountView::class.java.simpleName

        class AccountAdapter(private val listener: (UserAccount) -> Unit) :
            RecyclerView.Adapter<AccountAdapter.ViewHolder>() {
            val data = ArrayList<UserAccount>()

            inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                fun bind(item: UserAccount) = with(itemView) {
                    val bindData = ItemAccountBinding.bind(this)
                    item.accountNumber?.let {
                        bindData.tvNumber.text = it.chunked(4).joinToString(" ")
                    }
                    item.accountOwner?.let {
                        bindData.tvName.text = it
                    }
                    item.balance?.let {
                        bindData.tvBalance.text = it.reformatCurrency("Rp")
                    }
                    when (item.id != null) {
                        true -> {
                            bindData.containerAccount.visibility = View.VISIBLE
                            bindData.containerEmpty.visibility = View.GONE
                        }
                        false -> {
                            bindData.containerAccount.visibility = View.INVISIBLE
                            bindData.containerEmpty.visibility = View.VISIBLE
                        }
                    }
                    bindData.root.setOnClickListener { listener(item) }
                }

            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                return ViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_account, parent, false)
                )
            }

            override fun getItemCount(): Int = data.size

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                holder.bind(data[position])
            }


            fun setData(list: ArrayList<UserAccount>) {
                val cbDiff = AccountCallback(data, list)
                val resultDif = DiffUtil.calculateDiff(cbDiff)
                data.clear()
                data.addAll(list)
                resultDif.dispatchUpdatesTo(this)
            }

            inner class AccountCallback(
                private val oldList: ArrayList<UserAccount>,
                private val newList: ArrayList<UserAccount>
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

    private fun init(context: Context, attributeSet: AttributeSet? = null) {
        mContext = context
        binding = LayoutAccountBinding.bind(
            LayoutInflater.from(mContext)
                .inflate(R.layout.layout_account, this, true)
        )
        initAdapter()
    }

    private fun initAdapter() {
        accountAdapter = AccountAdapter {
            listener?.onClick(it)
        }
        binding.rvItems.apply {
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = accountAdapter
        }
    }

    fun setData(data: ArrayList<UserAccount>, limit: Int? = null) {
        val list = if (limit != null) {
            when (data.size > limit) {
                true -> data.take(limit).toCollection(ArrayList())
                false -> data
            }
        } else data
        accountAdapter?.setData(list)
    }

    fun setListener(accountListener: AccountListener) {
        listener = accountListener
    }

    interface AccountListener {
        fun onClick(account: UserAccount? = null) {}
    }
}