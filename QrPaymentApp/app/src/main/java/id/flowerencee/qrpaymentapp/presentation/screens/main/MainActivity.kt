package id.flowerencee.qrpaymentapp.presentation.screens.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.databinding.ActivityMainBinding
import id.flowerencee.qrpaymentapp.presentation.screens.main.dashboard.DashboardFragment
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
        /*binding.tbToolbar.apply {
            initToolbar(this.toolbar(), "")
        }*/

        setCurrentFragment(openDashboard())
    }

    private fun openDashboard() : DashboardFragment {
        return DashboardFragment.newInstance()
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager
            .animatedEnterTransaction
            .setReorderingAllowed(true)
            .replace(R.id.fragmentContainer, fragment, fragment.tag)
            .commitNowAllowingStateLoss()
    }
}