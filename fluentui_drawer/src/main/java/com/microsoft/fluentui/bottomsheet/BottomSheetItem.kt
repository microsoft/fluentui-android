/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.bottomsheet

import android.content.Context
import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import android.view.View
import com.microsoft.fluentui.drawer.R
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
    val customBitmap: Bitmap?
    val disabled: Boolean
    @DrawableRes
    val accessoryImageId: Int
    val accessoryBitmap: Bitmap?

    val roleDescription: String
    val customAccessoryView: View?

    @JvmOverloads
    constructor(
        id: Int = NO_ID,
        @DrawableRes imageId: Int = NO_ID,
        title: String,
        subtitle: String = "",
        useDivider: Boolean = false,
        @ColorInt imageTint: Int = 0,
        imageTintType: ImageTintType = ImageTintType.DEFAULT,
        customBitmap: Bitmap? = null,
        disabled: Boolean = false,
        @DrawableRes accessoryImageId: Int = NO_ID,
        accessoryBitmap: Bitmap? = null,
        roleDescription: String = "",
        customAccessoryView: View? = null
    ) {
        this.id = id
        this.imageId = imageId
        this.title = title
        this.subtitle = subtitle
        this.useDivider = useDivider
        this.imageTint = imageTint
        this.imageTintType = imageTintType
        this.customBitmap = customBitmap
        this.disabled = disabled
        this.accessoryImageId = accessoryImageId
        this.accessoryBitmap = accessoryBitmap
        this.roleDescription = roleDescription
        this.customAccessoryView = customAccessoryView
    }

    private constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        imageId = parcel.readInt(),
        title = parcel.readString() ?: "",
        subtitle = parcel.readString() ?: "",
        useDivider = parcel.readInt() == 1,
        imageTint = parcel.readInt(),
        imageTintType = ImageTintType.values()[parcel.readInt()],
        customBitmap = parcel.readParcelable(Bitmap::class.java.classLoader),
        disabled = parcel.readInt() == 1,
        accessoryImageId = parcel.readInt(),
        accessoryBitmap = parcel.readParcelable(Bitmap::class.java.classLoader),
        roleDescription = parcel.readString() ?: "",
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(imageId)
        parcel.writeString(title)
        parcel.writeString(subtitle)
        parcel.writeInt(if (useDivider) 1 else 0)
        parcel.writeInt(imageTint)
        parcel.writeInt(imageTintType.ordinal)
        parcel.writeValue(customBitmap)
        parcel.writeInt(if (disabled) 1 else 0)
        parcel.writeInt(accessoryImageId)
        parcel.writeValue(accessoryBitmap)
        parcel.writeString(roleDescription)
    }

    override fun describeContents(): Int = 0
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BottomSheetItem

        if (id != other.id) return false
        if (imageId != other.imageId) return false
        if (title != other.title) return false
        if (subtitle != other.subtitle) return false
        if (useDivider != other.useDivider) return false
        if (imageTint != other.imageTint) return false
        if (imageTintType != other.imageTintType) return false
        if (customBitmap != other.customBitmap) return false
        if (disabled != other.disabled) return false
        if (accessoryImageId != other.accessoryImageId) return false
        if (accessoryBitmap != other.accessoryBitmap) return false
        if (roleDescription != other.roleDescription) return false
        if (customAccessoryView != other.customAccessoryView) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + imageId
        result = 31 * result + title.hashCode()
        result = 31 * result + subtitle.hashCode()
        result = 31 * result + useDivider.hashCode()
        result = 31 * result + imageTint
        result = 31 * result + imageTintType.hashCode()
        result = 31 * result + (customBitmap?.hashCode() ?: 0)
        result = 31 * result + disabled.hashCode()
        result = 31 * result + accessoryImageId.hashCode()
        result = 31 * result + (accessoryBitmap?.hashCode() ?: 0)
        result = 31 * result + roleDescription.hashCode()
        result = 31 * result + (customAccessoryView?.hashCode() ?: 0)
        return result
    }

    companion object {
        const val NO_ID = View.NO_ID

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