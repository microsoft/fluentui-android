//
//  Copyright (c) Microsoft Corporation. All rights reserved.
//  Licensed under the MIT License.
//

package com.microsoft.fluentui.theme.token

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.FluentTheme.themeMode

data class StateColor(val rest:Color = Color.Unspecified,
                      val pressed:Color = Color.Unspecified,
                      val selected: Color = Color.Unspecified,
                      val focused:Color = Color.Unspecified,
                      val disabled:Color = Color.Unspecified,
)

data class FluentColor (val light:Color, val lightHighContrast :Color? = null,
                        val lightElevated: Color? = null, val lightElevatedHighContrast: Color? = null,
                        val dark: Color = light,
                        var darkHighContrast: Color? = null, var darkElevated: Color? = null,
                        var darkElevatedHighContrast: Color? = null, var colorful:Color = light) {

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

    @Composable
    fun value(themeMode: ThemeMode = com.microsoft.fluentui.theme.FluentTheme.themeMode) :Color{
        return when(themeMode){
            ThemeMode.Light -> light
            ThemeMode.Dark -> dark
            ThemeMode.Colorful -> colorful
            ThemeMode.Auto -> if(isSystemInDarkTheme()) dark else light
            ThemeMode.AutoColorful -> if(isSystemInDarkTheme()) dark else colorful
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