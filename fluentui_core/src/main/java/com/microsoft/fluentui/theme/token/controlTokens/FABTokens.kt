//
//  Copyright (c) Microsoft Corporation. All rights reserved.
//  Licensed under the MIT License.
//

package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
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

open class FABInfo(
    val state: FABState = FABState.Expanded,
    val size: FABSize = FABSize.Large
) : ControlInfo

@Parcelize
open class FABTokens : IControlToken, Parcelable {

    @Composable
    open fun iconColor(fabInfo: FABInfo): StateColor {
        return StateColor(
            rest = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                themeMode = themeMode
            ),
            pressed = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                themeMode = themeMode
            ),
            focused = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                themeMode = themeMode
            ),
            disabled = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                themeMode = themeMode
            )
        )
    }

    @Composable
    open fun textColor(fabInfo: FABInfo): StateColor {
        return StateColor(
            rest = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                themeMode = themeMode
            ),
            pressed = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                themeMode = themeMode
            ),
            focused = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                themeMode = themeMode
            ),
            disabled = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                themeMode = themeMode
            )
        )
    }

    @Composable
    open fun backgroundBrush(fabInfo: FABInfo): StateBrush {
        return StateBrush(
            rest = SolidColor(
                aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                    themeMode = themeMode
                )
            ),
            pressed = SolidColor(
                aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1Pressed].value(
                    themeMode = themeMode
                )
            ),
            selected = SolidColor(
                aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1Selected].value(
                    themeMode = themeMode
                )
            ),
            focused = SolidColor(
                aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                    themeMode = themeMode
                )
            ),
            disabled = SolidColor(
                aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background5].value(
                    themeMode = themeMode
                )
            )
        )
    }

    @Composable
    open fun borderStroke(fabInfo: FABInfo): StateBorderStroke {
        return StateBorderStroke(
            focused = listOf(
                BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth20.value,
                    aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.StrokeFocus2].value(
                        themeMode = themeMode
                    )
                ),
                BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth15.value,
                    aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.StrokeFocus1].value(
                        themeMode = themeMode
                    )
                )
            )
        )
    }

    @Composable
    open fun iconSize(fabInfo: FABInfo): Dp {
        return when (fabInfo.size) {
            FABSize.Small -> FluentGlobalTokens.IconSizeTokens.IconSize200.value
            FABSize.Large -> FluentGlobalTokens.IconSizeTokens.IconSize240.value
        }
    }

    @Composable
    open fun typography(fabInfo: FABInfo): TextStyle {
        return when (fabInfo.size) {
            FABSize.Small -> aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body2Strong]
            FABSize.Large -> aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body1Strong]
        }
    }

    @Composable
    open fun iconPadding(fabInfo: FABInfo): PaddingValues {
        return when (fabInfo.size) {
            FABSize.Small ->
                PaddingValues(
                    horizontal = FluentGlobalTokens.SizeTokens.Size120.value,
                    vertical = FluentGlobalTokens.SizeTokens.Size120.value
                )
            FABSize.Large ->
                PaddingValues(
                    horizontal = FluentGlobalTokens.SizeTokens.Size160.value,
                    vertical = FluentGlobalTokens.SizeTokens.Size160.value,
                )
        }
    }

    @Composable
    open fun textPadding(fabInfo: FABInfo): PaddingValues {
        return when (fabInfo.size) {
            FABSize.Small ->
                PaddingValues(
                    top = FluentGlobalTokens.SizeTokens.Size120.value,
                    bottom = FluentGlobalTokens.SizeTokens.Size120.value,
                    start = FluentGlobalTokens.SizeTokens.Size120.value,
                    end = FluentGlobalTokens.SizeTokens.Size160.value
                )
            FABSize.Large ->
                PaddingValues(
                    top = FluentGlobalTokens.SizeTokens.Size160.value,
                    bottom = FluentGlobalTokens.SizeTokens.Size160.value,
                    start = FluentGlobalTokens.SizeTokens.Size160.value,
                    end = FluentGlobalTokens.SizeTokens.Size200.value
                )
        }
    }

    @Composable
    open fun spacing(fabInfo: FABInfo): Dp {
        return when (fabInfo.size) {
            FABSize.Small -> FluentGlobalTokens.SizeTokens.Size80.value
            FABSize.Large -> FluentGlobalTokens.SizeTokens.Size80.value
        }
    }

    @Composable
    open fun fixedHeight(fabInfo: FABInfo): Dp {
        return when (fabInfo.size) {
            FABSize.Small -> 44.dp
            FABSize.Large -> 56.dp
        }
    }

    @Composable
    open fun minWidth(fabInfo: FABInfo): Dp {
        return when (fabInfo.size) {
            FABSize.Small -> 44.dp
            FABSize.Large -> 56.dp
        }
    }

    @Composable
    open fun elevation(fabInfo: FABInfo): StateElevation {
        return StateElevation(
            rest = FluentGlobalTokens.ShadowTokens.Shadow08.value,
            pressed = FluentGlobalTokens.ShadowTokens.Shadow02.value,
            selected = FluentGlobalTokens.ShadowTokens.Shadow02.value,
            focused = FluentGlobalTokens.ShadowTokens.Shadow02.value,
            disabled = FluentGlobalTokens.ShadowTokens.Shadow02.value
        )
    }
}
