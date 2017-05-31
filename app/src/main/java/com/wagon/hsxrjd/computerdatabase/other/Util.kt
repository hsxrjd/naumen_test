package com.wagon.hsxrjd.computerdatabase.other

/**
 * Created by hsxrjd on 01.06.17.
 */

/*
* Just for comfortable use
* */
fun <E> MutableList<in E>.addUnique(element: E) {
    if (!this.contains(element)) this.add(element)
}

fun <E> MutableList<in E>.addUnique(elements: Iterable<E>) {
    elements.forEach { this.addUnique(it) }
}

fun <E> MutableList<in E>.addUnique(elements: Sequence<E>) {
    elements.forEach { this.addUnique(it) }
}

fun <E> MutableList<in E>.addUnique(elements: Array<E>) {
    elements.forEach { this.addUnique(it) }
}