package id.flowerencee.qrpaymentapp.presentation.screens.transaction.inquiry

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.core.view.isVisible
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.databinding.ActivityInquiryBinding
import id.flowerencee.qrpaymentapp.presentation.shared.custom.InputView
import id.flowerencee.qrpaymentapp.presentation.shared.custom.PopUpInterface
import id.flowerencee.qrpaymentapp.presentation.shared.custom.showPopup
import id.flowerencee.qrpaymentapp.presentation.shared.extension.hideSoftKeyboard
import id.flowerencee.qrpaymentapp.presentation.shared.extension.reformatCurrency
import id.flowerencee.qrpaymentapp.presentation.shared.extension.toHide
import id.flowerencee.qrpaymentapp.presentation.shared.extension.toSHow
import id.flowerencee.qrpaymentapp.presentation.shared.`object`.DialogData
import id.flowerencee.qrpaymentapp.presentation.shared.`object`.TextLabel
import id.flowerencee.qrpaymentapp.presentation.shared.support.BaseActivity
import id.flowerencee.qrpaymentapp.presentation.shared.support.DeLog
import org.koin.androidx.viewmodel.ext.android.viewModel

class InquiryActivity : BaseActivity() {
    private lateinit var binding: ActivityInquiryBinding
    private val viewModel: InquiryViewModel by viewModel()

    companion object {
        private val TAG = InquiryActivity::class.java.simpleName
        private const val EXTRA_QR = "EXTRA_QR"
        fun myIntent(context: Context, qrData: String) = Intent(context, InquiryActivity::class.java).apply {
            putExtra(EXTRA_QR, qrData)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInquiryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBundle()
        initUi()
        initData()
    }

    private fun initBundle() {
        val qrData = intent.getStringExtra(EXTRA_QR) ?: ""
        viewModel.setQrData(qrData)
    }

    private fun initData() {
        viewModel.inquiryData.observe(this){
            binding.inquiryData.setData(it)
        }
        viewModel.getSof().observe(this){
            val listString = ArrayList<String>()
            it.forEach { userAccount ->
                listString.add("${userAccount.accountNumber}-${userAccount.accountOwner} \n${userAccount.balance?.reformatCurrency("Rp")}")
            }
            binding.inputSof.setType(InputView.TYPE.DROPDOWN, listString)
        }
        viewModel.status.observe(this){
            when(it) {
                true -> {
                    binding.tbToolbar.setTitle(getString(R.string.receipt))
                    binding.btnNext.text = getString(R.string.close)
                }
                false -> {
                    val data = DialogData("Transaction Failed", positive = getString(R.string.close))
                    showPopup(data)
                }
            }
        }
    }

    private fun initUi() {
        binding.inputSof.apply {
            setActivity(this@InquiryActivity)
            setHint(getString(R.string.select_sof))
            setListener(object : InputView.InputTextListener {
                override fun afterTextChanged(textValue: String?) {
                    validateButton()
                    DeLog.d("HAHA", "text $textValue")
                }
            })
        }
        binding.inputAmount.apply {
            setType(InputView.TYPE.CURRENCY, "")
            setHint(getString(R.string.payment_amount))
            enableClearText()
            setListener(object : InputView.InputTextListener {
                override fun afterTextChanged(textValue: String?) {
                    validateButton()
                    DeLog.d("HAHA", "text $textValue")
                }
            })
        }
        validateButton()
        binding.btnNext.setOnClickListener {
            when(binding.btnNext.text) {
                getString(R.string.next) -> {
                    val transactionAmount = binding.inputAmount.getTextValue().toDouble()
                    viewModel.inquiryTransaction(transactionAmount, binding.inputSof.getTextValue())
                    binding.btnCancel.toSHow()
                    binding.inputAmount.toHide()
                    binding.inputSof.toHide()
                    binding.tbToolbar.setTitle(getString(R.string.confirmation))
                    binding.btnNext.text = getString(R.string.confirm)
                }
                getString(R.string.confirm) -> {
                    viewModel.executeTransaction()
                    binding.btnCancel.toHide()
                }
                else -> {
                    setResult(RESULT_OK)
                    finish()
                }
            }
            hideSoftKeyboard()
        }
        binding.btnCancel.setOnClickListener {
            hideSoftKeyboard()
            onBackPressed()
        }
    }

    private fun validateButton() {

        with(binding){
            DeLog.d("validate", "VALUE SOF ${inputSof.getTextValue()} ${inputSof.getTextValue().isNotEmpty()}")
            DeLog.d("validate", "VALUE AMOUNT ${inputAmount.getTextValue()} ${inputAmount.getTextValue().isNotEmpty()}")
            btnNext.isEnabled = inputSof.getTextValue().isNotEmpty() && inputAmount.getTextValue().isNotEmpty()
        }
    }

    override fun onBackPressed() {
        val dialogData = DialogData(
            getString(R.string.warning),
            getString(R.string.cancel_transaction),
            getString(R.string.cancel),
            getString(R.string.yes),
            icon = R.drawable.round_delete_outline
        )
        val listener = object : PopUpInterface {
            override fun onNegative() {
                super.onNegative()
                setResult(RESULT_CANCELED)
                finish()
            }
        }
        showPopup(dialogData, listener)
    }
}