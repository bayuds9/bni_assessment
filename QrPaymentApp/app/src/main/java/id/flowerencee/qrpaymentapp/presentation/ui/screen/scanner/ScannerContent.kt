package id.flowerencee.qrpaymentapp.presentation.ui.screen.scanner

import android.Manifest
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.mlkit.vision.barcode.common.Barcode
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.presentation.shared.extension.isQrValid
import id.flowerencee.qrpaymentapp.presentation.shared.support.DeLog
import id.flowerencee.qrpaymentapp.presentation.shared.support.QRDetectionProcessor
import id.flowerencee.qrpaymentapp.presentation.ui.component.appBarComponent

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun scannerScreen(navController: NavController, qrResult: (String) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val qrListener: (Barcode) -> Unit = { barcode: Barcode ->
        barcode.rawValue?.let { raw ->
            if (raw.isQrValid()) qrResult(raw)
        }
    }

    val cameraPermissionsState = rememberPermissionState(Manifest.permission.CAMERA)
    if (cameraPermissionsState.status.isGranted) {
        Scaffold(
            topBar = {
                appBarComponent(
                    title = stringResource(id = R.string.menu_scan),
                    R.drawable.round_arrow_back
                ) {
                    navController.navigateUp()
                }
            },
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

                AndroidView(
                    factory = { ctx ->
                        val previewView = PreviewView(ctx)
                        val cameraExecutor = ContextCompat.getMainExecutor(ctx)

                        val imageAnalyzer = ImageAnalysis.Builder()
                            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                            .build()
                            .also {
                                it.setAnalyzer(cameraExecutor, QRDetectionProcessor(qrListener))
                            }

                        cameraProviderFuture.addListener({
                            val cameraProvider = cameraProviderFuture.get()
                            val preview = androidx.camera.core.Preview.Builder().build().also {
                                it.setSurfaceProvider(previewView.surfaceProvider)
                            }

                            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                            cameraProvider.unbindAll()  // Optional but recommended before binding new use cases
                            cameraProvider.bindToLifecycle(
                                lifecycleOwner, cameraSelector, imageAnalyzer, preview
                            )
                        }, cameraExecutor)

                        previewView
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        DeLog.d("camera", "started")
    } else {
        Column {
            val remarkText = if (cameraPermissionsState.status.shouldShowRationale) {
                stringResource(id = R.string.camera_require)
            } else {
                "Camera unavailable"
            }
            Text(remarkText)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { cameraPermissionsState.launchPermissionRequest() }) {
                Text(stringResource(id = R.string.grant_permission))
            }
        }
    }

    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_START) {
                    cameraPermissionsState.launchPermissionRequest()
                }
            }

            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )
}