package com.wagon.hsxrjd.computerdatabase

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.wagon.hsxrjd.computerdatabase.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        savedInstanceState ?:
                supportFragmentManager
                        .beginTransaction()
                        .add(R.id.fragment_container, MainFragment.newInstance())
                        .commit()
    }
}
