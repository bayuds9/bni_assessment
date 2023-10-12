package id.flowerencee.qrpayapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import id.flowerencee.qrpayapp.presentation.ui.screens.main.MainScreen
import id.flowerencee.qrpayapp.presentation.ui.theme.QrPayAppTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QrPayAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    viewModel: PaymentViewModel = koinViewModel()
) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
    viewModel.getAccount().observeAsState(listOf()).value.forEach {
        Log.d("haha", "user account $it")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    QrPayAppTheme {
        Greeting("Android")
    }
}