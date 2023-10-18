package id.flowerencee.qrpaymentapp.presentation.screens.promo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import id.flowerencee.qrpaymentapp.data.model.response.promo.PromoItem
import id.flowerencee.qrpaymentapp.presentation.shared.extension.formatFirstLetter
import id.flowerencee.qrpaymentapp.presentation.shared.extension.isJsonObject
import id.flowerencee.qrpaymentapp.presentation.shared.extension.toMap
import id.flowerencee.qrpaymentapp.presentation.shared.extension.toNumberFormat
import id.flowerencee.qrpaymentapp.presentation.shared.`object`.TextLabel
import id.flowerencee.qrpaymentapp.presentation.shared.support.DeLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PromoViewModel : ViewModel() {

    private var promo: Flow<PromoItem> = flowOf()
    private var _promoDetail: MutableLiveData<ArrayList<TextLabel>> = MutableLiveData()
    val promoDetail: LiveData<ArrayList<TextLabel>> get() = _promoDetail

    fun setPromoData(data: PromoItem) {
        promo = flowOf(data)
        viewModelScope.launch(Dispatchers.IO) {
            promo.collect() {
                DeLog.d("haha", "promo ${it}")
            }
        }
    }

    fun getTitle() = liveData {
        promo.collect() {
            val title = it.title ?: it.namePromo ?: it.nama
            emit(title)
        }
    }

    fun getImage() = liveData {
        promo.collect() {
            val imgUrl = it.img?.url ?: it.img?.formats?.large?.url ?: it.img?.formats?.medium?.url
            ?: it.img?.formats?.small?.url
            emit(imgUrl)
        }
    }

    fun getPromoDetail() {
        viewModelScope.launch(Dispatchers.IO) {
            promo.collect() {
                val detailData = generateDetailData(it)
                withContext(Dispatchers.Main) {
                    _promoDetail.value = detailData
                }
            }
        }
    }

    private fun generateDetailData(source: PromoItem): ArrayList<TextLabel> {
        val list = ArrayList<TextLabel>()
        toMap(source).forEach {
            DeLog.d("haha", "key ${it.key}")
            when {
                it.key != "img" && it.key != "title" && it.key != "name_promo" && it.key != "nama" && it.key != "Title" && it.key != "id" -> {
                    if (it.value != null || it.value is String && it.value != "null") {
                        val label = if (it.key == "desc") "" else it.key
                        val value = when {
                            !isJsonObject(it.value.toString()) -> it.value.toString()
                                .replace(Regex("\\n"), "")

                            it.value is Number -> it.value.toString().toNumberFormat()
                            it.value.toString().matches(Regex("[A-Za-z0-9]")) -> it.value.toString()
                                .formatFirstLetter()

                            else -> it.value.toString().replace(Regex("\\n"), "")
                        }

                        if (value.isNotEmpty()) list.add(TextLabel(list.size + 1, label, value))
                    }
                }

                else -> {}
            }
        }
        return list
    }
}