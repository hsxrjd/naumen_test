package com.wagon.hsxrjd.computerdatabase

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.Window
import butterknife.BindView
import butterknife.ButterKnife
import com.wagon.hsxrjd.computerdatabase.fragment.CardListFragment

class MainActivity : AppCompatActivity() {

    @BindView(R.id.toolbar_main) lateinit var mToolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        }

        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        setSupportActionBar(mToolbar)
        val retainListFragment = supportFragmentManager.findFragmentByTag(MainActivity.cardListFragmentBackStackName)
        retainListFragment ?:
                supportFragmentManager
                        .beginTransaction()
                        .add(R.id.fragment_container, CardListFragment.newInstance(), MainActivity.cardListFragmentBackStackName)
                        .commit()

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                supportActionBar?.setTitle(R.string.app_name)
                supportActionBar?.setDisplayShowHomeEnabled(false)
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportFragmentManager.popBackStack()
                val retainFragment = supportFragmentManager.findFragmentByTag(MainActivity.cardListFragmentBackStackName)
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, retainFragment, MainActivity.cardListFragmentBackStackName)
                        .commit()
            }
        }
        return true
    }

    companion object {
        val cardFragmentBackStackName: String = "CARD_FRAGMENT"
        val cardListFragmentBackStackName: String = "LIST_VIEW_FRAGMENT"
    }
}
