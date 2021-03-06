package com.wagon.hsxrjd.computerdatabase

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.wagon.hsxrjd.computerdatabase.log.LoggedActivity
import com.wagon.hsxrjd.computerdatabase.module.container.ContainerFragment
import com.wagon.hsxrjd.computerdatabase.navigator.Navigator
import javax.inject.Inject

class MainActivity : LoggedActivity() {
    override fun getClassName(): String {
        return className
    }

    @BindView(R.id.toolbar_main) lateinit var mToolbar: Toolbar
    @BindView(R.id.toolbar_title) lateinit var mToolbarTitle: TextView

    @Inject lateinit var mNavigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainApplication.appComponent.inject(this)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val retainListFragment = supportFragmentManager.findFragmentByTag(MainActivity.BACK_STACK_TAG_LIST_MAIN)

        retainListFragment ?: let {
            mToolbarTitle.setText(R.string.app_name)
            val fragment = ContainerFragment()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit()
        }
    }

    override fun onResume() {
        mNavigator.setActivity(this)
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
        val className = "MainActivity"
        val BACK_STACK_TAG_CARD: String = "CARD_FRAGMENT"
        val BACK_STACK_TAG_LIST_MAIN: String = "LIST_VIEW_MAIN"
        val BACK_STACK_TAG_CARD_LIST: String = "LIST_VIEW_FRAGMENT"
        val BACK_STACK_TAG_PAGINATOR: String = "LIST_VIEW_PAGINATOR"
    }
}
