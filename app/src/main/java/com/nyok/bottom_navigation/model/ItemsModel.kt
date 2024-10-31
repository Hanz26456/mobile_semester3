package com.nyok.bottom_navigation.model

import android.os.Parcel
import android.os.Parcelable
import com.nyok.bottom_navigation.R

class ItemsModel(
    var title: String = "",
    var description: String = "",
    var drawableId: Int = R.drawable.cat2_1,
    var images: List<Int> = listOf(R.drawable.cat2_1, R.drawable.cat2_1, R.drawable.cat2_1), // List foto yang diambil dari drawable
    var model: List<String> = listOf("Model A", "Model B", "Model C"), // Model sebagai list string
    var price: Double = 0.0,
    var rating: Double = 0.0,
    var numberInCart: Int = 0,
    var showRecommended: Boolean = false,
    var categoryId: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "", // Membaca title
        parcel.readString() ?: "", // Membaca description
        parcel.readInt(), // Membaca drawableId
        parcel.createIntArray()?.toList() ?: listOf(), // Membaca images sebagai List<Int>
        parcel.createStringArrayList() ?: listOf(), // Membaca model
        parcel.readDouble(), // Membaca price
        parcel.readDouble(), // Membaca rating
        parcel.readInt(), // Membaca numberInCart
        parcel.readByte() != 0.toByte(), // Membaca showRecommended
        parcel.readString().toString() // Membaca categoryId
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title) // Menulis title
        parcel.writeString(description) // Menulis description
        parcel.writeInt(drawableId) // Menulis drawableId
        parcel.writeIntArray(images.toIntArray()) // Menulis images sebagai array
        parcel.writeStringList(model) // Menulis model
        parcel.writeDouble(price) // Menulis price
        parcel.writeDouble(rating) // Menulis rating
        parcel.writeInt(numberInCart) // Menulis numberInCart
        parcel.writeByte(if (showRecommended) 1 else 0) // Menulis showRecommended
        parcel.writeString(categoryId) // Menulis categoryId
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemsModel> {
        override fun createFromParcel(parcel: Parcel): ItemsModel {
            return ItemsModel(parcel)
        }

        override fun newArray(size: Int): Array<ItemsModel?> {
            return arrayOfNulls(size)
        }
    }
}
