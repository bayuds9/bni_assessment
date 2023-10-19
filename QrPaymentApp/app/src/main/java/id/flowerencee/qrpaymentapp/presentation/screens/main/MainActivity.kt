package id.flowerencee.qrpaymentapp.presentation.screens.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.databinding.ActivityMainBinding
import id.flowerencee.qrpaymentapp.presentation.screens.main.account.AccountFragment
import id.flowerencee.qrpaymentapp.presentation.screens.main.cart.CartFragment
import id.flowerencee.qrpaymentapp.presentation.screens.main.dashboard.DashboardFragment
import id.flowerencee.qrpaymentapp.presentation.screens.main.scanner.ScannerFragment
import id.flowerencee.qrpaymentapp.presentation.shared.custom.PopUpInterface
import id.flowerencee.qrpaymentapp.presentation.shared.custom.showPopup
import id.flowerencee.qrpaymentapp.presentation.shared.extension.animatedTransaction
import id.flowerencee.qrpaymentapp.presentation.shared.`object`.DialogData
import id.flowerencee.qrpaymentapp.presentation.shared.support.BaseActivity

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        private val REQUIRED_PERMISSIONS =
            arrayOf(Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    private var dashboardFragment: DashboardFragment? = null
    private var accountFragment: AccountFragment? = null
    private var scannerFragment: ScannerFragment? = null
    private var cartFragment: CartFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        validatePermission()
        initUi()
    }

    override fun onBackPressed() {
        val dialogData = DialogData(
            getString(R.string.exit_title),
            getString(R.string.exit_desc),
            getString(R.string.cancel),
            getString(R.string.yes),
            icon = R.drawable.round_exit_to_app
        )
        val listener = object : PopUpInterface {
            override fun onNegative() {
                super.onNegative()
                finishAndRemoveTask()
            }
        }
        showPopup(dialogData, listener)
    }

    private fun initUi() {
        binding.tbToolbar.apply {
            initToolbar(this.toolbar(), getString(R.string.app_name))
        }
        setCurrentFragment(openDashboard())
        binding.navBottom.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_dashboard -> setCurrentFragment(openDashboard())
                R.id.menu_account -> setCurrentFragment(openAccount())
                R.id.menu_scann -> setCurrentFragment(openScanner())
                R.id.menu_cashflow -> setCurrentFragment(openCart())
                R.id.menu_about -> {
                    throw RuntimeException("Test Crash")
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun openDashboard(): DashboardFragment {
        if (dashboardFragment == null) dashboardFragment = DashboardFragment.newInstance()
        return dashboardFragment as DashboardFragment
    }

    private fun openAccount(): AccountFragment {
        if (accountFragment == null) accountFragment = AccountFragment.newInstance()
        return accountFragment as AccountFragment
    }

    private fun openScanner(): ScannerFragment {
        if (scannerFragment == null) scannerFragment = ScannerFragment.newInstance()
        return scannerFragment as ScannerFragment
    }

    private fun openCart(): CartFragment {
        if (cartFragment == null) cartFragment = CartFragment.newInstance()
        return cartFragment as CartFragment
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager
            .animatedTransaction
            .setReorderingAllowed(true)
            .replace(R.id.fragmentContainer, fragment, fragment.tag)
            .commitNowAllowingStateLoss()
    }

    fun backToHome() {
        setCurrentFragment(openDashboard())
    }

    private fun validatePermission() {
        if (!allPermissionsGranted()) showPermissionDialog()
    }

    private fun showPermissionDialog() {
        ActivityCompat.requestPermissions(
            this,
            REQUIRED_PERMISSIONS,
            REQUEST_CODE_PERMISSIONS
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.isEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                val permissionData = DialogData(
                    getString(R.string.permission_required),
                    getString(R.string.grant_permission),
                    getString(R.string.okay),
                    getString(R.string.cancel)
                )
                val permissionCallback = object : PopUpInterface {
                    override fun onPositive() {
                        showPermissionDialog()
                    }
                }
                showPopup(permissionData, permissionCallback)
            }
        }

    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            this, it
        ) == PackageManager.PERMISSION_GRANTED
    }
}