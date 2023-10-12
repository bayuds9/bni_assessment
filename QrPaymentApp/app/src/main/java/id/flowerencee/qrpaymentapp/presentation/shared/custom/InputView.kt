package id.flowerencee.qrpaymentapp.presentation.shared.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputLayout
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.databinding.LayoutInputBinding

class InputView : ConstraintLayout {
    private lateinit var mContext: Context
    private lateinit var binding: LayoutInputBinding
    private var textListener: InputTextListener? = null

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
        binding = LayoutInputBinding.bind(
            LayoutInflater.from(mContext)
                .inflate(R.layout.layout_input, this, true)
        )
    }

    fun enableClearText() {
        binding.inputLabel.endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
    }

    fun setHint(title: String) {
        binding.inputLabel.hint = title
    }

    fun setMessage(message: String) {
        binding.inputLabel.isHelperTextEnabled = true
        binding.inputLabel.helperText = message
    }

    fun getTextValue() = binding.etInput.text.toString().trim()

    fun setListener(listener: InputTextListener) {
        this.textListener = listener
    }

    interface InputTextListener {
        fun afterTextChanged(textValue: String?) {}
    }
}