package id.flowerencee.qrpaymentapp.presentation.screens.main.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.databinding.FragmentCartBinding
import id.flowerencee.qrpaymentapp.presentation.shared.custom.CartView
import id.flowerencee.qrpaymentapp.presentation.shared.support.DeLog
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : Fragment() {
    companion object {
        private val TAG = CartFragment::class.java.simpleName
        fun newInstance() : CartFragment {
            val fragment = CartFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel : CartViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.bind(
            inflater.inflate(R.layout.fragment_cart, container, false)
        )
        return binding?.root
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
        viewModel.getPortfolio().observe(viewLifecycleOwner){portfolio ->
            DeLog.d(TAG, "data $portfolio")
            portfolio.firstOrNull { it.type == "donutChart" }?.let {doughnut ->
                doughnut.doughnut.forEach {
                    DeLog.d(TAG, "data doughnut $it")
                }
                binding.viewCart.setData(doughnut.doughnut, doughnut.type ?: "")
            }
        }
    }

    private fun initUi() {
        val listener = object : CartView.CartListener {

        }
        binding.viewCart.setListener(listener)
    }

}