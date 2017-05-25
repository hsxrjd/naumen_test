package com.wagon.hsxrjd.computerdatabase.view

import com.wagon.hsxrjd.computerdatabase.model.Card

/**
 * Created by hsxrjd on 23.05.17.
 */


interface CardListFragmentView : BaseView{
    fun showLoading()
    fun hideLoading()
    fun showMessage(message: String)
    fun showCardList(cardList: List<Card>)
    fun showCard(card: Card)
}