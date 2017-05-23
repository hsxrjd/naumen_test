package com.wagon.hsxrjd.computerdatabase.model

/**
 * Created by hsxrjd on 23.05.17.
 */
class Card(
        val id: Int,
        val name: String,
        var imageUrl: String,
        var company: Company,
        var description: String = ""
)