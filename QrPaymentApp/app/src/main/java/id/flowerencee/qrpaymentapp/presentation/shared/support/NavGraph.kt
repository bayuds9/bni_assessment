package id.flowerencee.qrpaymentapp.presentation.shared.support

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.data.model.Constant
import id.flowerencee.qrpaymentapp.data.model.response.promo.PromoItem
import id.flowerencee.qrpaymentapp.presentation.shared.`object`.MenuData
import id.flowerencee.qrpaymentapp.presentation.ui.screen.about.aboutScreen
import id.flowerencee.qrpaymentapp.presentation.ui.screen.chart.ChartsViewModel
import id.flowerencee.qrpaymentapp.presentation.ui.screen.chart.chartScreen
import id.flowerencee.qrpaymentapp.presentation.ui.screen.detail.DetailViewModel
import id.flowerencee.qrpaymentapp.presentation.ui.screen.detail.detailScreen
import id.flowerencee.qrpaymentapp.presentation.ui.screen.history.HistoriesViewModel
import id.flowerencee.qrpaymentapp.presentation.ui.screen.history.historyScreen
import id.flowerencee.qrpaymentapp.presentation.ui.screen.home.HomeScreen
import id.flowerencee.qrpaymentapp.presentation.ui.screen.promo.PromosViewModel
import id.flowerencee.qrpaymentapp.presentation.ui.screen.promo.promoScreen
import id.flowerencee.qrpaymentapp.presentation.ui.screen.promo.promodetail.PromoDetailViewModel
import id.flowerencee.qrpaymentapp.presentation.ui.screen.promo.promodetail.promoDetailScreen
import id.flowerencee.qrpaymentapp.presentation.ui.screen.scanner.scannerScreen
import id.flowerencee.qrpaymentapp.presentation.ui.screen.transaction.TransactionViewModel
import id.flowerencee.qrpaymentapp.presentation.ui.screen.transaction.transactionScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    closeApp: () -> Unit = {},
    navController: NavHostController = rememberNavController(),
    initScreen: String = RouteTo.MENU.screen
) {
    val menus = getAllMenu()

    NavHost(navController = navController, startDestination = initScreen, modifier = modifier) {
        composable(RouteTo.MENU.screen) {
            BackHandler {
                closeApp.invoke()
            }

            HomeScreen(listMenu = menus) { menu ->
                navController.navigate(menu.route)
            }
        }

        composable(route = RouteTo.SCANNER.screen) {
            BackHandler {
                navController.navigateUp()
            }
            scannerScreen(navController) {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    Constant.DATA.QR_DATA,
                    it
                )
                navController.navigate(RouteTo.TRANSACTION.screen)
            }
        }

        composable(route = RouteTo.TRANSACTION.screen) {
            BackHandler {
                navController.navigateUp()
            }

            val qrMetaData =
                navController.previousBackStackEntry?.savedStateHandle?.get<String>(Constant.DATA.QR_DATA)
            val transactionViewModel: TransactionViewModel = koinViewModel()
            qrMetaData?.let {
                transactionScreen(
                    navController,
                    viewModel = transactionViewModel,
                    qrData = it
                ) { transactionId: Int ->
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        Constant.DATA.TRANSACTION_ID,
                        transactionId
                    )
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        Constant.DATA.DIRECT,
                        true
                    )
                    navController.navigate(RouteTo.DETAIL.screen)
                }
            }
        }

        composable(route = RouteTo.DETAIL.screen) {
            val transactionData =
                navController.previousBackStackEntry?.savedStateHandle?.get<Int>(Constant.DATA.TRANSACTION_ID)
            val isDirect =
                navController.previousBackStackEntry?.savedStateHandle?.get<Boolean>(Constant.DATA.DIRECT)
            val viewModel: DetailViewModel = koinViewModel()
            BackHandler {
                when (isDirect) {
                    true -> navController.navigate(RouteTo.MENU.screen)
                    else -> navController.navigateUp()
                }
            }

            if (transactionData != null) {
                detailScreen(viewModel = viewModel, transactionData) {
                    when (isDirect) {
                        true -> navController.navigate(RouteTo.MENU.screen)
                        else -> navController.navigateUp()
                    }
                }
            }
        }

        composable(route = RouteTo.HISTORY.screen) {
            BackHandler {
                navController.navigateUp()
            }
            val viewModel: HistoriesViewModel = koinViewModel()
            historyScreen(
                navController = navController,
                viewModel = viewModel
            ) { transactionId: Int ->
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    Constant.DATA.TRANSACTION_ID,
                    transactionId
                )
                navController.navigate(RouteTo.DETAIL.screen)
            }
        }

        composable(route = RouteTo.PROMO.screen) {
            BackHandler {
                navController.navigateUp()
            }
            val viewModel: PromosViewModel = koinViewModel()
            promoScreen(
                navController = navController,
                viewModel = viewModel
            ) { promoItem: PromoItem ->
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    Constant.DATA.PROMO_DATA,
                    promoItem
                )
                navController.navigate(RouteTo.DETAIL.screen)
            }
        }

        composable(route = RouteTo.PROMO_DETAIL.screen) {
            BackHandler {
                navController.navigateUp()
            }
            val promoData =
                navController.previousBackStackEntry?.savedStateHandle?.get<PromoItem>(Constant.DATA.PROMO_DATA)
            if (promoData != null) {
                val viewModel: PromoDetailViewModel = koinViewModel()
                viewModel.setPromoData(promoData)
                viewModel.getPromoDetail()
                promoDetailScreen(
                    promoData,
                    viewModel = viewModel
                ) {
                    navController.navigateUp()
                }
            }
        }

        composable(route = RouteTo.CHART.screen) {
            BackHandler {
                navController.navigateUp()
            }
            val viewModel: ChartsViewModel = koinViewModel()
            chartScreen(viewModel) {
                navController.navigateUp()
            }
        }

        composable(route = RouteTo.ABOUT.screen) {
            BackHandler {
                navController.navigateUp()
            }
            aboutScreen {
                navController.navigateUp()
            }
        }
    }
}

@Composable
fun getAllMenu(): ArrayList<MenuData> {
    val list = ArrayList<MenuData>()
    list.add(
        MenuData(
            list.size,
            stringResource(id = R.string.menu_scan),
            R.drawable.round_qr_code_scanner,
            RouteTo.SCANNER.screen
        )
    )
    list.add(
        MenuData(
            list.size,
            stringResource(id = R.string.menu_history),
            R.drawable.round_history,
            RouteTo.HISTORY.screen
        )
    )
    list.add(
        MenuData(
            list.size,
            stringResource(id = R.string.menu_cashflow),
            R.drawable.round_auto_graph,
            RouteTo.CHART.screen
        )
    )
    list.add(
        MenuData(
            list.size,
            stringResource(id = R.string.promo),
            R.drawable.round_article,
            RouteTo.PROMO.screen
        )
    )
    list.add(
        MenuData(
            list.size,
            stringResource(id = R.string.menu_about),
            R.drawable.round_info,
            RouteTo.ABOUT.screen
        )
    )
    return list
}