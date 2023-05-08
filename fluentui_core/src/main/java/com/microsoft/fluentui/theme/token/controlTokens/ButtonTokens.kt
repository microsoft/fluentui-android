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

enum class ButtonStyle {
    Button,
    OutlinedButton,
    TextButton
}

enum class ButtonSize {
    Small,
    Medium,
    Large
}

data class ButtonInfo(
    val style: ButtonStyle = ButtonStyle.Button,
    val size: ButtonSize = ButtonSize.Medium
) : ControlInfo

@Parcelize
open class ButtonTokens : IControlToken, Parcelable {

    @Composable
    open fun iconColor(buttonInfo: ButtonInfo): StateColor {
        return when (buttonInfo.style) {
            ButtonStyle.Button ->
                StateColor(
                    rest = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                        themeMode = themeMode
                    ),
                    pressed = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                        themeMode = themeMode
                    ),
                    selected = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                        themeMode = themeMode
                    ),
                    focused = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                        themeMode = themeMode
                    ),
                    disabled = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                        themeMode = themeMode
                    )
                )

            ButtonStyle.OutlinedButton, ButtonStyle.TextButton ->
                StateColor(
                    rest = aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
                        themeMode = themeMode
                    ),
                    pressed = aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1Pressed].value(
                        themeMode = themeMode
                    ),
                    selected = aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1Selected].value(
                        themeMode = themeMode
                    ),
                    focused = aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
                        themeMode = themeMode
                    ),
                    disabled = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                        themeMode = themeMode
                    )
                )
        }
    }

    @Composable
    open fun textColor(buttonInfo: ButtonInfo): StateColor {
        return when (buttonInfo.style) {
            ButtonStyle.Button -> StateColor(
                rest = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                    themeMode = themeMode
                ),
                pressed = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                    themeMode = themeMode
                ),
                selected = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                    themeMode = themeMode
                ),
                focused = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                    themeMode = themeMode
                ),
                disabled = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                    themeMode = themeMode
                )
            )
            ButtonStyle.OutlinedButton, ButtonStyle.TextButton ->
                StateColor(
                    rest = aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
                        themeMode = themeMode
                    ),
                    pressed = aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1Pressed].value(
                        themeMode = themeMode
                    ),
                    selected = aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1Selected].value(
                        themeMode = themeMode
                    ),
                    focused = aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
                        themeMode = themeMode
                    ),
                    disabled = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                        themeMode = themeMode
                    )
                )
        }
    }

    @Composable
    open fun backgroundColor(buttonInfo: ButtonInfo): StateColor {
        return when (buttonInfo.style) {
            ButtonStyle.Button ->
                StateColor(
                    rest = aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                        themeMode = themeMode
                    ),
                    pressed = aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1Pressed].value(
                        themeMode = themeMode
                    ),
                    selected = aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1Selected].value(
                        themeMode = themeMode
                    ),
                    focused = aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                        themeMode = themeMode
                    ),
                    disabled = aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background5].value(
                        themeMode = themeMode
                    )
                )
            ButtonStyle.OutlinedButton -> StateColor()
            ButtonStyle.TextButton -> StateColor()
        }
    }

    @Composable
    open fun borderStroke(buttonInfo: ButtonInfo): StateBorderStroke {
        return when (buttonInfo.style) {
            ButtonStyle.Button, ButtonStyle.TextButton -> StateBorderStroke(
                focused = listOf(
                    BorderStroke(
                        FluentGlobalTokens.strokeWidth(FluentGlobalTokens.StrokeWidthTokens.StrokeWidth20),
                        aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.StrokeFocus2].value(
                            themeMode = themeMode
                        )
                    ),
                    BorderStroke(
                        FluentGlobalTokens.strokeWidth(FluentGlobalTokens.StrokeWidthTokens.StrokeWidth15),
                        aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.StrokeFocus1].value(
                            themeMode = themeMode
                        )
                    )
                )
            )

            ButtonStyle.OutlinedButton -> StateBorderStroke(
                pressed = listOf(
                    BorderStroke(
                        FluentGlobalTokens.strokeWidth(FluentGlobalTokens.StrokeWidthTokens.StrokeWidth15),
                        aliasTokens.brandStroke[FluentAliasTokens.BrandStrokeColorTokens.BrandStroke1Pressed].value(
                            themeMode = themeMode
                        )
                    )
                ),
                rest = listOf(
                    BorderStroke(
                        FluentGlobalTokens.strokeWidth(FluentGlobalTokens.StrokeWidthTokens.StrokeWidth15),
                        aliasTokens.brandStroke[FluentAliasTokens.BrandStrokeColorTokens.BrandStroke1].value(
                            themeMode = themeMode
                        )
                    )
                ),
                disabled = listOf(
                    BorderStroke(
                        FluentGlobalTokens.strokeWidth(FluentGlobalTokens.StrokeWidthTokens.StrokeWidth15),
                        aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.StrokeDisabled].value(
                            themeMode = themeMode
                        )
                    )
                ),
                focused = listOf(
                    BorderStroke(
                        FluentGlobalTokens.strokeWidth(FluentGlobalTokens.StrokeWidthTokens.StrokeWidth20),
                        aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.StrokeFocus2].value(
                            themeMode = themeMode
                        )
                    ),
                    BorderStroke(
                        FluentGlobalTokens.strokeWidth(FluentGlobalTokens.StrokeWidthTokens.StrokeWidth15),
                        aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.StrokeFocus1].value(
                            themeMode = themeMode
                        )
                    )
                )
            )
        }
    }

    @Composable
    open fun cornerRadius(buttonInfo: ButtonInfo): Dp {
        return when (buttonInfo.size) {
            ButtonSize.Small -> FluentGlobalTokens.cornerRadius(FluentGlobalTokens.CornerRadiusTokens.CornerRadius80)
            ButtonSize.Medium -> FluentGlobalTokens.cornerRadius(FluentGlobalTokens.CornerRadiusTokens.CornerRadius40)
            ButtonSize.Large -> FluentGlobalTokens.cornerRadius(FluentGlobalTokens.CornerRadiusTokens.CornerRadius40)
        }
    }

    @Composable
    open fun iconSize(buttonInfo: ButtonInfo): Dp {
        return when (buttonInfo.style) {
            ButtonStyle.Button, ButtonStyle.TextButton, ButtonStyle.OutlinedButton ->
                when (buttonInfo.size) {
                    ButtonSize.Small -> FluentGlobalTokens.iconSize(FluentGlobalTokens.IconSizeTokens.IconSize160)
                    ButtonSize.Medium -> FluentGlobalTokens.iconSize(FluentGlobalTokens.IconSizeTokens.IconSize200)
                    ButtonSize.Large -> FluentGlobalTokens.iconSize(FluentGlobalTokens.IconSizeTokens.IconSize200)
                }
        }
    }

    @Composable
    open fun typography(buttonInfo: ButtonInfo): TextStyle {
        return when (buttonInfo.size) {
            ButtonSize.Small -> aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body2Strong]
            ButtonSize.Medium -> aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body2Strong]
            ButtonSize.Large -> aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body1Strong]
        }
    }

    @Composable
    open fun padding(buttonInfo: ButtonInfo): PaddingValues {
        return when (buttonInfo.size) {
            ButtonSize.Small ->
                PaddingValues(
                    horizontal = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size80),
                    vertical = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size40)
                )
            ButtonSize.Medium -> PaddingValues(
                horizontal = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size120),
                vertical = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size80)
            )
            ButtonSize.Large -> PaddingValues(
                horizontal = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size200),
                vertical = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size120)
            )
        }
    }

    @Composable
    open fun spacing(buttonInfo: ButtonInfo): Dp {
        return when (buttonInfo.size) {
            ButtonSize.Small -> FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size40)
            ButtonSize.Medium -> FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size80)
            ButtonSize.Large -> FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size80)
        }
    }

    @Composable
    open fun fixedHeight(buttonInfo: ButtonInfo): Dp {
        return when (buttonInfo.size) {
            ButtonSize.Small -> 28.dp
            ButtonSize.Medium -> 36.dp
            ButtonSize.Large -> 48.dp
        }
    }
}
