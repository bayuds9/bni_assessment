package id.flowerencee.qrpaymentapp.presentation.screens.transaction.receipt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.databinding.ActivityReceiptBinding
import id.flowerencee.qrpaymentapp.presentation.shared.support.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Date

class ReceiptActivity : BaseActivity() {
    private lateinit var binding: ActivityReceiptBinding
    private val viewModel: ReceiptViewModel by viewModel()

    companion object {
        fun myIntent(context: Context, transactionId: Int) =
            Intent(context, ReceiptActivity::class.java).apply {
                putExtra(EXTRA_TRANSACTION_ID, transactionId)
            }

        private const val EXTRA_TRANSACTION_ID = "EXTRA_TRANSACTION_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceiptBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBundle()
        initUi()
        initData()
    }

    private fun initBundle() {
        val transactionId = intent.getIntExtra(EXTRA_TRANSACTION_ID, 0)
        viewModel.getTransactionData(transactionId)
    }

    private fun initData() {
        viewModel.transactionField.observe(this){
            binding.receiptField.setData(it)
        }
        viewModel.transactionData.observe(this){
            val time = it.transactionTime?.let { timeLong -> Date(timeLong) }
            binding.receiptField.setHeader(it.transactionStatus!!, time.toString())
        }
    }

    private fun initUi() {
        binding.tbToolbar.apply {
            initToolbar(this.toolbar(), getString(R.string.receipt))
        }
        binding.btnClose.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
    }
}