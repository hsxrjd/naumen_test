package com.wagon.hsxrjd.computerdatabase

import android.os.Build
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.transition.Slide
import android.util.Log
import android.view.View
import android.widget.TextView
import com.wagon.hsxrjd.computerdatabase.fragment.CardFragment
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

    override fun configureTransition(fragment: Fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val transition = Slide()
            fragment.sharedElementReturnTransition = transition
            fragment.sharedElementEnterTransition = transition
            fragment.exitTransition = transition
            fragment.enterTransition = transition
            fragment.allowEnterTransitionOverlap = false
            fragment.allowReturnTransitionOverlap = false
        }
    }

    override fun startCardFragment(view: View, card: Card) {
        val fragment = CardFragment.newInstance(card.id, card.name)
        enableToolbar(true)
        configureTransition(fragment)
        setToolbarTitle(card.name)
        mActivity.get()?.supportFragmentManager
                ?.beginTransaction()
                ?.addSharedElement(view, ViewCompat.getTransitionName(mToolbarTitle.get()))
                ?.replace(R.id.fragment_container, fragment)
                ?.addToBackStack(MainActivity.BACK_STACK_TAG_CARD)
                ?.commit()
    }


    private object Holder {
        val mInstance = BaseNavigator()
    }

    companion object {
        val instance: Navigator by lazy { Holder.mInstance }
    }

}