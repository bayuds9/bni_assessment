package id.flowerencee.qrpaymentapp.presentation.screens.main.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.data.entity.UserAccount
import id.flowerencee.qrpaymentapp.databinding.FragmentDashboardBinding
import id.flowerencee.qrpaymentapp.presentation.shared.custom.AccountView
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

    private lateinit var binding: FragmentDashboardBinding
    private val viewModel : DashboardViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.bind(
            inflater.inflate(R.layout.fragment_dashboard, container, false)
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initData()
    }

    private fun initData() {
        viewModel.loading.observe(viewLifecycleOwner){
            when(it){
                true -> {}
                false -> {}
            }
        }
        viewModel.getUserAccount().observe(viewLifecycleOwner){
            binding.accList.setData(it.toCollection(ArrayList()), 3)
        }
    }

    private fun initUi() {
        val accountListener = object : AccountView.AccountListener {
            override fun onClick(account: UserAccount?) {
                super.onClick(account)
                DeLog.d(TAG, "clicked account $account")
                when(account?.id != null) {
                    true -> {
                        DeLog.d(TAG, "go to account")
                    }
                    false -> {
                        DeLog.d(TAG, "create new account")
                        val userAccount = UserAccount(accountOwner = "sukri", accountNumber = "number", balance = 2129000.0)
                        viewModel.addAccount(userAccount)
                    }
                }
            }
        }

        binding.accList.apply {
            setListener(accountListener)
        }
        binding.tvAccount.setOnClickListener {
            val userAccount = UserAccount(accountOwner = "sukri", accountNumber = "number", balance = 2129000.0)
            viewModel.addAccount(userAccount)
        }
    }


}