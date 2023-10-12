package id.flowerencee.qrpayapp.presentation.ui.screens.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TransactionViewModel : ViewModel() {
    private var _title = MutableLiveData<String>()
    val title: LiveData<String> get() = _title

    fun setTitle(text: String) {
        _title.value = text
    }
}