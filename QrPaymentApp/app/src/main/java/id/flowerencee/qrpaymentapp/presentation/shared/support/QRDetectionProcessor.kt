package id.flowerencee.qrpaymentapp.presentation.shared.support

import android.graphics.Rect
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.io.IOException

class QRDetectionProcessor(
    private val listener: (Barcode) -> Unit
) : BaseImageAnalyzer<List<Barcode>>() {
    private val realTimeOpts = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
        .build()

    private val detector = BarcodeScanning.getClient(realTimeOpts)

    companion object {
        private const val TAG = "QRDetectorProcessor"
    }

    override fun detectInImage(image: InputImage): Task<List<Barcode>> {
        return detector.process(image)
    }

    override fun stop() {
        try {
            detector.close()
        } catch (e: IOException) {
            Log.e(TAG, "Exception thrown while trying to close Face Detector: $e")
        }
    }

    override fun onSuccess(results: List<Barcode>, rect: Rect) {
        results.forEach {
            listener(it)
        }
    }

    override fun onFailure(e: Exception) {
        Log.w(TAG, "QR Detector Failed.$e")
    }
}