package com.zenjob.utils.ext

import android.content.Context
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by suyashg
 *
 * Utiliy class for activity fragments operations
 */
    @Suppress("UNCHECKED_CAST")
    fun <T> FragmentActivity.findFragmentById(@IdRes id: Int): T = supportFragmentManager.findFragmentById(id) as T

    inline fun FragmentActivity.replaceFragment(containerViewId: Int, f: () -> Fragment, bundle: Bundle? = null): Fragment? {
        return f().apply {
            this.arguments = bundle
            supportFragmentManager?.beginTransaction()?.replace(containerViewId, this)?.commit()
        }
    }

    inline fun FragmentActivity.addFragment(containerViewId: Int, f: () -> Fragment, bundle: Bundle): Fragment? {
        val manager = supportFragmentManager
        return f().apply {
            this.arguments = bundle
            manager?.beginTransaction()?.add(containerViewId, this)?.addToBackStack(null)?.commit()
        }
    }

inline fun FragmentActivity.hideKeyboard(activity: FragmentActivity?) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = activity?.getCurrentFocus()
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(activity)
    }
    imm?.hideSoftInputFromWindow(view?.windowToken, 0)
    }
