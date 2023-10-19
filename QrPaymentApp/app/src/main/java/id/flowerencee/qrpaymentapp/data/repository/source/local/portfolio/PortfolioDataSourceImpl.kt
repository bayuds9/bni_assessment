package id.flowerencee.qrpaymentapp.data.repository.source.local.portfolio

import android.content.res.Resources
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.data.model.Constant
import id.flowerencee.qrpaymentapp.data.model.response.portfolio.DoughnutData
import id.flowerencee.qrpaymentapp.data.model.response.portfolio.LineCart
import id.flowerencee.qrpaymentapp.data.model.response.portfolio.PortfolioItem
import id.flowerencee.qrpaymentapp.presentation.shared.support.DeLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class PortfolioDataSourceImpl(
    private val resources: Resources
) : PortfolioDataSource {
    companion object {
        private val TAG = PortfolioDataSourceImpl::class.java.simpleName
    }

    override suspend fun getCartData(): Flow<ArrayList<PortfolioItem>> {
        val inputStream = resources.openRawResource(R.raw.cart_data)
        val jsonContent = inputStream.bufferedReader().use { it.readText() }
        val resultList = ArrayList<PortfolioItem>()
        val gson = Gson()
        val listType = object : TypeToken<List<JsonObject>>() {}.type
        val elements: List<JsonObject> = gson.fromJson(jsonContent, listType)
        elements.forEach { element ->
            when (val type = element.get(Constant.PARAM.TYPE).asString) {
                Constant.PARAM.DOUGHNUT_CHART -> {
                    val result : PortfolioItem = processDoughnutCart(gson, type, element)
                    resultList.add(result)
                }

                Constant.PARAM.LINE_CHART -> {
                    val result : PortfolioItem = processLineCart(gson, type, element)
                    resultList.add(result)
                }
                else -> {}
            }
        }
        DeLog.d(TAG, "result $resultList")
        return flowOf(resultList)
    }

    private fun processLineCart(gson: Gson, type: String, element: JsonObject): PortfolioItem {
        val raw = gson.fromJson(element.getAsJsonObject(Constant.PARAM.DATA), JsonObject::class.java)
        val result = gson.fromJson(raw, LineCart::class.java)
        return PortfolioItem(type, lineCart = result)
    }

    private fun processDoughnutCart(gson: Gson, type: String, element: JsonObject): PortfolioItem {
        val raw = gson.fromJson(element.getAsJsonArray(Constant.PARAM.DATA), JsonArray::class.java)
        val doughnut = ArrayList<DoughnutData>()
        raw.forEach {
            val result = gson.fromJson(it, DoughnutData::class.java)
            doughnut.add(result)
        }
        return PortfolioItem(type, doughnut)
    }
}