package com.codewithmohsen.commonandroid

import com.codewithmohsen.common.logger.Logger
import timber.log.Timber
import javax.inject.Inject

class LoggerImpl @Inject constructor(): Logger {

    override fun d(tag: String, message: String) {
        Timber.d("$tag: $message")
    }

    override fun e(tag: String, message: String) {
        Timber.e("$tag: $message")
    }

    override fun i(tag: String, message: String) {
        Timber.i("$tag: $message")
    }

    override fun w(tag: String, message: String) {
        Timber.w("$tag: $message")
    }
}