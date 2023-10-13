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
        viewModel.loading.observe(viewLifecycleOwner) {
            when (it) {
                true -> {}
                false -> {}
            }
        }
        viewModel.getUserAccount().observe(viewLifecycleOwner) {
            when(it.isEmpty()){
                true -> {
                    binding.containerEmpty.toSHow()
                }
                false -> {
                    binding.containerEmpty.toHide()
                    binding.accList.setData(it.toCollection(ArrayList()), 3)
                }
            }
        }
    }

    private fun initUi() {
        val accountListener = object : AccountView.AccountListener {
            override fun onClick(account: UserAccount?) {
                super.onClick(account)
                DeLog.d(TAG, "clicked account $account")
            }
        }

        binding.accList.apply {
            setListener(accountListener)
        }
        binding.fabAddAccount.setOnClickListener {
            val userAccount = UserAccount(
                accountOwner = "sukri",
                accountNumber = "6765756577644",
                balance = 2129000.0
            )
            viewModel.addAccount(userAccount)
        }
        binding.btnPlus.setOnClickListener {
            val userAccount = UserAccount(
                accountOwner = "sukri",
                accountNumber = "6765756577644",
                balance = 2129000.0
            )
            viewModel.addAccount(userAccount)
        }
    }

}