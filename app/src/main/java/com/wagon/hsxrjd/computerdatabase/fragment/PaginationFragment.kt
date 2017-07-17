package com.wagon.hsxrjd.computerdatabase.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.dagger.container.ContainerComponent
import com.wagon.hsxrjd.computerdatabase.dagger.paginator.PaginationModule
import com.wagon.hsxrjd.computerdatabase.presenter.PaginationPresenter
import com.wagon.hsxrjd.computerdatabase.view.PaginationFragmentView
import javax.inject.Inject

/**
 * Created by erychkov on 7/14/17.
 */
class PaginationFragment : Fragment(), PaginationFragmentView {

    companion object {
        fun newInstance(containerComponent: ContainerComponent): PaginationFragment {
            val fragment = PaginationFragment()
            containerComponent.plus(PaginationModule()).inject(fragment)
            return fragment
        }
    }

    @Inject lateinit var mPresenter: PaginationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.setView(this)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showPage(id: Int, total: Int) {
//        mPageCount.text = "Page ${id + 1} of $total"
        mPageCount.text = String.format(getString(R.string.text_paginator), id + 1, total)
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(resource: Int) {
        Toast.makeText(context, resource, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(resource: Int, vararg varargs: Any) {
        val message = getString(resource, varargs)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    @BindView(R.id.button_prev) lateinit var mButtonPrev: Button
    @BindView(R.id.button_next) lateinit var mButtonNext: Button
    @BindView(R.id.text_count) lateinit var mPageCount: TextView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_pagination, container, false)
        ButterKnife.bind(this, view)
        mButtonNext.setOnClickListener { mPresenter.onPressNext() }
        mButtonPrev.setOnClickListener { mPresenter.onPressPrev() }
        return view
    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }
}