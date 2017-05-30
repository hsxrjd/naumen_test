package com.wagon.hsxrjd.computerdatabase.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by hsxrjd on 23.05.17.
 */
class Company(
        val id: Int,
        val name: String
) : Parcelable {
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            it.writeInt(id)
            it.writeString(name)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    constructor(p: Parcel) : this(p.readInt(), p.readString())

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Company> = object : Parcelable.Creator<Company> {
            override fun newArray(size: Int): Array<Company?> {
                return arrayOfNulls(size)
            }

            override fun createFromParcel(source: Parcel): Company {
                return Company(source)
            }
        }
    }
}