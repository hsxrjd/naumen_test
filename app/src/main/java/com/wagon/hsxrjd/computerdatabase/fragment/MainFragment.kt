package com.wagon.hsxrjd.computerdatabase.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.wagon.hsxrjd.computerdatabase.R

class MainFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_main, container, false)
    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }


}
