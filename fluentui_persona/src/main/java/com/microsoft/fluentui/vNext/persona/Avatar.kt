package com.microsoft.fluentui.vNext.persona

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import com.microsoft.fluentui.generator.AvatarSize
import com.microsoft.fluentui.generator.AvatarStyle
import com.microsoft.fluentui.util.FieldUpdateListener

class Avatar : IAvatar {
    var fieldUpdateListener: FieldUpdateListener? = null

    override var name: String = ""
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }
    override var email: String = ""
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }
    override var avatarImageBitmap: Bitmap? = null
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }
    override var avatarImageDrawable: Drawable? = null
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }
    override var avatarImageResourceId: Int = -1
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }
    override var avatarImageUri: Uri? = null
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }
    override var avatarBackgroundColor: Int? = null
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }
    override var avatarForegroundColor: Int? = null
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }
    override var avatarRingColor: Int? = null
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }
    override var isRingVisible: Boolean = false
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }
    override var isTransparent: Boolean = true
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }
    override var isOOF: Boolean = false
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }
    override var avatarStyle: AvatarStyle? = AvatarStyle.BASE
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }
    override var avatarSize: AvatarSize? = AvatarSize.LARGE
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }
    override var presence: AvatarPresence = AvatarPresence.NONE
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }
}