package com.codewithmohsen.features.adapter

import android.text.Html
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.codewithmohsen.domain.di.DefaultDispatcher
import com.codewithmohsen.features.R
import com.codewithmohsen.features.databinding.InsuranceItemBinding
import com.codewithmohsen.presentation.models.InsuranceModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * A RecyclerView adapter for [Item List] class.
 */
class ItemListAdapter(
    @DefaultDispatcher defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val itemClickCallback: ((InsuranceModel) -> Unit)?
) : DataBoundListAdapter<InsuranceModel, InsuranceItemBinding>(
    defaultDispatcher,
    diffCallback = object : DiffUtil.ItemCallback<InsuranceModel>() {
        override fun areItemsTheSame(oldItem: InsuranceModel, newItem: InsuranceModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: InsuranceModel, newItem: InsuranceModel): Boolean {
            return oldItem.id == newItem.id &&
            oldItem.title == newItem.title &&
            oldItem.price == newItem.price &&
            oldItem.commentsCount == newItem.commentsCount &&
            oldItem.discountedPrice == newItem.discountedPrice &&
            oldItem.rate == newItem.rate &&
            oldItem.discount== newItem.discount &&
            oldItem.logoUrl == newItem.logoUrl
        }
    }
) {

    override fun createBinding(parent: ViewGroup): InsuranceItemBinding {

        val binding = DataBindingUtil.inflate<InsuranceItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.insurance_item,
            parent,
            false)
        binding.root.setOnClickListener { _ ->
            binding.item.let {
                if(it != null) itemClickCallback?.invoke(it)
            }
        }

        return binding
    }

    override fun bind(binding: InsuranceItemBinding, item: InsuranceModel) {
        binding.item = item
        binding.price.text = Html.fromHtml("<s> ${item.price} </s>")
    }
}
