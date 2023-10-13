package id.flowerencee.qrpaymentapp.presentation.shared.custom

import android.app.Activity
import android.content.Context
import android.media.tv.interactive.AppLinkInfo
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputLayout
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.databinding.LayoutInputBinding
import id.flowerencee.qrpaymentapp.presentation.shared.extension.hideSoftKeyboard
import id.flowerencee.qrpaymentapp.presentation.shared.extension.reformatCurrency
import id.flowerencee.qrpaymentapp.presentation.shared.support.DeLog

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

    enum class TYPE { TEXT, CURRENCY, DROPDOWN }
    private var inputType = TYPE.TEXT
    private var currentCurrency = ""
    private var activity : Activity? = null

    private fun init(context: Context, attributeSet: AttributeSet? = null) {
        mContext = context
        binding = LayoutInputBinding.bind(
            LayoutInflater.from(mContext)
                .inflate(R.layout.layout_input, this, true)
        )
        binding.etInput.addTextChangedListener(textWatcher)
        binding.actInput.setOnClickListener {
            activity?.hideSoftKeyboard()
        }
        binding.actInput.doAfterTextChanged { textListener?.afterTextChanged(it?.toString()) }
    }

    fun setActivity(act: Activity) {
        activity = act
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            when {
                inputType == TYPE.CURRENCY -> {
                    if (s.toString() != currentCurrency) {
                        binding.etInput.removeTextChangedListener(this)

                        val cleanString = s.toString()
                            .replace("([^0-9.,])+".toRegex(), "")
                            .replace(",", "")
                            .replace(".", "")

                        var formatted = ""
                        if (cleanString.isNotEmpty()) {
                            val parsed = cleanString.toDouble()
                            formatted = parsed.reformatCurrency()
                            currentCurrency = formatted
                        } else {
                            currentCurrency = ""
                        }

                        binding.etInput.setText(formatted)
                        try {
                            binding.etInput.setSelection(formatted.length)
                        } catch (_: IndexOutOfBoundsException) {
                        }

                        binding.etInput.addTextChangedListener(this)
                    }
                }
                inputType == TYPE.DROPDOWN -> {
                    activity?.hideSoftKeyboard()
                }
                else -> {}
            }

            textListener?.afterTextChanged(s?.toString())
        }

    }

    fun enableClearText() {
        binding.inputLabel.endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
    }

    fun setType(type: TYPE, data: Any? = null) {
        inputType = type
        when(type) {
            TYPE.TEXT -> {
                binding.etInput.inputType = InputType.TYPE_CLASS_TEXT
            }
            TYPE.CURRENCY -> {
                binding.etInput.inputType = InputType.TYPE_CLASS_NUMBER
            }
            TYPE.DROPDOWN -> {
                when(data){
                    is List<*> -> {
                        binding.inputLabel.visibility = View.GONE
                        binding.ddLabel.visibility = View.VISIBLE
                        binding.ddLabel.hint = binding.inputLabel.hint
                        val items = data as List<String>
                        val adapter = ArrayAdapter(mContext, R.layout.list_item, items)
                        (binding.ddLabel.editText as? AutoCompleteTextView)?.setAdapter(adapter)
                    }
                }
            }
        }
    }

    fun setHint(title: String) {
        binding.inputLabel.hint = title
    }

    fun setMessage(message: String) {
        binding.inputLabel.isHelperTextEnabled = true
        binding.inputLabel.helperText = message
    }

    fun getTextValue() = when(inputType){
        TYPE.CURRENCY -> binding.etInput.text.toString().trim().replace(".", "")
        TYPE.DROPDOWN -> binding.actInput.text.toString()
        else -> binding.etInput.text.toString().trim()
    }

    fun setListener(listener: InputTextListener) {
        this.textListener = listener
    }

    interface InputTextListener {
        fun afterTextChanged(textValue: String?) {}
    }
}