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
import com.microsoft.fluentui.theme.FluentTheme.aliasTokens
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
open class FABTokens : ControlToken, Parcelable {

    companion object {
        const val Type: String = "FloatingActionButton"
    }

    @Composable
    open fun iconColor(fabInfo: FABInfo): StateColor {
        return StateColor(
                rest = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                        themeMode = themeMode
                ),
                pressed = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                    themeMode = themeMode
                ),
                focused = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                    themeMode = themeMode
                ),
                disabled = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                        themeMode = themeMode
                )
        )
    }

    @Composable
    open fun textColor(fabInfo: FABInfo): StateColor {
        return StateColor(
                rest = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                        themeMode = themeMode
                ),
                pressed = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                    themeMode = themeMode
                ),
                focused = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                    themeMode = themeMode
                ),
                disabled = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                        themeMode = themeMode
                )
        )
    }

    @Composable
    open fun backgroundColor(fabInfo: FABInfo): StateColor {
        return StateColor(
                rest = aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                        themeMode = themeMode
                ),
                pressed = aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1Pressed].value(
                        themeMode = themeMode
                ),
                selected = aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1Selected].value(
                        themeMode = themeMode
                ),
                focused = aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                    themeMode = themeMode
                ),
                disabled = aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background5].value(
                        themeMode = themeMode
                )
        )
    }

    @Composable
    open fun borderStroke(fabInfo: FABInfo): StateBorderStroke {
        return StateBorderStroke(
                focused = listOf(
                        BorderStroke(
                                GlobalTokens.borderSize(GlobalTokens.BorderSizeTokens.Thick),
                                aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.StrokeFocus2].value(
                                        themeMode = themeMode
                                )
                        ),
                        BorderStroke(
                                GlobalTokens.borderSize(GlobalTokens.BorderSizeTokens.Thin),
                                aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.StrokeFocus1].value(
                                        themeMode = themeMode
                                )
                        )
                )
        )
    }

    @Composable
    open fun iconSize(fabInfo: FABInfo): IconSize {
        return when (fabInfo.size) {
            FABSize.Small -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.Small)
            FABSize.Large -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.Medium)
        }
    }

    @Composable
    open fun fontInfo(fabInfo: FABInfo): FontInfo {
        return when (fabInfo.size) {
            FABSize.Small -> aliasTokens.typography[AliasTokens.TypographyTokens.Body2Strong]
            FABSize.Large -> aliasTokens.typography[AliasTokens.TypographyTokens.Body1Strong]
        }
    }

    @Composable
    open fun iconPadding(fabInfo: FABInfo): PaddingValues {
        return when (fabInfo.size) {
            FABSize.Small ->
                PaddingValues(
                        horizontal = GlobalTokens.spacing(GlobalTokens.SpacingTokens.Small),
                        vertical = GlobalTokens.spacing(GlobalTokens.SpacingTokens.Small)
                )
            FABSize.Large ->
                PaddingValues(
                        horizontal = GlobalTokens.spacing(GlobalTokens.SpacingTokens.Medium),
                        vertical = GlobalTokens.spacing(GlobalTokens.SpacingTokens.Medium)
                )
        }
    }

    @Composable
    open fun textPadding(fabInfo: FABInfo): PaddingValues {
        return when (fabInfo.size) {
            FABSize.Small ->
                PaddingValues(
                        top = GlobalTokens.spacing(GlobalTokens.SpacingTokens.Small),
                        bottom = GlobalTokens.spacing(GlobalTokens.SpacingTokens.Small),
                        start = GlobalTokens.spacing(GlobalTokens.SpacingTokens.Small),
                        end = GlobalTokens.spacing(GlobalTokens.SpacingTokens.Medium)
                )
            FABSize.Large ->
                PaddingValues(
                        top = GlobalTokens.spacing(GlobalTokens.SpacingTokens.Medium),
                        bottom = GlobalTokens.spacing(GlobalTokens.SpacingTokens.Medium),
                        start = GlobalTokens.spacing(GlobalTokens.SpacingTokens.Medium),
                        end = GlobalTokens.spacing(GlobalTokens.SpacingTokens.Large)
                )
        }
    }

    @Composable
    open fun spacing(fabInfo: FABInfo): Dp {
        return when (fabInfo.size) {
            FABSize.Small -> GlobalTokens.spacing(GlobalTokens.SpacingTokens.XSmall)
            FABSize.Large -> GlobalTokens.spacing(GlobalTokens.SpacingTokens.XSmall)
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
                rest = GlobalTokens.elevation(GlobalTokens.ShadowTokens.Shadow08),
                pressed = GlobalTokens.elevation(GlobalTokens.ShadowTokens.Shadow02),
                selected = GlobalTokens.elevation(GlobalTokens.ShadowTokens.Shadow02),
                focused = GlobalTokens.elevation(GlobalTokens.ShadowTokens.Shadow02),
                disabled = GlobalTokens.elevation(GlobalTokens.ShadowTokens.Shadow02)
        )
    }
}
