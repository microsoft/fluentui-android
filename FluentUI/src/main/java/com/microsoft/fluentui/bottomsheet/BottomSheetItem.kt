/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.bottomsheet

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.StyleRes
import android.widget.ImageView
import com.microsoft.fluentui.R
import com.microsoft.fluentui.util.ThemeUtil

class BottomSheetItem : Parcelable {
    interface OnClickListener {
        fun onBottomSheetItemClick(item: BottomSheetItem)
    }

    /**
     *  Use [ImageTintType.DEFAULT] to use the default [imageTint]. Use [ImageTintType.CUSTOM] to
     *  customize the [imageTint]. Use [ImageTintType.NONE] to void the [imageTint].
     */
    enum class ImageTintType {
        DEFAULT, CUSTOM, NONE
    }

    val id: Int
    @DrawableRes
    val imageId: Int
    val title: String
    val subtitle: String
    val useDivider: Boolean
    @ColorInt
    val imageTint: Int
    val imageTintType: ImageTintType
    val customImage: ImageView?
    @StyleRes
    val titleStyleId: Int
    @StyleRes
    val subtitleStyleId: Int

    @JvmOverloads
    constructor(
        id: Int = NO_ID,
        @DrawableRes imageId: Int = NO_ID,
        title: String,
        subtitle: String = "",
        useDivider: Boolean = false,
        @ColorInt imageTint: Int = 0,
        imageTintType: ImageTintType = ImageTintType.DEFAULT,
        customImage: ImageView? = null,
        @StyleRes titleStyleId : Int = 0,
        @StyleRes subtitleStyleId: Int = 0
    ) {
        this.id = id
        this.imageId = imageId
        this.title = title
        this.subtitle = subtitle
        this.useDivider = useDivider
        this.imageTint = imageTint
        this.imageTintType = imageTintType
        this.customImage = customImage
        this.titleStyleId = titleStyleId
        this.subtitleStyleId = subtitleStyleId
    }

    private constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        imageId = parcel.readInt(),
        title = parcel.readString() ?: "",
        subtitle = parcel.readString() ?: "",
        useDivider = parcel.readInt() == 1,
        imageTint = parcel.readInt(),
        imageTintType = ImageTintType.values()[parcel.readInt()]
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(imageId)
        parcel.writeString(title)
        parcel.writeString(subtitle)
        parcel.writeInt(if (useDivider) 1 else 0)
        parcel.writeInt(imageTint)
        parcel.writeInt(imageTintType.ordinal)
    }

    override fun describeContents(): Int = 0

    companion object {
        const val NO_ID = -1

        @JvmField
        val CREATOR = object : Parcelable.Creator<BottomSheetItem> {
            override fun createFromParcel(source: Parcel): BottomSheetItem = BottomSheetItem(source)
            override fun newArray(size: Int): Array<BottomSheetItem?> = arrayOfNulls(size)
        }
    }
}

fun BottomSheetItem.getImageTint(context: Context): Int? {
    return when (imageTintType) {
        BottomSheetItem.ImageTintType.DEFAULT -> ThemeUtil.getThemeAttrColor(context, R.attr.fluentuiBottomSheetIconColor)
        BottomSheetItem.ImageTintType.CUSTOM -> imageTint
        BottomSheetItem.ImageTintType.NONE -> null
    }
}