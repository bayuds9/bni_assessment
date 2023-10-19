package id.flowerencee.qrpaymentapp.data.repository.source.local.portfolio

import android.content.res.Resources
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.data.model.response.portfolio.DoughnutData
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
        for (element in elements) {
            when (val type = element.get("type").asString) {
                "donutChart" -> {
                    val raw = gson.fromJson(element.getAsJsonArray("data"), JsonArray::class.java)
                    val doughnut = ArrayList<DoughnutData>()
                    raw.forEach {
                        val gsonData = Gson()
                        val result = gsonData.fromJson(it, DoughnutData::class.java)
                        doughnut.add(result)
                    }
                    resultList.add(PortfolioItem(type, doughnut))
                }

                "lineChart" -> {
                    val data = element.getAsJsonObject("data")
                    resultList.add(PortfolioItem(type, lineCart = data))
                }

                else -> {

                }
            }
        }
        DeLog.d(TAG, "result $resultList")
        return flowOf(resultList)
    }
}