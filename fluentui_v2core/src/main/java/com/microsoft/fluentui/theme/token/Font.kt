//
//  Copyright (c) Microsoft Corporation. All rights reserved.
//  Licensed under the MIT License.
//

package com.microsoft.fluentui.theme.token

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit

data class FontSize(val size: TextUnit, val lineHeight:TextUnit)
data class FontInfo(val name: String? = null, val fontSize: FontSize, val weight:FontWeight = FontWeight.Normal)

