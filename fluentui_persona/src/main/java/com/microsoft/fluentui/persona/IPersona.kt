/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.persona

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import java.io.Serializable

interface IPersona : IAvatar {
    var subtitle: String
    var footer: String
}

data class Persona(override var name: String = "", override var email: String = "") : IPersona, Serializable {
    override var subtitle: String = ""
    override var footer: String = ""
    override var avatarImageBitmap: Bitmap? = null
    override var avatarImageDrawable: Drawable? = null
    override var avatarImageResourceId: Int? = null
    override var avatarImageUri: Uri? = null
    override var avatarBackgroundColor: Int? = null
}