package com.zenjob.ui.common.dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.zenjob.R


class ErrorDialog : DialogFragment(), View.OnClickListener {

    companion object {
        @JvmField
        val TAG = this::class.java.simpleName

        fun newInstance(errMsg: String, paramKey: String): DialogFragment {
            val frag = ErrorDialog()
            val args = Bundle()
            args.putString(paramKey, errMsg);
            frag.setArguments(args)
            return frag;
        }
    }

    init {
        //Empty Constructor
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(activity, R.style.Theme_Dialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val view = View.inflate(context, R.layout.dialog_error, null)
        val text = view.findViewById(R.id.tv_error_dialog_text) as TextView
        text.text = arguments?.getString(getString(R.string.param_error_message))

        val dialogButton = view.findViewById(R.id.btn_error_dialog) as Button
        dialogButton.setOnClickListener(this)

        dialog.setContentView(view)
        return dialog;
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_error_dialog) {
            this.dismiss()
        }
    }
}