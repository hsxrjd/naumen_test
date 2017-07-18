package com.wagon.hsxrjd.computerdatabase.model

import com.wagon.hsxrjd.computerdatabase.model.db.CardRealm
import com.wagon.hsxrjd.computerdatabase.model.db.CompanyRealm
import com.wagon.hsxrjd.computerdatabase.model.db.PageRealm
import com.wagon.hsxrjd.computerdatabase.model.net.Card
import com.wagon.hsxrjd.computerdatabase.model.net.Company
import com.wagon.hsxrjd.computerdatabase.model.net.Page

/**
 * Created by erychkov on 7/18/17.
 */


fun PageRealm.mutate(): Page {
    return Page(
            this.items.map { it.mutate() },
            this.page,
            this.offset,
            this.total
    )
}

fun Page.mutate(): PageRealm {
    val pageRealm: PageRealm = PageRealm()
    this.items.mapTo(pageRealm.items) { it.mutate() }
    pageRealm.offset = this.offset
    pageRealm.total = this.total
    pageRealm.page = this.page
    return pageRealm
}

fun CardRealm.mutate(): Card {
    return Card(
            this.id,
            this.name,
            this.imageUrl,
            this.company?.mutate(),
            this.description)
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