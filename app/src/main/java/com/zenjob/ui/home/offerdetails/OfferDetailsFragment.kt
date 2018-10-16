package com.zenjob.ui.home.offerdetails

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
import com.zenjob.databinding.FragmentOfferDetailsBinding
import com.zenjob.ui.common.listeners.ErrorItemClickListener
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class OfferDetailsFragment : Fragment(), ErrorItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: OfferDetailsViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(OfferDetailsViewModel::class.java)
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_offer_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding(view)
    }

    private fun initBinding(view: View) {
        val binding = DataBindingUtil.bind<FragmentOfferDetailsBinding>(view)
        binding.let {
            it!!.viewModel = viewModel
            it!!.errorClickListener = this
            it.setLifecycleOwner(this)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getOfferDetails()
    }

    private fun getOfferDetails() {
        viewModel.getOfferDetails(arguments?.getString(getString(R.string.param_offer_details_id)))
    }

    override fun onRetryClick(position: Int) {
        getOfferDetails()
    }
}