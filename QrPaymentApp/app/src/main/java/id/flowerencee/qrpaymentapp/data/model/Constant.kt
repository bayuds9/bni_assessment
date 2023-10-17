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
            const val BEARER_TOKEN = "Bearer "
        }
    }
}