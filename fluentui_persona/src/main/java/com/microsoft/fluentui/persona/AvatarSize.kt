/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.persona

import android.content.Context
import com.microsoft.fluentui.persona.R

/**
 * The [id] passed to this enum class references a dimens resource that [getDisplayValue] converts into an int.
 * This int specifies the layout width and height for the AvatarView.
 */
enum class AvatarSize(private val id: Int) {
    XSMALL(R.dimen.fluentui_avatar_size_xsmall),
    SMALL(R.dimen.fluentui_avatar_size_small),
    MEDIUM(R.dimen.fluentui_avatar_size_medium),
    LARGE(R.dimen.fluentui_avatar_size_large),
    XLARGE(R.dimen.fluentui_avatar_size_xlarge),
    XXLARGE(R.dimen.fluentui_avatar_size_xxlarge);

    /**
     * This method uses [context] to convert the [id] resource into an int that becomes
     * AvatarView's layout width and height
     */
    internal fun getDisplayValue(context: Context): Int {
        return context.resources.getDimension(id).toInt()
    }
}