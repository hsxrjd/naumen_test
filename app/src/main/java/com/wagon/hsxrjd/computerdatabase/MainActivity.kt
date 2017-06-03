package com.wagon.hsxrjd.computerdatabase

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Window
import butterknife.BindView
import butterknife.ButterKnife
import com.wagon.hsxrjd.computerdatabase.fragment.CardListFragment

class MainActivity : AppCompatActivity() {

    @BindView(R.id.toolbar_main) lateinit var toolbar: Toolbar

    private val tag_lvf: String = "LIST_VIEW_FRAGMENT"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        }

        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        Log.d("TOOLBAR?", "$toolbar")
        setSupportActionBar(toolbar)

        val retainFragment = supportFragmentManager.findFragmentByTag(tag_lvf)
        retainFragment ?:
                supportFragmentManager
                        .beginTransaction()
                        .add(R.id.fragment_container, CardListFragment.newInstance(), tag_lvf)
                        .commit()

    }

    companion object{
        val cardFragmentBackStackName: String = "CARD_FRAGMENT"
    }
}
