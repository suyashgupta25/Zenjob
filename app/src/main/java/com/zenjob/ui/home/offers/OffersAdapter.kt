package com.zenjob.ui.home.offers

import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zenjob.R
import com.zenjob.data.model.OffersItem
import com.zenjob.data.model.Result
import com.zenjob.data.remote.NetworkState
import com.zenjob.data.remote.Status
import com.zenjob.databinding.ItemNetworkStateBinding
import com.zenjob.databinding.ItemOffersListBinding
import com.zenjob.ui.common.base.BaseHolder
import com.zenjob.ui.common.listeners.ErrorItemClickListener
import com.zenjob.ui.common.viewmodels.ItemNetworkStateViewModel
import com.zenjob.utils.AppConstants.Companion.ONE

class OffersAdapter(val itemClickListener: OfferItemClickListener) : RecyclerView.Adapter<BaseHolder>() {

    private var items = mutableListOf<OffersItem>()
    private var networkState = NetworkState(Status.RUNNING, NetworkState.MSG_RUNNING)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewHolder: BaseHolder

        when (viewType) {
            R.layout.item_offers_list -> {
                val binding = ItemOffersListBinding.inflate(layoutInflater, parent, false)
                viewHolder = OfferItemViewHolder(binding, itemClickListener)
            }
            R.layout.item_network_state -> {
                val binding = ItemNetworkStateBinding.inflate(layoutInflater, parent, false)
                viewHolder = NetworkStateItemViewHolder(binding, itemClickListener)
            }
            else -> throw IllegalArgumentException("unknown view type")
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_offers_list -> (holder as OfferItemViewHolder).onBind(position)
            R.layout.item_network_state -> (holder as NetworkStateItemViewHolder).onBind(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items.isEmpty()) {
            R.layout.item_network_state
        } else {
            R.layout.item_offers_list
        }
    }

    fun setData(result: Result<List<OffersItem>>?, context: Context?) {
        when (result) {
            is Result.Loading -> {
                networkState = NetworkState(Status.RUNNING, NetworkState.MSG_RUNNING)
            }
            is Result.Success -> {
                networkState = NetworkState.LOADED
                val diffCallback = OfferListDiffCallback(items, result.data)
                val diffResult = DiffUtil.calculateDiff(diffCallback)

                items.clear()
                items.addAll(result.data);
                diffResult.dispatchUpdatesTo(this);
            }
            is Result.Failure -> {
                networkState = NetworkState(Status.FAILED, result.message.localizedMessage)
            }
            else -> {
                Log.e("TAG", context?.getString(R.string.err_data_default))
            }
        }
    }

    override fun getItemCount(): Int {
        if (networkState.status == Status.RUNNING) {
            return ONE
        } else if (networkState.status == Status.FAILED) {
            return ONE
        } else {
            return items.size
        }
    }

    inner class OfferItemViewHolder(binding: ItemOffersListBinding, itemClickListener: OfferItemClickListener) : BaseHolder(binding.root) {
        private val binding: ItemOffersListBinding

        init {
            binding.btnOfferDetails.setOnClickListener {
                itemClickListener.onSeeDetailsClick(it, adapterPosition)
            }
            binding.btnOfferDecline.setOnClickListener {
                itemClickListener.onOfferDeclineClick(it, adapterPosition)
            }
            this.binding = binding
        }

        override fun onBind(position: Int) {
            val item = this@OffersAdapter.items[position]
            val itemViewModel = OfferItemViewModel(item)
            binding.viewModel = itemViewModel
        }
    }

    fun getOfferId(position: Int): String? {
        return items[position].id
    }

    inner class NetworkStateItemViewHolder(binding: ItemNetworkStateBinding, clickListener: OfferItemClickListener) : BaseHolder(binding.root), ErrorItemClickListener {

        private val binding: ItemNetworkStateBinding
        private val clickListener: OfferItemClickListener
        private var positionOfItem: Int = 0

        init {
            this.binding = binding
            this.clickListener = clickListener
        }

        override fun onBind(position: Int) {
            positionOfItem = position
            val networkStateObj = this@OffersAdapter.networkState
            val itemNetworkStateViewModel = ItemNetworkStateViewModel(networkStateObj)
            binding.viewModel = itemNetworkStateViewModel
            binding.errorClickListener = this
        }

        override fun onRetryClick(position: Int) {
            clickListener.onRetryClick(positionOfItem)
        }
    }

    internal inner class OfferListDiffCallback(private val oldList: List<OffersItem>, private val newList: List<OffersItem>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            if (oldItem.id.isNullOrBlank() || newItem.id.isNullOrBlank())
                return oldItem.description.equals(newItem.description)
            return oldItem.id.equals(newItem.id)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem.equals(newItem)
        }
    }
}