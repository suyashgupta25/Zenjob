package com.zenjob.ui.common.dialogs

import android.app.Dialog
import android.support.v4.app.FragmentActivity
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.zenjob.R

class ErrorDialog : View.OnClickListener {

    var dialog: Dialog? = null

    fun showDialog(activity: FragmentActivity?, msg: String) {
        dialog = Dialog(activity, R.style.Theme_Dialog)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        dialog?.setContentView(R.layout.dialog_error)

        val text = dialog?.findViewById(R.id.tv_error_dialog_text) as TextView
        text.text = msg

        val dialogButton = dialog?.findViewById(R.id.btn_error_dialog) as Button
        dialogButton.setOnClickListener(this)
        dialog?.show()
    }

    override fun onClick(v: View?) {
        dialog?.dismiss()
    }
}