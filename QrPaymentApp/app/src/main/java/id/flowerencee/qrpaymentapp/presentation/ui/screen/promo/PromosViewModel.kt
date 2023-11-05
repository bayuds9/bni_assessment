package id.flowerencee.qrpaymentapp.presentation.ui.screen.promo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.flowerencee.qrpaymentapp.data.model.response.failed.StatusResponses
import id.flowerencee.qrpaymentapp.data.model.response.promo.PromoItem
import id.flowerencee.qrpaymentapp.domain.usecase.promo.GetAllPromoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PromosViewModel(
    private val getAllPromoUseCase: GetAllPromoUseCase
) : ViewModel() {
    private val _listPromo = MutableStateFlow<ArrayList<PromoItem>>(arrayListOf())
    val listPromo: StateFlow<ArrayList<PromoItem>> get() = _listPromo
    private val _statusResponse = MutableStateFlow<StatusResponses?>(null)
    val statusResponse: StateFlow<StatusResponses?> get() = _statusResponse

    init {
        getAllPromo()
    }

    fun getAllPromo() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllPromoUseCase.execute().collectLatest {
                withContext(Dispatchers.Main) {
                    _listPromo.value = ArrayList(it)
                }
            }
            getAllPromoUseCase.getStatus().collectLatest {
                withContext(Dispatchers.Main) {
                    _statusResponse.value = it
                }
            }
        }
    }
}