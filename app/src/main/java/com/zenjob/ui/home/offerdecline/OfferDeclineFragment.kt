package com.zenjob.ui.home.offerdecline

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.zenjob.R
import com.zenjob.data.model.ReasonsItem
import com.zenjob.data.model.Result
import com.zenjob.databinding.FragmentOfferDeclineBinding
import com.zenjob.ui.common.listeners.ErrorItemClickListener
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class OfferDeclineFragment : DialogFragment(), View.OnClickListener, ErrorItemClickListener, RadioGroup.OnCheckedChangeListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: OfferDeclineViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(OfferDeclineViewModel::class.java)
    }
    val params = LinearLayout.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT)

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Theme_Dialog);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_offer_decline, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding(view)
    }

    private fun initBinding(view: View) {
        val binding = DataBindingUtil.bind<FragmentOfferDeclineBinding>(view)
        binding.let {
            it!!.viewModel = viewModel
            it!!.errorClickListener = this
            it!!.checkChangeListener = this
            it!!.clickListener = this
            it.setLifecycleOwner(this)
        }
        binding?.watcher = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        }
        addObservers(binding)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getOfferDeclineReasons()
    }

    fun addObservers(binding: FragmentOfferDeclineBinding?) {
        viewModel.reasons.observe(this, Observer {
            it?.listIterator()?.forEach { t: ReasonsItem ->
                val rbn = RadioButton(activity)
                rbn.id = it?.indexOf(t)
                rbn.text = t.label
                rbn.layoutParams = params
                binding?.rgOfferDeclineReasons?.addView(rbn)
            }
        })
        viewModel.offerDeclineResponse.observe(this, Observer {
            when (it) {
                is Result.Loading -> {
                }
                is Result.Success -> {
                    val intent = getActivity()?.getIntent()
                    intent?.putExtra(getString(R.string.param_offer_decline_response), it.data)
                    getTargetFragment()?.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                    dismiss()
                }
                is Result.Failure -> {
                    Toast.makeText(activity, it.message.message, Toast.LENGTH_SHORT).show()
                    dismiss()
                    Log.e(TAG, it.message.message)
                }
                else -> {
                    Log.e(TAG, context?.getString(R.string.err_data_default))
                }
            }
        })
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        if (viewModel.isStep2RadioButton(checkedId)) {
            val binding = DataBindingUtil.bind<FragmentOfferDeclineBinding>(view!!)
            binding?.llOfferDeclineReasons?.visibility = View.GONE
            binding?.llOfferDeclineStep2?.visibility = View.VISIBLE
        }
    }

    override fun onRetryClick(position: Int) {
        viewModel.reasons.value?.let {
            if (it.isEmpty()) {
                viewModel.getOfferDeclineReasons()
            } else {
                executeTheOfferDeclineCall()
            }
        }
    }

    private fun executeTheOfferDeclineCall() {
        val binding = DataBindingUtil.bind<FragmentOfferDeclineBinding>(view!!)
        val checkedRadioButtonId = binding?.rgOfferDeclineReasons?.checkedRadioButtonId
        val offerId = arguments?.getString(getString(R.string.param_offer_decline_id))
        viewModel.prepareAndExecuteRequest(offerId!!, checkedRadioButtonId)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_offer_decline_go_back) {
            this.dismiss()
        } else if (v?.id == R.id.btn_offer_decline_send) {
            val binding = DataBindingUtil.bind<FragmentOfferDeclineBinding>(view!!)
            if (binding?.rgOfferDeclineReasons?.checkedRadioButtonId == -1) {
                Toast.makeText(activity, getString(R.string.err_offer_decline_choice_empty), Toast.LENGTH_SHORT).show()
            } else {
                executeTheOfferDeclineCall()
            }
        } else if (v?.id == R.id.btn_offer_decline_send_step2) {
            executeTheOfferDeclineCall()
        }
    }

    companion object {
        @JvmField
        val TAG = this::class.java.simpleName

        fun newInstance(declineOfferId: String?, paramKey: String): DialogFragment {
            val frag = OfferDeclineFragment()
            val args = Bundle()
            args.putString(paramKey, declineOfferId);
            frag.setArguments(args)
            return frag;
        }
    }

    init {
        //Empty Constructor
    }

}