package com.wagon.hsxrjd.computerdatabase.navigator

import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.wagon.hsxrjd.computerdatabase.MainActivity
import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.card.CardFragment
import com.wagon.hsxrjd.computerdatabase.model.Card
import java.lang.ref.WeakReference

/**
 * Created by hsxrjd on 05.06.17.
 */
class BaseNavigator : Navigator {
    private var mActivity: WeakReference<AppCompatActivity?> = WeakReference(null)
    private var mToolbarTitle: WeakReference<TextView?> = WeakReference(null)

    override fun resumeCardListFragment() {
        enableToolbar(false)
        setToolbarTitle(R.string.app_name)
    }

    override fun enableToolbar(toggle: Boolean) {
        val supportActionBar = mActivity.get()?.supportActionBar
        supportActionBar?.setDisplayShowHomeEnabled(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(toggle)
    }

    override fun setToolbarTitle(title: String) {
        mToolbarTitle.get()?.text = title
    }

    override fun setToolbarTitle(resource: Int) {
        mToolbarTitle.get()?.setText(resource)
    }

    override fun setActivity(activity: AppCompatActivity) {
        mActivity = WeakReference(activity)
        mToolbarTitle = WeakReference((activity as MainActivity).mToolbarTitle)
    }

    override fun startCardFragment(view: View, card: Card) {
        val fragment = CardFragment.newInstance(card.id, card.name)
        enableToolbar(true)
        setToolbarTitle(card.name)
        (mActivity.get()?.supportFragmentManager as FragmentManager)
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(MainActivity.BACK_STACK_TAG_CARD + card.id)
                .commit()
    }


//    private object Holder {
//        val mInstance = BaseNavigator()
//    }
//
//    companion object {
//        val instance: Navigator by lazy { Holder.mInstance }
//    }

}