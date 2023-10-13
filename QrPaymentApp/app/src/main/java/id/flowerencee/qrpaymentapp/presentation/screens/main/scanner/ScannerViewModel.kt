package id.flowerencee.qrpaymentapp.presentation.screens.main.scanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScannerViewModel : ViewModel() {

    private var _validMetadata = MutableLiveData<String>()
    val validMetaData : LiveData<String> get() = _validMetadata

    fun validateQrCode(qrValue: String) {
        viewModelScope.launch(Dispatchers.IO){
            qrValue.split(".").also {
                withContext(Dispatchers.Main){
                    when(it.size == 4) {
                        true -> _validMetadata.value = qrValue
                        false -> _validMetadata.value = ""
                    }
                }
            }
        }
    }
}