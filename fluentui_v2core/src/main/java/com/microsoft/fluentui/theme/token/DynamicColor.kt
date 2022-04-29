//
//  Copyright (c) Microsoft Corporation. All rights reserved.
//  Licensed under the MIT License.
//

package com.microsoft.fluentui.theme.token

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.ui.graphics.Color

data class DynamicColor (val light:Color, val lightHighContrast :Color? = null,
                         val lightElevated: Color? = null, val lightElevatedHighContrast: Color? = null,
                         val dark: Color,
                         var darkHighContrast: Color? = null, var darkElevated: Color? = null,
                         var darkElevatedHighContrast: Color? = null) {

    fun value(colorScheme: ColorScheme, contrast: ColorSchemeContrast, isElevated : Boolean): Color?{
        if(colorScheme == ColorScheme.Dark){
           return getColor(dark,darkHighContrast,darkElevated,darkElevatedHighContrast,contrast,isElevated)
        }
        else if(colorScheme == ColorScheme.Light){
            return getColor(light,lightHighContrast,lightElevated,lightElevatedHighContrast, contrast, isElevated)
        }
        else{
            throw Exception("Unable to choose color. Should not be reachable, as `light` color is non-optional.")
        }
    }
    private fun getColor(default: Color?,
                         highContrast: Color?,
                         elevated: Color?,
                         elevatedHighContrast: Color?,
                         contrast: ColorSchemeContrast,
                         isElevated: Boolean
    ): Color?{
        if( isElevated == true){
            return getColorForContrast(elevated,elevatedHighContrast,contrast)
        }
        return getColorForContrast(default,highContrast,contrast)
    }

    private fun getColorForContrast(default:Color?,
                                    highContrast: Color?,
                                    contrast: ColorSchemeContrast
    ): Color? {
        if(contrast == ColorSchemeContrast.HighContrast){
            return highContrast
        }
        return default
    }
}
enum class ColorScheme(val mode: Int){
    Dark(AppCompatDelegate.MODE_NIGHT_NO),
    Light(AppCompatDelegate.MODE_NIGHT_YES)
}
enum class ColorSchemeContrast(){
    Standard,
    HighContrast
}