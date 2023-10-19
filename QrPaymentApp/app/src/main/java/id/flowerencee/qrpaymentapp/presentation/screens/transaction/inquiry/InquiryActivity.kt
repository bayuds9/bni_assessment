package id.flowerencee.qrpaymentapp.presentation.screens.transaction.inquiry

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.databinding.ActivityInquiryBinding
import id.flowerencee.qrpaymentapp.presentation.screens.transaction.receipt.ReceiptActivity
import id.flowerencee.qrpaymentapp.presentation.shared.custom.InputView
import id.flowerencee.qrpaymentapp.presentation.shared.custom.PopUpInterface
import id.flowerencee.qrpaymentapp.presentation.shared.custom.showChallengePopup
import id.flowerencee.qrpaymentapp.presentation.shared.custom.showPopup
import id.flowerencee.qrpaymentapp.presentation.shared.extension.hideSoftKeyboard
import id.flowerencee.qrpaymentapp.presentation.shared.extension.reformatCurrency
import id.flowerencee.qrpaymentapp.presentation.shared.extension.toHide
import id.flowerencee.qrpaymentapp.presentation.shared.extension.toSHow
import id.flowerencee.qrpaymentapp.presentation.shared.`object`.DialogData
import id.flowerencee.qrpaymentapp.presentation.shared.support.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class InquiryActivity : BaseActivity() {
    private lateinit var binding: ActivityInquiryBinding
    private val viewModel: InquiryViewModel by viewModel()

    companion object {
        private val TAG = InquiryActivity::class.java.simpleName
        private const val EXTRA_QR = "extra_qr"
        fun myIntent(context: Context, qrData: String) =
            Intent(context, InquiryActivity::class.java).apply {
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
        viewModel.inquiryData.observe(this) {
            binding.inquiryData.setData(it)
        }
        viewModel.getSof().observe(this) {
            when (it.isNotEmpty()) {
                true -> {
                    val listString = ArrayList<String>()
                    it.forEach { userAccount ->
                        listString.add(
                            "${userAccount.accountNumber}-${userAccount.accountOwner} \n${
                                userAccount.balance?.reformatCurrency(
                                    "Rp"
                                )
                            }"
                        )
                    }
                    binding.inputSof.setType(InputView.TYPE.DROPDOWN, listString)
                }

                false -> {
                    showEmptySof()
                }
            }
        }
        viewModel.transactionId.observe(this) {
            activityLauncher.launch(ReceiptActivity.myIntent(this, it))
            setResult(RESULT_OK)
            finish()
        }
    }

    private fun showEmptySof() {
        val listener = object : PopUpInterface {
            override fun onPositive() {
                setResult(RESULT_OK)
                finish()
            }
        }
        val popUpData = DialogData(
            getString(R.string.warning),
            getString(R.string.no_account),
            positive = getString(R.string.okay)
        )
        showPopup(popUpData, listener)
    }

    private fun initUi() {
        binding.tbToolbar.apply {
            initToolbar(
                this.toolbar(),
                getString(R.string.inquiry),
                ContextCompat.getDrawable(context, R.drawable.round_arrow_back)
            )
        }
        binding.inputSof.apply {
            setActivity(this@InquiryActivity)
            setHint(getString(R.string.select_sof))
            setListener(object : InputView.InputTextListener {
                override fun afterTextChanged(textValue: String?) {
                    validateButton()
                }
            })
        }
        binding.inputAmount.apply {
            setType(InputView.TYPE.CURRENCY, viewModel.getBillerAmount())
            setHint(getString(R.string.payment_amount))
            enableClearText()
            setListener(object : InputView.InputTextListener {
                override fun afterTextChanged(textValue: String?) {
                    validateButton()
                }
            })
        }
        validateButton()
        binding.btnNext.setOnClickListener {
            when (binding.btnNext.text) {
                getString(R.string.next) -> {
                    val transactionAmount = binding.inputAmount.getTextValue().toDouble()
                    viewModel.inquiryTransaction(transactionAmount, binding.inputSof.getTextValue())
                    binding.btnCancel.toSHow()
                    binding.inputAmount.toHide()
                    binding.inputSof.toHide()
                    binding.tbToolbar.apply {
                        initToolbar(this.toolbar(), getString(R.string.inquiry))
                    }
                    binding.btnNext.text = getString(R.string.confirm)
                }

                getString(R.string.confirm) -> {
                    showValidation()
                }
            }
            hideSoftKeyboard()
        }
        binding.btnCancel.setOnClickListener {
            hideSoftKeyboard()
            onBackPressed()
        }
    }

    private fun showValidation() {
        val listener = object : PopUpInterface {
            override fun onResult(success: Boolean) {
                when (success) {
                    true -> viewModel.executeTransaction()
                    false -> Toast.makeText(
                        this@InquiryActivity,
                        "Challenge failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        showChallengePopup(listener = listener)
    }

    private fun validateButton() {
        with(binding) {
            btnNext.isEnabled =
                inputSof.getTextValue().isNotEmpty() && inputAmount.getTextValue().isNotEmpty()
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
                setResult(RESULT_CANCELED)
                finish()
            }
        }
        showPopup(dialogData, listener)
    }
}