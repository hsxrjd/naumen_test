package com.wagon.hsxrjd.computerdatabase.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by hsxrjd on 23.05.17.
 */
class Card(
        val id: Int,
        val name: String,
        var imageUrl: String,
        var company: Company?,
        var description: String = ""
) : Parcelable {

    constructor(p: Parcel) : this(p.readInt(), p.readString(), p.readString(), p.readParcelable(Company::class.java.classLoader))

    override fun writeToParcel(dest: Parcel?, flags: Int) {

        dest?.let {
            it.writeInt(id)
            it.writeString(name)
            it.writeString(imageUrl)
            company ?: it.writeParcelable(company, 0)
            it.writeString(description)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Card> = object : Parcelable.Creator<Card> {
            override fun newArray(size: Int): Array<Card?> {
                return arrayOfNulls(size)
            }

            override fun createFromParcel(source: Parcel): Card {
                return Card(source)
            }
        }
    }
}