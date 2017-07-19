package com.wagon.hsxrjd.computerdatabase.model

import com.wagon.hsxrjd.computerdatabase.model.db.CardRealm
import com.wagon.hsxrjd.computerdatabase.model.db.CardRealmLight
import com.wagon.hsxrjd.computerdatabase.model.db.CompanyRealm
import com.wagon.hsxrjd.computerdatabase.model.db.PageRealm
import com.wagon.hsxrjd.computerdatabase.model.net.Card
import com.wagon.hsxrjd.computerdatabase.model.net.Company
import com.wagon.hsxrjd.computerdatabase.model.net.Page
import io.realm.RealmList
import io.realm.RealmModel

/**
 * Created by erychkov on 7/18/17.
 */

fun PageRealm.mutate(): Page {
    val page = Page()
    page.items = this.items.map { it.mutate() }
    page.page = this.page
    page.offset = this.offset
    page.total = total
    return page
}

fun Page.mutate(): PageRealm {
    val pageRealm: PageRealm = PageRealm()
    pageRealm.items.addAll(this.items.map { it.mutateLight() })
    pageRealm.offset = this.offset
    pageRealm.total = this.total
    pageRealm.page = this.page
    return pageRealm
}

fun CardRealm.mutate(): Card {
    val card = Card()
    card.id = this.id
    card.name = this.name
    card.imageUrl = this.imageUrl
    card.company = this.company?.mutate()
    card.description = this.description
    return card
}


fun Card.mutate(): CardRealm {
    val card: CardRealm = CardRealm()
    card.id = this.id
    card.name = this.name
    card.imageUrl = this.imageUrl
    card.company = this.company?.mutate()
    card.description = this.description
    return card
}

fun Card.mutateLight(): CardRealmLight {
    val card: CardRealmLight = CardRealmLight()
    card.id = this.id
    card.name = this.name
    card.company = this.company?.mutate()
    return card
}

fun CardRealmLight.mutate(): Card {
    val card: Card = Card()
    card.id = this.id
    card.name = this.name
    card.company = this.company?.mutate()
    return card
}

fun CompanyRealm.mutate(): Company {
    val company = Company()
    company.id = this.id
    company.name = this.name
    return company
}

fun Company.mutate(): CompanyRealm {
    val company = CompanyRealm()
    company.id = this.id
    company.name = this.name
    return company
}

inline fun <T, R : RealmModel> Iterable<T>.mapToRealm(transform: (T) -> R): RealmList<R> {
    return mapTo(RealmList<R>(), transform)
}