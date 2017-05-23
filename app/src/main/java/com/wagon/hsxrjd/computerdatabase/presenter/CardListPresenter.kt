package com.wagon.hsxrjd.computerdatabase.presenter

import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import com.wagon.hsxrjd.computerdatabase.view.CardListFragmentView

/**
 * Created by hsxrjd on 24.05.17.
 */
class CardListPresenter(private var mDataSource: CardDataSource) : BasePresenter<CardListFragmentView>() {
    fun start() {}

}