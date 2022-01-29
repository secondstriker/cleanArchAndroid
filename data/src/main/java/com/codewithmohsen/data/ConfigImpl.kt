package com.codewithmohsen.data

import com.codewithmohsen.common.Config
import javax.inject.Inject

class ConfigImpl @Inject constructor(): Config {

    override fun getLongRunningThreshold(): Long = 3000L

}