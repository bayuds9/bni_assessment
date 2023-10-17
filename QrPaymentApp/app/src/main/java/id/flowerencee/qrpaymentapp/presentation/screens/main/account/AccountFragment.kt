package id.flowerencee.qrpaymentapp.presentation.screens.main.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.data.model.entity.UserAccount
import id.flowerencee.qrpaymentapp.databinding.FragmentAccountBinding
import id.flowerencee.qrpaymentapp.presentation.screens.main.MainActivity
import id.flowerencee.qrpaymentapp.presentation.screens.main.account.history.HistoryActivity
import id.flowerencee.qrpaymentapp.presentation.shared.custom.AccountView
import id.flowerencee.qrpaymentapp.presentation.shared.custom.PopUpInterface
import id.flowerencee.qrpaymentapp.presentation.shared.custom.showChallengePopup
import id.flowerencee.qrpaymentapp.presentation.shared.custom.showCreateAccountPopup
import id.flowerencee.qrpaymentapp.presentation.shared.support.DeLog
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : Fragment() {
    companion object {
        private val TAG = AccountFragment::class.java.simpleName
        fun newInstance(): AccountFragment {
            val fragment = AccountFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AccountViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.bind(
            inflater.inflate(R.layout.fragment_account, container, false)
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
        viewModel.getAllAccount().observe(viewLifecycleOwner) {
            DeLog.d("haha", "row id $it")
            binding.accountView.setData(ArrayList(it))
        }
        viewModel.success.observe(viewLifecycleOwner) {
            val message = when (it) {
                true -> "Success"
                else -> "Failed"
            }
            Toast.makeText(
                requireContext(),
                "${getString(R.string.create_account)} $message",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun initUi() {
        val manager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val listener = object : AccountView.AccountListener {
            override fun onClick(account: UserAccount?) {
                with((activity as MainActivity)){
                    activityLauncher.launch(HistoryActivity.myIntent(this, account?.id))
                }
            }
        }
        binding.accountView.apply {
            setLayoutManager(manager)
            setListener(listener)
        }
        binding.btnAddAccount.setOnClickListener {
            val popupListener = object : PopUpInterface {
                override fun onCreateAccount(name: String, number: String, balance: Double) {
                    super.onCreateAccount(name, number, balance)
                    when (name.isNotEmpty() && number.isNotEmpty() && balance > 0.0) {
                        true -> {
                            val account = UserAccount(
                                accountOwner = name,
                                accountNumber = number,
                                balance = balance
                            )
                            challengePopup(account)
                        }

                        false -> Toast.makeText(
                            requireContext(),
                            "Invalid Data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            requireActivity().showCreateAccountPopup(popupListener)
        }
    }

    private fun challengePopup(account: UserAccount) {
        val popupListener = object : PopUpInterface {
            override fun onResult(success: Boolean) {
                when (success) {
                    true -> viewModel.createAccount(account)
                    false -> Toast.makeText(
                        requireContext(),
                        "Challenge failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        requireActivity().showChallengePopup(popupListener)
    }
}