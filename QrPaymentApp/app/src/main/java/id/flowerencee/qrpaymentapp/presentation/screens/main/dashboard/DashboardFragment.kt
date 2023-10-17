package id.flowerencee.qrpaymentapp.presentation.screens.main.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.data.model.entity.Transaction
import id.flowerencee.qrpaymentapp.databinding.FragmentDashboardBinding
import id.flowerencee.qrpaymentapp.presentation.screens.main.MainActivity
import id.flowerencee.qrpaymentapp.presentation.screens.main.account.history.HistoryActivity
import id.flowerencee.qrpaymentapp.presentation.screens.transaction.receipt.ReceiptActivity
import id.flowerencee.qrpaymentapp.presentation.shared.custom.TransactionView
import id.flowerencee.qrpaymentapp.presentation.shared.extension.toHide
import id.flowerencee.qrpaymentapp.presentation.shared.extension.toSHow
import id.flowerencee.qrpaymentapp.presentation.shared.support.DeLog
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment() {
    companion object {
        private val TAG = DashboardFragment::class.java.simpleName
        fun newInstance(): DashboardFragment {
            val fragment = DashboardFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.bind(
            inflater.inflate(R.layout.fragment_dashboard, container, false)
        )
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initData()
    }

    private fun initData() {
        viewModel.getAllPromo().observe(viewLifecycleOwner){
            DeLog.d(TAG, "response $it")
        }
        viewModel.getStatus().observe(viewLifecycleOwner){
            DeLog.d(TAG, "status responsr $it")
        }
        viewModel.getLastTransaction(10).observe(viewLifecycleOwner) {
            binding.listTransaction.setData(ArrayList(it))
            when (it.isEmpty()) {
                true -> binding.containerEmptyHistory.toSHow()
                false -> binding.containerEmptyHistory.toHide()
            }
        }
    }

    private fun initUi() {
        val transactionListener = object : TransactionView.TransactionListener {
            override fun onClickTransaction(transaction: Transaction) {
                transaction.id?.let {
                    with((activity as MainActivity)) {
                        activityLauncher.launch(ReceiptActivity.myIntent(this, it))
                    }
                }
            }

        }
        binding.listTransaction.apply {
            setLabel(getString(R.string.last_transaction))
            setListener(transactionListener)
        }
        binding.tvSeeAll.setOnClickListener {
            with((activity as MainActivity)) {
                activityLauncher.launch(HistoryActivity.myIntent(this))
            }
        }
    }

}