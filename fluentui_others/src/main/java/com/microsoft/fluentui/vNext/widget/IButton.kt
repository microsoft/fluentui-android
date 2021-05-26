/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */
package com.microsoft.fluentui.vNext.widget

import android.graphics.drawable.Drawable
import com.microsoft.fluentui.generator.ButtonSize
import com.microsoft.fluentui.generator.ButtonStyle

interface IButton {

    var buttonIconDrawable: Drawable?
    var buttonStyle: ButtonStyle?
    var buttonSize: ButtonSize?

}