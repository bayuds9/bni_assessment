package id.flowerencee.qrpaymentapp.presentation.shared.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.databinding.LayoutToolbarBinding

class CustomToolbar : ConstraintLayout {
    private lateinit var mContext: Context
    private lateinit var binding: LayoutToolbarBinding
    private var textListener: InputView.InputTextListener? = null

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
        binding = LayoutToolbarBinding.bind(
            LayoutInflater.from(mContext)
                .inflate(R.layout.layout_toolbar, this, true)
        )
    }

    fun setTitle(text: String) {
        binding.topAppBar.title = text
    }

    fun toolbar(): Toolbar = binding.topAppBar

}