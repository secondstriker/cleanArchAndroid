package com.codewithmohsen.domain.use_case


import com.codewithmohsen.domain.repository.InsurancesRepository
import javax.inject.Inject


class InsurancesUseCase @Inject constructor(
    private val insurancesRepository: InsurancesRepository
) {

    suspend operator fun invoke() =
        insurancesRepository.getAllInsurances()

    suspend fun fetch() = insurancesRepository.fetchAllInsurances()


}