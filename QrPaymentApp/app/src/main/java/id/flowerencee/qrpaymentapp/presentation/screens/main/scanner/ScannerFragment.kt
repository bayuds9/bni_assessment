package id.flowerencee.qrpaymentapp.presentation.screens.main.scanner

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.databinding.FragmentScannerBinding
import id.flowerencee.qrpaymentapp.presentation.screens.main.MainActivity
import id.flowerencee.qrpaymentapp.presentation.screens.transaction.inquiry.InquiryActivity
import id.flowerencee.qrpaymentapp.presentation.shared.custom.PopUpInterface
import id.flowerencee.qrpaymentapp.presentation.shared.custom.showPopup
import id.flowerencee.qrpaymentapp.presentation.shared.`object`.DialogData
import id.flowerencee.qrpaymentapp.presentation.shared.support.CameraManager
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScannerFragment : Fragment() {
    companion object {
        private val TAG = ScannerFragment::class.java.simpleName
        fun newInstance(): ScannerFragment {
            val fragment = ScannerFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    private var _binding: FragmentScannerBinding? = null
    private val binding get() = _binding!!
    private var cameraManager: CameraManager? = null
    private val viewModel: ScannerViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScannerBinding.bind(
            inflater.inflate(R.layout.fragment_scanner, container, false)
        )
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validatePermission()
    }

    private fun validatePermission() {
        if (!allPermissionsGranted()) {
            showPermissionDialog()
            initDefault()
        } else {
            initScreen()
        }
    }

    private fun initDefault() {
        binding.btnOpenSetting.apply {
            if (visibility != View.VISIBLE) visibility = View.VISIBLE
            setOnClickListener {
                val popupListener = object : PopUpInterface {
                    override fun onPositive() {
                        super.onPositive()
                        showPermissionDialog()
                        postDelayed({
                            validatePermission()
                        }, 3000)
                    }

                    override fun onNegative() {
                        super.onNegative()
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri =
                            Uri.fromParts(
                                "package",
                                (activity as MainActivity).packageName,
                                null
                            )
                        intent.data = uri
                        (activity as MainActivity).activityLauncher.launch(intent) {
                            validatePermission()
                        }
                    }

                    override fun onNeutral() {
                        super.onNeutral()
                        (activity as MainActivity).backToHome()
                    }
                }
                val data = DialogData(
                    getString(R.string.attention),
                    getString(R.string.camera_require),
                    getString(R.string.sure),
                    getString(R.string.open_setting),
                    getString(R.string.cancel),
                    R.drawable.round_warning_amber
                )
                requireActivity().showPopup(data, popupListener)
            }
        }
    }

    private fun initScreen() {
        initUi()
        initData()
    }

    private fun showPermissionDialog() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            REQUIRED_PERMISSIONS,
            REQUEST_CODE_PERMISSIONS
        )
    }

    private fun initData() {
        viewModel.validMetaData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                cameraManager?.freezeCamera()
                routeToInquiry(it)
            }
        }
    }

    private fun initUi() {
        binding.btnOpenSetting.visibility = View.GONE
        setupScanner()
        resetStates()
        binding.btnFlash.setOnClickListener {
            cameraManager?.let {
                when (it.isFlashOn()) {
                    true -> it.flashControll(false)
                    else -> it.flashControll(true)
                }
                binding.btnFlash.isSelected = it.isFlashOn()
            }
        }
    }

    private fun resetStates() {
        binding.btnFlash.isSelected = false
        cameraManager?.startCamera()
    }

    private fun setupScanner() {
        cameraManager = CameraManager(
            requireContext(),
            binding.cameraPreview,
            this.viewLifecycleOwner
        ) { barcode ->
            barcode.rawValue?.let {
                viewModel.validateQrCode(it)
            }
        }
    }

    private fun routeToInquiry(qrCode: String) {
        (activity as MainActivity).let {
            it.activityLauncher.launch(InquiryActivity.myIntent(it, qrCode)){ result ->
                when(result.resultCode){
                    Activity.RESULT_OK -> it.backToHome()
                }
            }
        }
    }
}