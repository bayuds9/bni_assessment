package id.flowerencee.qrpaymentapp.data.model

object Constant {
    interface ENDPOINT {
        companion object {
            const val GET_PROMO_LIST = "promos"
        }
    }
    interface HEADER {
        companion object {
            const val AUTHORIZATION = "Au"
        }
    }
    interface PARAM {
        companion object {
            const val READ_TIME_OUT = 60L
            const val CONNECTION_TIME_OUT = 60L
            const val CALL_TIME_OUT = 60L

            const val DOUGHNUT_CHART = "donutChart"
            const val LINE_CHART = "lineChart"
            const val DATA = "data"
            const val TYPE = "type"
        }
    }

    interface DATA {
        companion object {
            const val QR_DATA = "qr_data"
            const val TRANSACTION_ID = "transaction_id"
            const val DIRECT = "direct"
            const val PROMO_DATA = "promo_data"
        }
    }
}