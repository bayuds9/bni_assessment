package id.flowerencee.qrpaymentapp.presentation.shared.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.data.model.response.portfolio.DoughnutData
import id.flowerencee.qrpaymentapp.databinding.LayoutDoughnutCartBinding
import id.flowerencee.qrpaymentapp.presentation.shared.extension.roundToFloat
import id.flowerencee.qrpaymentapp.presentation.shared.getColorAttribute
import id.flowerencee.qrpaymentapp.presentation.shared.getColors
import id.flowerencee.qrpaymentapp.presentation.shared.support.DeLog

class CartView : ConstraintLayout {
    companion object {
        private val TAG = CartView::class.java.simpleName
    }
    private lateinit var mContext: Context
    private lateinit var binding: LayoutDoughnutCartBinding
    private var listener: CartListener? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        init(context, attributeSet)
    }

    private fun init(context: Context, attributeSet: AttributeSet? = null) {
        mContext = context
        binding = LayoutDoughnutCartBinding.bind(
            LayoutInflater.from(mContext)
                .inflate(R.layout.layout_doughnut_cart, this, true)
        )
        initCart()
    }

    private val cartListener = object : OnChartValueSelectedListener {
        override fun onValueSelected(e: Entry?, h: Highlight?) {
            DeLog.d(TAG, "selected entry $e")
            DeLog.d(TAG, "selected entry ${e?.data}")
            DeLog.d(TAG, "Highlight $h")
        }

        override fun onNothingSelected() {

        }

    }

    private fun initCart() {
        binding.viewCart.apply {
            animateXY(1000, 1000)
            setTouchEnabled(true)
            setDrawEntryLabels(true)
            setEntryLabelTextSize(8f)
            setHoleColor(context.getColorAttribute(com.google.android.material.R.attr.colorSurface))
            legend.isEnabled = false
            description.isEnabled = false
            holeRadius = 50f
            transparentCircleRadius = 2f
        }
    }

    fun setData(list: ArrayList<DoughnutData>, label: String) {
        binding.tvLabel.text = label
        val pieData = PieData()
        val entries = ArrayList<PieEntry>()
        val colorList = ArrayList<Int>()
        list.forEachIndexed { index, doughnutData ->
            entries.add(
                PieEntry(
                    doughnutData.percentage.roundToFloat(),
                    doughnutData.label.toString()
                )
            )
            colorList.add(context.getColors(index))
        }
        val dataSet = PieDataSet(entries, label)
        dataSet.apply {
            setDrawValues(false)
            colors = colorList
        }
        pieData.apply {
            setDrawValues(false)
            this.dataSet = dataSet
        }
        binding.viewCart.apply {
            data = pieData
            setOnChartValueSelectedListener(cartListener)
            setTouchEnabled(true)
        }
    }


    fun setListener(cartListener: CartListener) {
        listener = cartListener
    }

    interface CartListener {
        fun onItemCLicked(cart: PieChart) {}
    }
}