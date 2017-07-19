package com.wagon.hsxrjd.computerdatabase.module.container

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wagon.hsxrjd.computerdatabase.MainActivity
import com.wagon.hsxrjd.computerdatabase.MainApplication
import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.dagger.container.ContainerComponent
import com.wagon.hsxrjd.computerdatabase.dagger.container.ListInteractorModule
import com.wagon.hsxrjd.computerdatabase.log.LoggedFragment
import com.wagon.hsxrjd.computerdatabase.module.list.CardListFragment
import com.wagon.hsxrjd.computerdatabase.module.pagin.PaginationFragment

/**
 * Created by erychkov on 7/14/17.
 */
class ContainerFragment : LoggedFragment() {
    override fun getClassName(): String {
        return className
    }

    companion object {
        val className = "ContainerFragment"
    }

    lateinit var containerComponent: ContainerComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        containerComponent = MainApplication.appComponent.plus(ListInteractorModule())
        createChildFragments()

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_container, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    fun createChildFragments() {
        val fragmentL = CardListFragment.newInstance(containerComponent)
        val fragmentP = PaginationFragment.newInstance(containerComponent)

        childFragmentManager
                .beginTransaction()
                .add(R.id.list_container, fragmentL, MainActivity.BACK_STACK_TAG_CARD_LIST)
                .add(R.id.paginator_container, fragmentP, MainActivity.BACK_STACK_TAG_PAGINATOR)
                .commit()
    }
}