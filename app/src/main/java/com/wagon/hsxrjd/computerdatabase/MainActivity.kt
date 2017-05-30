package com.wagon.hsxrjd.computerdatabase

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wagon.hsxrjd.computerdatabase.fragment.CardListFragment

class MainActivity : AppCompatActivity() {

    private val tag_lvf: String = "LIST_VIEW_FRAGMENT"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retainFragment = supportFragmentManager.findFragmentByTag(tag_lvf)
        retainFragment ?:
                supportFragmentManager
                        .beginTransaction()
                        .add(R.id.fragment_container, CardListFragment.newInstance(), tag_lvf)
                        .commit()

    }
}
