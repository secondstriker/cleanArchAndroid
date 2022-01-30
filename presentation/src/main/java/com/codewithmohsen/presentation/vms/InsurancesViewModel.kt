package com.codewithmohsen.presentation.vms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithmohsen.domain.use_case.InsurancesUseCase
import com.codewithmohsen.presentation.mappers.transform
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class InsurancesViewModel @Inject constructor(
    private val insurancesUseCase: InsurancesUseCase
) : ViewModel() {

    private lateinit var job: Job

    suspend fun getInsurances() = insurancesUseCase().map { resource ->
        resource.transform()
    }
    fun fetch() {
        newJob()
        viewModelScope.launch(job) {
            insurancesUseCase.fetch()
        }
    }

    fun cancel() {
        Timber.d("viewModel cancel")
        job.cancel()
    }

    private fun newJob() {
        if(!this::job.isInitialized)
            job = Job()
        if (job.isActive || job.isCancelled) {
            job.cancel()
            job = Job()
            job.invokeOnCompletion {
                Timber.d("viewModel job completed. is cancelled? ${job.isCancelled}")
            }
        }
    }
}