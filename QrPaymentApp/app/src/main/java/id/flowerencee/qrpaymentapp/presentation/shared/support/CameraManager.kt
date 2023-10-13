package id.flowerencee.qrpaymentapp.presentation.shared.support

import android.content.Context
import android.util.Log
import android.view.Surface
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.core.UseCaseGroup
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.mlkit.vision.barcode.common.Barcode
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraManager(
    private val context: Context,
    private val finderView: PreviewView,
    private val lifecycleOwner: LifecycleOwner,
    private val qrListener: (Barcode) -> Unit
) {

    private var preview: Preview? = null

    private var camera: Camera? = null
    private lateinit var cameraExecutor: ExecutorService
    private var cameraSelectorOption = CameraSelector.LENS_FACING_BACK
    private var cameraProvider: ProcessCameraProvider? = null

    private var isFlashAvailable: Boolean = false
    private var flashOn = false

    private var imageAnalyzer: ImageAnalysis? = null
    private var imageCapture: ImageCapture? = null

    private var file = 1

    init {
        cameraSelectorOption = CameraSelector.LENS_FACING_BACK
        createNewExecutor()
    }

    private fun createNewExecutor() {
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener(
            {
                cameraProvider = cameraProviderFuture.get()
                preview = Preview.Builder()
                    .build()

                imageCapture = ImageCapture.Builder()
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                    .setTargetRotation(Surface.ROTATION_0)
                    .build()

                imageAnalyzer = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        it.setAnalyzer(cameraExecutor, QRDetectionProcessor(qrListener))
                    }

                val cameraSelector = CameraSelector.Builder()
                    .requireLensFacing(cameraSelectorOption)
                    .build()

                setCameraConfig(cameraProvider, cameraSelector)

            }, ContextCompat.getMainExecutor(context)
        )
    }

    private fun setCameraConfig(
        cameraProvider: ProcessCameraProvider?,
        cameraSelector: CameraSelector
    ) {
        val group = UseCaseGroup.Builder()
            .addUseCase(preview!!)
            .addUseCase(imageAnalyzer!!)
            .addUseCase(imageCapture!!)
            .build()

        try {
            cameraProvider?.unbindAll()

            camera = cameraProvider?.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                group
            )
            preview?.setSurfaceProvider(
                finderView.surfaceProvider
            )
            isFlashAvailable = camera?.cameraInfo?.hasFlashUnit() ?: false
        } catch (e: Exception) {
            Log.e(TAG, "Use case binding failed", e)
        }
    }

    fun freezeCamera() {
        cameraProvider?.unbind(preview)
    }

    fun flashControll(states: Boolean) {
        if (isFlashAvailable) {
            camera?.cameraControl?.enableTorch(states)
            flashOn = states
        }
    }

    fun isFlashOn(): Boolean = flashOn

    fun isFlashAvailable(): Boolean = isFlashAvailable

    private fun createFile(baseFolder: File, format: String, extension: String) =
        File(
            baseFolder, "$format-${file++}" + extension
        )

    companion object {
        private val TAG = CameraManager::class.java.simpleName
    }

}