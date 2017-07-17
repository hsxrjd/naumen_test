package com.wagon.hsxrjd.computerdatabase.navigator

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.wagon.hsxrjd.computerdatabase.model.Card

/**
 * Created by hsxrjd on 05.06.17.
 */
interface Navigator {
    fun resumeCardListFragment()
    fun setActivity(activity: AppCompatActivity)
    fun setToolbarTitle(title: String)
    fun setToolbarTitle(resource: Int)
    fun startCardFragment(view: View, card: Card)
    fun enableToolbar(toggle:Boolean)
}