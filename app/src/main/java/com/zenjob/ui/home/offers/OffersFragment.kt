package com.zenjob.ui.home.offers

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zenjob.R
import com.zenjob.databinding.FragmentOffersBinding
import com.zenjob.ui.common.base.BaseActivity
import com.zenjob.ui.home.offerdetails.OfferDetailsFragment
import com.zenjob.utils.ext.addFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class OffersFragment : Fragment(), OfferItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var mAdapter = OffersAdapter(this)

    val viewModel: OffersViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(OffersViewModel::class.java)
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_offers, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding(view)
    }

    private fun initBinding(view: View) {
        val binding = DataBindingUtil.bind<FragmentOffersBinding>(view)
        binding.let {
            it!!.viewModel = viewModel
            it.setLifecycleOwner(this)
            binding?.rvOffers?.adapter = mAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addObservers()
    }

    private fun addObservers() {
        viewModel.offerList.observe(this, Observer {
            mAdapter?.setData(it, activity?.applicationContext)
            mAdapter?.notifyDataSetChanged()
        })
        viewModel.getOffers()
    }

    override fun onSeeDetailsClick(view: View, position: Int) {
        val bundle = Bundle()
        bundle.putString(getString(R.string.param_offer_details_id),
                mAdapter.getOfferId(position))
        activity?.addFragment((activity as BaseActivity).fragmentContainerId, ::OfferDetailsFragment, bundle)
    }

    override fun onOfferDeclineClick(view: View, position: Int) {

    }

    override fun onRetryClick(position: Int) {
        viewModel.getOffers()
    }

}