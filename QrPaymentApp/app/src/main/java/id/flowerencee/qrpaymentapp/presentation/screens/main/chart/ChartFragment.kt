package id.flowerencee.qrpaymentapp.presentation.screens.main.chart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.data.model.response.portfolio.DoughnutData
import id.flowerencee.qrpaymentapp.databinding.FragmentChartBinding
import id.flowerencee.qrpaymentapp.presentation.shared.custom.ChartView
import id.flowerencee.qrpaymentapp.presentation.shared.extension.toHide
import id.flowerencee.qrpaymentapp.presentation.shared.extension.toSHow
import id.flowerencee.qrpaymentapp.presentation.shared.support.DeLog
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChartFragment : Fragment() {
    companion object {
        private val TAG = ChartFragment::class.java.simpleName
        fun newInstance(): ChartFragment {
            val fragment = ChartFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: FragmentChartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChartViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChartBinding.bind(
            inflater.inflate(R.layout.fragment_chart, container, false)
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
        viewModel.getPortfolio().observe(viewLifecycleOwner) { portfolio ->
            DeLog.d(TAG, "data $portfolio")
            portfolio.firstOrNull { it.type == "donutChart" }?.let { doughnut ->
                doughnut.doughnut.forEach {
                    DeLog.d(TAG, "data doughnut $it")
                }
                binding.viewCart.setData(doughnut.doughnut, doughnut.type ?: "")
            }
        }
        viewModel.chartDetail.observe(viewLifecycleOwner) {
            binding.viewText.setData(it)
        }
    }

    private fun initUi() {
        val listener = object : ChartView.ChartListener {
            override fun onClickedDoughnut(chart: DoughnutData?) {
                openDetail(chart)
            }
        }
        binding.viewCart.setListener(listener)
    }

    private fun openDetail(chart: DoughnutData?) {
        when (chart != null) {
            true -> {
                binding.viewText.apply {
                    setHeader(chart.label.toString())
                    toSHow()
                }
                viewModel.generateChartDetail(chart)
            }

            false -> {
                binding.viewText.toHide()
            }
        }
    }

}