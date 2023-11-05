package id.flowerencee.qrpaymentapp.presentation.ui.screen.about

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.presentation.shared.`object`.TextLabel
import id.flowerencee.qrpaymentapp.presentation.ui.component.appBarComponent
import id.flowerencee.qrpaymentapp.presentation.ui.component.listTextItem

@Composable
fun aboutScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            appBarComponent(
                title = stringResource(R.string.menu_about),
                leftIcon = R.drawable.round_arrow_back
            ) {
                onBack()
            }
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    shape = MaterialTheme.shapes.large,
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            items(generateLibraryListData()) { data ->
                                listTextItem(data = data)
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun generateLibraryListData(): ArrayList<TextLabel> {
    val list = ArrayList<TextLabel>()
    repeat(12) {
        val libraryName = when (it) {
            0 -> "Firebase Bom"
            1 -> "Firebase Messaging, Analytics, Crash Analytic"
            2 -> "Jetpack Compose"
            3 -> "Room Database"
            4 -> "Kotlin Symbolic Processor KSP"
            5 -> "Koin Dependency Injection"
            6 -> "Camera 2"
            7 -> "Mlkit Barcode Scanning"
            8 -> "Ktor Networking"
            9 -> "Chucker Team in app network inspection"
            10 -> "Coil image loader"
            11 -> "ycharts"
            else -> ""
        }
        if (libraryName.isNotEmpty()) list.add(TextLabel(it, "", libraryName))
    }
    return list
}