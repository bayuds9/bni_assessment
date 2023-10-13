package id.flowerencee.qrpaymentapp.presentation.screens.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.databinding.ActivityMainBinding
import id.flowerencee.qrpaymentapp.presentation.screens.main.dashboard.DashboardFragment
import id.flowerencee.qrpaymentapp.presentation.screens.main.scanner.ScannerFragment
import id.flowerencee.qrpaymentapp.presentation.shared.extension.animatedEnterTransaction
import id.flowerencee.qrpaymentapp.presentation.shared.support.BaseActivity

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
    }

    private fun initUi() {
        binding.tbToolbar.apply {
            initToolbar(this.toolbar(), getString(R.string.app_name))
        }
        setCurrentFragment(openDashboard())
        binding.navBottom.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_dashboard -> setCurrentFragment(openDashboard())
                R.id.menu_scann -> setCurrentFragment(openScanner())
                /*R.id.menu_setting -> setCurrentFragment(setting)*/
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun openDashboard() : DashboardFragment {
        return DashboardFragment.newInstance()
    }

    private fun openScanner() : ScannerFragment {
        return ScannerFragment.newInstance()
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager
            .animatedEnterTransaction
            .setReorderingAllowed(true)
            .replace(R.id.fragmentContainer, fragment, fragment.tag)
            .commitNowAllowingStateLoss()
    }

    fun backToHome() {
        setCurrentFragment(openDashboard())
    }
}