//

// Global Tokens represent a unified set of constants to be used by Fluent UI.

//  Copyright (c) Microsoft Corporation. All rights reserved.
//  Licensed under the MIT License.
//
package com.example.theme.token

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.token.GlobalTokens
import com.microsoft.fluentui.theme.token.TokenSet
 class MyGlobalTokens : GlobalTokens() {
     override val borderSize: TokenSet<BorderSizeTokens, Dp> by lazy {
         TokenSet { 5.dp}
     }
 }
