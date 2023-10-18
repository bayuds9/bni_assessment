package id.flowerencee.qrpaymentapp.presentation.shared.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.databinding.ItemTextFieldBinding
import id.flowerencee.qrpaymentapp.databinding.LayoutTextFieldsBinding
import id.flowerencee.qrpaymentapp.presentation.shared.extension.toHide
import id.flowerencee.qrpaymentapp.presentation.shared.extension.toSHow
import id.flowerencee.qrpaymentapp.presentation.shared.`object`.TextLabel

class TextFieldView : ConstraintLayout {
    private lateinit var mContext: Context
    private lateinit var binding: LayoutTextFieldsBinding
    private var textAdapter : TextAdapter? = null

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
        private val TAG = TextFieldView::class.java.simpleName

        class TextAdapter : RecyclerView.Adapter<TextAdapter.ViewHolder>() {
            val data = ArrayList<TextLabel>()

            inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                fun bind(item: TextLabel) = with(itemView) {
                    val bindData = ItemTextFieldBinding.bind(this)
                    bindData.tvValue.text = item.value
                    bindData.tvLabel.text = item.label
                    if (item.label.isEmpty()) {
                        bindData.tvLabel.toHide()
                        bindData.tvValue.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    }
                }
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                return ViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_text_field, parent, false)
                )
            }

            override fun getItemCount(): Int = data.size

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                holder.bind(data[position])
            }

            fun setData(list: ArrayList<TextLabel>) {
                data.clear()
                data.addAll(list)
                notifyDataSetChanged()
            }
        }

    }

    private fun init(context: Context, attributeSet: AttributeSet? = null) {
        mContext = context
        binding = LayoutTextFieldsBinding.bind(
            LayoutInflater.from(mContext)
                .inflate(R.layout.layout_text_fields, this, true)
        )
        initAdapter()
    }

    private fun initAdapter() {
        textAdapter = TextAdapter()
        binding.rvItems.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = textAdapter
        }
    }

    fun setHeader(status: Boolean, time: String) {
        if (!status) {
            binding.tvStatus.apply {
                MaterialColors.getColor(this, com.google.android.material.R.attr.errorTextColor)
            }
        }
        val transactionStatus = if (status) context.getString(R.string.success) else context.getString(R.string.failed)
        binding.tvStatus.text = context.getString(R.string.transaction_status, transactionStatus)
        binding.tvDateTime.text = time
        binding.tvStatus.toSHow()
        binding.tvDateTime.toSHow()
    }

    fun setHeader(text: String) {
        binding.tvStatus.text = text
        binding.tvDateTime.toHide()
        binding.tvStatus.toSHow()
    }
    fun setData(list: ArrayList<TextLabel>){
        textAdapter?.setData(list)
    }

}