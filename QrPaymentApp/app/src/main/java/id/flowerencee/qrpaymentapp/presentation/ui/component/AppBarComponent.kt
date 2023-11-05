package id.flowerencee.qrpaymentapp.presentation.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun appBarComponent(
    title: String,
    leftIcon: Int? = null,
    leftAction: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = title, modifier = Modifier)
        },
        navigationIcon = {
            if (leftIcon != null) {
                IconButton(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = leftAction
                ) {
                    Icon(
                        tint = MaterialTheme.colorScheme.surfaceTint,
                        painter = painterResource(id = leftIcon),
                        contentDescription = "Left Icon"
                    )
                }
            }
        }
    )
}