//

// Global Tokens represent a unified set of constants to be used by Fluent UI.

//  Copyright (c) Microsoft Corporation. All rights reserved.
//  Licensed under the MIT License.
//
package com.example.theme.token


import androidx.compose.ui.graphics.Color
import com.microsoft.fluentui.theme.token.GlobalTokens
import com.microsoft.fluentui.theme.token.TokenSet

class MyGlobalTokens : GlobalTokens() {
    override val brandColor: TokenSet<BrandColorTokens, Color> by lazy {
        TokenSet { token ->
            when (token) {
                BrandColorTokens.Color10 -> Color(0xFF443168)
                BrandColorTokens.Color20 -> Color(0xFF584183)
                BrandColorTokens.Color30 -> Color(0xFF430E60)
                BrandColorTokens.Color40 -> Color(0xFF7B64A3)
                BrandColorTokens.Color50 -> Color(0xFF5B1382)
                BrandColorTokens.Color60 -> Color(0xFF6C179A)
                BrandColorTokens.Color70 -> Color(0xFF9D87C4)
                BrandColorTokens.Color80 -> Color(0xFF7719AA)
                BrandColorTokens.Color90 -> Color(0xFF862EB5)
                BrandColorTokens.Color100 -> Color(0xFFC0AAE4)
                BrandColorTokens.Color110 -> Color(0xFFCEBBED)
                BrandColorTokens.Color120 -> Color(0xFFDCCDF6)
                BrandColorTokens.Color130 -> Color(0xFFA864CD)
                BrandColorTokens.Color140 -> Color(0xFFEADEFF)
                BrandColorTokens.Color150 -> Color(0xFFD1ABE6)
                BrandColorTokens.Color160 -> Color(0xFFE6D1F2)
            }
        }
    }
}
