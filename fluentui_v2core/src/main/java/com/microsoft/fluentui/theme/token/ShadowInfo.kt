//
//  Copyright (c) Microsoft Corporation. All rights reserved.
//  Licensed under the MIT License.
//

package com.microsoft.fluentui.theme.token

import androidx.compose.ui.unit.Dp

data class ShadowInfo(val colorOne: FluentColor, val blurOne: Int, val xOne: Int, val yOne: Int,
                      val colorTwo: FluentColor, val blurTwo: Int, val xTwo: Int, val yTwo: Int,
                      val elevation: Dp)
