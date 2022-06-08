//
//  Copyright (c) Microsoft Corporation. All rights reserved.
//  Licensed under the MIT License.
//

package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme.aliasToken
import com.microsoft.fluentui.theme.FluentTheme.globalTokens
import com.microsoft.fluentui.theme.FluentTheme.themeMode
import com.microsoft.fluentui.theme.token.*
import kotlinx.parcelize.Parcelize

enum class FABState {
    Expanded,
    Collapsed
}

enum class FABSize {
    Small,
    Large
}

data class FABInfo(
    val state: FABState = FABState.Expanded,
    val size: FABSize = FABSize.Large
) : ControlInfo

@Parcelize
open class FABTokens : ControlTokens, Parcelable {

    companion object {
        const val Type: String = "FloatingActionButton"
    }

    @Composable
    open fun iconColor(fabInfo: FABInfo): StateColor {
        return StateColor(
            rest = aliasToken.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                themeMode = themeMode
            ),
            disabled = aliasToken.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundDisable2].value(
                themeMode = themeMode
            )
        )
    }

    @Composable
    open fun textColor(fabInfo: FABInfo): StateColor {
        return StateColor(
            rest = aliasToken.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                themeMode = themeMode
            ),
            disabled = aliasToken.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundDisable2].value(
                themeMode = themeMode
            )
        )
    }

    @Composable
    open fun backgroundColor(fabInfo: FABInfo): StateColor {
        return StateColor(
            rest = aliasToken.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                themeMode = themeMode
            ),
            pressed = aliasToken.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1Pressed].value(
                themeMode = themeMode
            ),
            selected = aliasToken.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1Selected].value(
                themeMode = themeMode
            ),
            disabled = aliasToken.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackgroundDisabled].value(
                themeMode = themeMode
            )
        )
    }

    @Composable
    open fun borderStroke(fabInfo: FABInfo): StateBorderStroke {
        return StateBorderStroke(
            focused = listOf(
                BorderStroke(
                    globalTokens.borderSize[GlobalTokens.BorderSizeTokens.Thick],
                    aliasToken.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.StrokeFocus2].value(
                        themeMode = themeMode
                    )
                ),
                BorderStroke(
                    globalTokens.borderSize[GlobalTokens.BorderSizeTokens.Thin],
                    aliasToken.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.StrokeFocus1].value(
                        themeMode = themeMode
                    )
                )
            )
        )
    }

    @Composable
    open fun iconSize(fabInfo: FABInfo): IconSize {
        return when (fabInfo.size) {
            FABSize.Small -> globalTokens.iconSize[GlobalTokens.IconSizeTokens.Small]
            FABSize.Large -> globalTokens.iconSize[GlobalTokens.IconSizeTokens.Medium]
        }
    }

    @Composable
    open fun fontInfo(fabInfo: FABInfo): FontInfo {
        return when (fabInfo.size) {
            FABSize.Small -> aliasToken.typography[AliasTokens.TypographyTokens.Body2Strong]
            FABSize.Large -> aliasToken.typography[AliasTokens.TypographyTokens.Body1Strong]
        }
    }

    @Composable
    open fun iconPadding(fabInfo: FABInfo): PaddingValues {
        return when (fabInfo.size) {
            FABSize.Small ->
                PaddingValues(
                    horizontal = globalTokens.spacing[GlobalTokens.SpacingTokens.Small],
                    vertical = globalTokens.spacing[GlobalTokens.SpacingTokens.Small]
                )
            FABSize.Large ->
                PaddingValues(
                    horizontal = globalTokens.spacing[GlobalTokens.SpacingTokens.Medium],
                    vertical = globalTokens.spacing[GlobalTokens.SpacingTokens.Medium]
                )
        }
    }

    @Composable
    open fun textPadding(fabInfo: FABInfo): PaddingValues {
        return when (fabInfo.size) {
            FABSize.Small ->
                PaddingValues(
                    top = globalTokens.spacing[GlobalTokens.SpacingTokens.Small],
                    bottom = globalTokens.spacing[GlobalTokens.SpacingTokens.Small],
                    start = globalTokens.spacing[GlobalTokens.SpacingTokens.Small],
                    end = globalTokens.spacing[GlobalTokens.SpacingTokens.Medium]
                )
            FABSize.Large ->
                PaddingValues(
                    top = globalTokens.spacing[GlobalTokens.SpacingTokens.Medium],
                    bottom = globalTokens.spacing[GlobalTokens.SpacingTokens.Medium],
                    start = globalTokens.spacing[GlobalTokens.SpacingTokens.Medium],
                    end = globalTokens.spacing[GlobalTokens.SpacingTokens.Large]
                )
        }
    }

    @Composable
    open fun spacing(fabInfo: FABInfo): Dp {
        return when (fabInfo.size) {
            FABSize.Small -> globalTokens.spacing[GlobalTokens.SpacingTokens.XSmall]
            FABSize.Large -> globalTokens.spacing[GlobalTokens.SpacingTokens.XSmall]
        }
    }

    @Composable
    open fun fixedHeight(fabInfo: FABInfo): Dp {
        return when (fabInfo.size) {
            FABSize.Small -> 48.dp
            FABSize.Large -> 56.dp
        }
    }

    @Composable
    open fun minWidth(fabInfo: FABInfo): Dp {
        return when (fabInfo.size) {
            FABSize.Small -> 48.dp
            FABSize.Large -> 56.dp
        }
    }

    @Composable
    open fun elevation(fabInfo: FABInfo): StateElevation {
        return StateElevation(
            rest = globalTokens.elevation[GlobalTokens.ShadowTokens.Shadow08],
            pressed = globalTokens.elevation[GlobalTokens.ShadowTokens.Shadow02],
            selected = globalTokens.elevation[GlobalTokens.ShadowTokens.Shadow02],
            focused = globalTokens.elevation[GlobalTokens.ShadowTokens.Shadow02],
            disabled = globalTokens.elevation[GlobalTokens.ShadowTokens.Shadow02]
        )
    }
}
