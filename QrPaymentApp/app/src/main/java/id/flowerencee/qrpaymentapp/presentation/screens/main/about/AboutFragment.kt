package id.flowerencee.qrpaymentapp.presentation.screens.main.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.databinding.FragmentAboutBinding
import id.flowerencee.qrpaymentapp.presentation.shared.`object`.TextLabel
import java.util.ArrayList

class AboutFragment : Fragment() {

    companion object {
        fun newInstance(): AboutFragment {
            val fragment = AboutFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAboutBinding.bind(
            inflater.inflate(R.layout.fragment_about, container, false)
        )
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        binding.viewLibraries.apply {
            setHeader(getString(R.string.used_library))
            setData(generateLibraryListData())
        }
    }

    private fun generateLibraryListData(): ArrayList<TextLabel> {
        val list = ArrayList<TextLabel>()
        repeat(12){
            val libraryName = when(it){
                0 -> "Firebase Bom"
                1 -> "Firebase Messaging, Analytics, Crash Analytic"
                2 -> "Firebase Analytics"
                3 -> "Room Database"
                4 -> "Kotlin Symbolic Processor KSP"
                5 -> "Koin Dependency Injection"
                6 -> "Camera 2"
                7 -> "Mlkit Barcode Scanning"
                8 -> "Ktor Networking"
                9 -> "Chucker Team in app network inspection"
                10 -> "Glide image loader"
                11 -> "MPAndroidChart"
                else -> ""
            }
            if (libraryName.isNotEmpty()) list.add(TextLabel(it, "", libraryName))
        }
        return list
    }
}