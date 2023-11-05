@file:OptIn(ExperimentalMaterialApi::class)

package id.flowerencee.qrpaymentapp.presentation.ui.screen.chart

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.data.model.response.portfolio.DoughnutData
import id.flowerencee.qrpaymentapp.presentation.shared.getColors
import id.flowerencee.qrpaymentapp.presentation.ui.component.appBarComponent
import id.flowerencee.qrpaymentapp.presentation.ui.component.listTextItem

@Composable
fun chartScreen(
    viewModel: ChartsViewModel,
    onBackPress: () -> Unit
) {
    viewModel.getPortfolio()
    val portfolioList = viewModel.portfolioList.collectAsState()
    val portfolioField = viewModel.chartDetail.collectAsState()
    var screenTitle by remember {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            appBarComponent(
                title = screenTitle,
                leftIcon = R.drawable.round_arrow_back
            ) {
                onBackPress()
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                item {
                    // PIE CART
                    portfolioList.value.firstOrNull { it.type.equals("donutChart", true) }
                        ?.let { raw ->
                            pieCartContent(raw.doughnut) { chart ->
                                chart?.let {
                                    viewModel.generateChartDetail(it)
                                    if (it.label != null) screenTitle = it.label!!
                                }
                            }
                        }
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                if (portfolioField.value.isNotEmpty()) {
                    items(portfolioField.value) { data ->
                        Card(
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            shape = MaterialTheme.shapes.extraSmall,
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(4.dp)
                            ) {
                                listTextItem(data = data)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun pieCartContent(list: ArrayList<DoughnutData>, onClick: (DoughnutData?) -> Unit) {
    val context = LocalContext.current
    val doughnut = getPieChartData(list, context)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(250.dp)
                .width(250.dp),
            contentAlignment = Alignment.Center
        ) {
            DonutPieChart(modifier = Modifier.wrapContentSize(),
                pieChartData = doughnut,
                pieChartConfig = getPieCartConfig(),
                onSliceClick = { raw ->
                    list.firstOrNull { it.label == raw.label }?.let(onClick)
                })
        }
    }
}

fun getPieChartData(list: ArrayList<DoughnutData>, context: Context): PieChartData {

    val entries = ArrayList<PieChartData.Slice>()
    list.forEachIndexed { index, doughnutData ->
        val randomColors = Color(context.getColors(index))

        entries.add(
            PieChartData.Slice(
                doughnutData.label ?: "",
                (doughnutData.percentage ?: "0").toFloat(),
                randomColors
            )
        )
    }
    return PieChartData(slices = entries, PlotType.Pie)
}

@Composable
fun getPieCartConfig(): PieChartConfig {
    return PieChartConfig(
        sliceLabelTextColor = MaterialTheme.colorScheme.tertiary,
        isAnimationEnable = true,
        labelVisible = true,
        labelColor = MaterialTheme.colorScheme.surfaceDim,
        backgroundColor = MaterialTheme.colorScheme.surface,
        isEllipsizeEnabled = true,
        chartPadding = 2
    )
}