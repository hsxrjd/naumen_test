package com.wagon.hsxrjd.computerdatabase.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wagon.hsxrjd.computerdatabase.MainActivity
import com.wagon.hsxrjd.computerdatabase.R

/**
 * Created by erychkov on 7/14/17.
 */
class ContainerFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_container, container, false)
        val fragmentL = CardListFragment.newInstance()
        val fragmentP = PaginationFragment()

        fragmentManager
                .beginTransaction()
                .add(R.id.list_container, fragmentL, MainActivity.BACK_STACK_TAG_CARD_LIST)
                .add(R.id.paginator_container, fragmentP, MainActivity.BACK_STACK_TAG_PAGINATOR)
                .commit()
        return view
    }
}