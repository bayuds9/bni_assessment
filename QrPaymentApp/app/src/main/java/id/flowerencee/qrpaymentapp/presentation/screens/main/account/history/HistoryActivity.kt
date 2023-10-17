package id.flowerencee.qrpaymentapp.presentation.screens.main.account.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.databinding.ActivityHistoryBinding
import id.flowerencee.qrpaymentapp.presentation.shared.support.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryActivity : BaseActivity() {
    companion object {
        fun myIntent(context: Context, accountId: Int? = null) = Intent(context, HistoryActivity::class.java).apply {
            putExtra(EXTRA_ACCOUNT_ID, accountId)
        }
        private const val EXTRA_ACCOUNT_ID = "extra_account_id"
    }
    private lateinit var binding: ActivityHistoryBinding
    private val viewModel : HistoryViewModel by viewModel()
    private var accountId : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBunle()
        initUi()
        initData()
    }

    private fun initBunle() {
        accountId = intent.getIntExtra(EXTRA_ACCOUNT_ID, -1)
        if (accountId == -1) accountId = null
    }

    private fun initData() {
        viewModel.getTransactions(accountId).observe(this){
            binding.listTransaction.setData(ArrayList(it))
        }
    }

    private fun initUi() {
        binding.tbToolbar.apply {
            initToolbar(this.toolbar(), getString(R.string.transaction_history), ContextCompat.getDrawable(context, R.drawable.round_arrow_back))
        }
        binding.listTransaction.setLabel("")
    }
}