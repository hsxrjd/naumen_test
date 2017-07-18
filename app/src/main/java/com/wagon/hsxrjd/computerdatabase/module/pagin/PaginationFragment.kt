package com.wagon.hsxrjd.computerdatabase.module.pagin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
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
import com.wagon.hsxrjd.computerdatabase.module.pagin.presenter.PaginationPresenter
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
        mPresenter.start()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showPage(id: Int, total: Int) {
        mPageCount.text = String.format(getString(R.string.text_paginator), id + 1, total)
    }

    override fun changeButtonPrevState(state: Boolean) {
        mButtonPrev.isEnabled = state
    }

    override fun changeButtonNextState(state: Boolean) {
        mButtonNext.isEnabled = state
    }

    override fun showMessage(message: String) {

    }

    override fun showMessage(resource: Int) {

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

}