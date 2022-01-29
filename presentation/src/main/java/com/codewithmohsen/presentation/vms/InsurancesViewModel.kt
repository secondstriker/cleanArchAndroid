package com.codewithmohsen.presentation.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithmohsen.domain.use_case.InsurancesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class InsurancesViewModel @Inject constructor(
    private val insurancesUseCase: InsurancesUseCase
) : ViewModel() {

    suspend fun getInsurances() = insurancesUseCase()
    fun fetch() = viewModelScope.launch {
        insurancesUseCase.fetch()
    }
}