package com.codewithmohsen.features.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.codewithmohsen.domain.entities.resource_entities.ResourceEntity
import com.codewithmohsen.features.R
import com.codewithmohsen.features.adapter.ItemListAdapter
import com.codewithmohsen.features.databinding.FragmentInsuranceListBinding
import com.codewithmohsen.presentation.vms.InsurancesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class InsurancesFragment: Fragment() {

    private val job: Job = Job()
    private val viewModel: InsurancesViewModel by viewModels()
    private lateinit var adapter: ItemListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentInsuranceListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_insurance_list, container, false
        )

        adapter = ItemListAdapter {
            //onClick callback
        }
        binding.itemList.adapter = adapter

        lifecycleScope.launch(job) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getInsurances().collect { resource ->
                    Timber.d("resource -> $resource")

                    adapter.submitList(resource.data)

                    binding.swipeRefresh.isRefreshing =
                        (resource is ResourceEntity.Loading<*>) ||
                        (resource is ResourceEntity.LongLoading<*>)

                    binding.cancelContainer.visibility =
                        if(resource is ResourceEntity.LongLoading<*> && resource.data == null)
                            View.VISIBLE
                        else
                            View.GONE
                }
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.fetch()
        }

        viewModel.fetch()

        return binding.root
    }
}