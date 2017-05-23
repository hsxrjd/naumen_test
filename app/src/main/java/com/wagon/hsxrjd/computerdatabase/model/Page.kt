package com.wagon.hsxrjd.computerdatabase.model

/**
 * Created by hsxrjd on 23.05.17.
 */
class Page (
        val items: List<Card>,
        val page: Int,
        val offset: Int,
        val total: Int
)