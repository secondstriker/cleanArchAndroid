package com.codewithmohsen.domain.logger

interface Logger {

    fun d(tag: String, message: String)
    fun e(tag: String, message: String)
    fun i(tag: String, message: String)
    fun w(tag: String, message: String)
}