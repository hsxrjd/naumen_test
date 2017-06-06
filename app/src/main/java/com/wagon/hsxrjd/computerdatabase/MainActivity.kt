package com.wagon.hsxrjd.computerdatabase

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.Window
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.squareup.picasso.Picasso
import com.wagon.hsxrjd.computerdatabase.fragment.CardListFragment
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    @BindView(R.id.toolbar_main) lateinit var mToolbar: Toolbar
    @BindView(R.id.toolbar_title) lateinit var mToolbarTitle: TextView

    private val mNavigator: WeakReference<Navigator> = WeakReference(BaseNavigator.instance)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        }
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val retainListFragment = supportFragmentManager.findFragmentByTag(MainActivity.BACK_STACK_TAG_CARD_LIST)

        retainListFragment ?: let {
            mToolbarTitle.setText(R.string.app_name)
            val fragment = CardListFragment.newInstance()
            mNavigator.get()?.configureTransition(fragment)
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment, MainActivity.BACK_STACK_TAG_CARD_LIST)
                    .commit()
        }
    }

    override fun onResume() {
        mNavigator.get()?.setActivity(this)
        super.onResume()
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }

    companion object {
        val BACK_STACK_TAG_CARD: String = "CARD_FRAGMENT"
        val BACK_STACK_TAG_CARD_LIST: String = "LIST_VIEW_FRAGMENT"
    }
}
