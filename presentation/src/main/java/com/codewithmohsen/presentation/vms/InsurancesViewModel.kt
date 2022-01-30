package com.codewithmohsen.presentation.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithmohsen.domain.use_case.InsurancesUseCase
import com.codewithmohsen.presentation.mappers.transform
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsurancesViewModel @Inject constructor(
    private val insurancesUseCase: InsurancesUseCase
) : ViewModel() {

    suspend fun getInsurances() = insurancesUseCase().map { resource ->
        resource.transform()
    }
    fun fetch() {
        viewModelScope.launch {
            insurancesUseCase.fetch()
        }
    }
}