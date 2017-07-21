package com.wagon.hsxrjd.computerdatabase.module.card.Interactor

import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.adapter.attribute.*
import com.wagon.hsxrjd.computerdatabase.model.net.Card
import com.wagon.hsxrjd.computerdatabase.model.net.Company
import com.wagon.hsxrjd.computerdatabase.model.source.strategy.OperationFactory
import io.reactivex.Observable

/**
 * Created by erychkov on 7/17/17.
 */
class CardInteractorImpl(val operationFactory: OperationFactory) : CardInteractor {

    override fun getCard(id: Int): Observable<List<Attribute>> {
        return operationFactory
                .buildFetchCardOperation(id)
                .perform()
                .map { card: Card ->
                    val list: MutableList<Attribute> = mutableListOf()
                    list.add(TextAttribute(card.name))
                    card.company?.let(this::toCompanyAttr)?.let(list::add)
                    card.description?.let(this::toDescriptionAttr)?.let(list::add)
                    card.imageUrl?.let(this::toImageAttribute)?.let(list::add)
                    list
                }
    }

    fun toCompanyAttr(company: Company): TextAttribute = TextAttribute(company.name, R.string.company)
    fun toDescriptionAttr(description: String): ClickableTextAttribute = ClickableTextAttribute(description, R.string.description)
    fun toImageAttribute(url: String): ImageAttribute = ImageAttribute(url)

    override fun getSimilarTo(id: Int): Observable<List<Attribute>> {
        return operationFactory
                .buildFetchSimilarOperation(id)
                .perform()
                .map {
                    cards: List<Card> ->
                    val list: MutableList<Attribute> = mutableListOf(TextAttribute("", R.string.you_might_be_looking_for))
                    cards.mapTo(list, { card -> CardAttribute(card) })
                    list
                }
    }
}