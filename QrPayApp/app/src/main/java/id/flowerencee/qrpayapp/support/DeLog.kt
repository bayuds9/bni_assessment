package id.flowerencee.qrpayapp.support

import android.util.Log
import id.flowerencee.qrpayapp.BuildConfig

object DeLog {
    fun d(tag: String, message: String) {
        if (BuildConfig.DEBUG) Log.d(tag, message)
    }
    fun i(tag: String, message: String) {
        if (BuildConfig.DEBUG) Log.i(tag, message)
    }
    fun e(tag: String, message: String) {
        if (BuildConfig.DEBUG) Log.e(tag, message)
    }
    fun v(tag: String, message: String) {
        if (BuildConfig.DEBUG) Log.v(tag, message)
    }
    fun w(tag: String, message: String) {
        if (BuildConfig.DEBUG) Log.w(tag, message)
    }
}