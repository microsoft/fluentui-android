/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.popupmenu

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes

/**
 * [id] is the unique identifier for this item.
 * [title] is the primary text for the item.
 * [iconResourceId] adds an icon to the start of the item.
 * [isChecked] will check or uncheck either the radio button or checkbox of the item, if present.
 * [showDividerBelow] will render a divider at the bottom of the view representing the item.
 */
class PopupMenuItem : Parcelable {
    interface OnClickListener {
        fun onPopupMenuItemClicked(popupMenuItem: PopupMenuItem)
    }

    val id: Int
    val title: String
    @DrawableRes
    val iconResourceId: Int?
    var isChecked: Boolean
    val showDividerBelow: Boolean

    @JvmOverloads
    constructor(
        id: Int,
        title: String,
        @DrawableRes
        iconResourceId: Int? = null,
        isChecked: Boolean = false,
        showDividerBelow: Boolean = false
    ) {
        this.id = id
        this.title = title
        this.iconResourceId = iconResourceId
        this.isChecked = isChecked
        this.showDividerBelow = showDividerBelow
    }

    private constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        title = parcel.readString() ?: "",
        iconResourceId = parcel.readInt(),
        isChecked = parcel.readByte() != 0.toByte(),
        showDividerBelow = parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeValue(iconResourceId)
        parcel.writeByte(if (isChecked) 1 else 0)
        parcel.writeByte(if (showDividerBelow) 1 else 0)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<PopupMenuItem> {
        override fun createFromParcel(parcel: Parcel): PopupMenuItem = PopupMenuItem(parcel)
        override fun newArray(size: Int): Array<PopupMenuItem?> = arrayOfNulls(size)
    }
}