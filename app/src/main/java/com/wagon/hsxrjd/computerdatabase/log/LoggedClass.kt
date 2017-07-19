package com.wagon.hsxrjd.computerdatabase.log

/**
 * Created by erychkov on 7/19/17.
 */
interface LoggedClass {
    fun getClassName(): String

    enum class Tag(name: String)
}