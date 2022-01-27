package com.codewithmohsen.domain.use_case

import com.codewithmohsen.domain.Resource
import com.codewithmohsen.domain.entities.domain_entities.InsuranceResponseItem
import com.codewithmohsen.domain.repository.InsurancesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class InsurancesUseCase @Inject constructor(
    private val insurancesRepository: InsurancesRepository
) {

    suspend operator fun invoke(): Flow<Resource<List<InsuranceResponseItem>>> {
        return insurancesRepository.getAllInsurances()
    }

}