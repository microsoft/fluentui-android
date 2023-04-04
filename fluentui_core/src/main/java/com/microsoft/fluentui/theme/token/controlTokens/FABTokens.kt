//
//  Copyright (c) Microsoft Corporation. All rights reserved.
//  Licensed under the MIT License.
//

package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
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

data class FABInfo(
    val state: FABState = FABState.Expanded,
    val size: FABSize = FABSize.Large
) : ControlInfo

@Parcelize
open class FABTokens : ControlToken, Parcelable {

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
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth20),
                    aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.StrokeFocus2].value(
                        themeMode = themeMode
                    )
                ),
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth15),
                    aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.StrokeFocus1].value(
                        themeMode = themeMode
                    )
                )
            )
        )
    }

    @Composable
    open fun iconSize(fabInfo: FABInfo): Dp {
        return when (fabInfo.size) {
            FABSize.Small -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.IconSize200)
            FABSize.Large -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.IconSize240)
        }
    }

    @Composable
    open fun typography(fabInfo: FABInfo): TextStyle {
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
                    horizontal = GlobalTokens.size(GlobalTokens.SizeTokens.Size120),
                    vertical = GlobalTokens.size(GlobalTokens.SizeTokens.Size120)
                )
            FABSize.Large ->
                PaddingValues(
                    horizontal = GlobalTokens.size(GlobalTokens.SizeTokens.Size160),
                    vertical = GlobalTokens.size(GlobalTokens.SizeTokens.Size160),
                )
        }
    }

    @Composable
    open fun textPadding(fabInfo: FABInfo): PaddingValues {
        return when (fabInfo.size) {
            FABSize.Small ->
                PaddingValues(
                    top = GlobalTokens.size(GlobalTokens.SizeTokens.Size120),
                    bottom = GlobalTokens.size(GlobalTokens.SizeTokens.Size120),
                    start = GlobalTokens.size(GlobalTokens.SizeTokens.Size120),
                    end = GlobalTokens.size(GlobalTokens.SizeTokens.Size160)
                )
            FABSize.Large ->
                PaddingValues(
                    top = GlobalTokens.size(GlobalTokens.SizeTokens.Size160),
                    bottom = GlobalTokens.size(GlobalTokens.SizeTokens.Size160),
                    start = GlobalTokens.size(GlobalTokens.SizeTokens.Size160),
                    end = GlobalTokens.size(GlobalTokens.SizeTokens.Size200)
                )
        }
    }

    @Composable
    open fun spacing(fabInfo: FABInfo): Dp {
        return when (fabInfo.size) {
            FABSize.Small -> GlobalTokens.size(GlobalTokens.SizeTokens.Size80)
            FABSize.Large -> GlobalTokens.size(GlobalTokens.SizeTokens.Size80)
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
            rest = GlobalTokens.elevation(GlobalTokens.ShadowTokens.Shadow08),
            pressed = GlobalTokens.elevation(GlobalTokens.ShadowTokens.Shadow02),
            selected = GlobalTokens.elevation(GlobalTokens.ShadowTokens.Shadow02),
            focused = GlobalTokens.elevation(GlobalTokens.ShadowTokens.Shadow02),
            disabled = GlobalTokens.elevation(GlobalTokens.ShadowTokens.Shadow02)
        )
    }
}
