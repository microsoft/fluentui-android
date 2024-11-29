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

open class ButtonInfo(
    val style: ButtonStyle = ButtonStyle.Button,
    val size: ButtonSize = ButtonSize.Medium,
    val elevation: Dp = FluentGlobalTokens.ShadowTokens.Shadow00.value
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
    open fun trailingIconColor(buttonInfo: ButtonInfo): StateColor {
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
    open fun backgroundBrush(buttonInfo: ButtonInfo): StateBrush {
        return when (buttonInfo.style) {
            ButtonStyle.Button ->
                StateBrush(
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
            ButtonStyle.OutlinedButton -> StateBrush()
            ButtonStyle.TextButton -> StateBrush()
        }
    }

    @Composable
    open fun borderStroke(buttonInfo: ButtonInfo): StateBorderStroke {
        return when (buttonInfo.style) {
            ButtonStyle.Button, ButtonStyle.TextButton -> StateBorderStroke(
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

            ButtonStyle.OutlinedButton -> StateBorderStroke(
                pressed = listOf(
                    BorderStroke(
                        FluentGlobalTokens.StrokeWidthTokens.StrokeWidth15.value,
                        aliasTokens.brandStroke[FluentAliasTokens.BrandStrokeColorTokens.BrandStroke1Pressed].value(
                            themeMode = themeMode
                        )
                    )
                ),
                rest = listOf(
                    BorderStroke(
                        FluentGlobalTokens.StrokeWidthTokens.StrokeWidth15.value,
                        aliasTokens.brandStroke[FluentAliasTokens.BrandStrokeColorTokens.BrandStroke1].value(
                            themeMode = themeMode
                        )
                    )
                ),
                disabled = listOf(
                    BorderStroke(
                        FluentGlobalTokens.StrokeWidthTokens.StrokeWidth15.value,
                        aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.StrokeDisabled].value(
                            themeMode = themeMode
                        )
                    )
                ),
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
    }

    @Composable
    open fun cornerRadius(buttonInfo: ButtonInfo): Dp {
        return when (buttonInfo.size) {
            ButtonSize.Small -> FluentGlobalTokens.CornerRadiusTokens.CornerRadius80.value
            ButtonSize.Medium -> FluentGlobalTokens.CornerRadiusTokens.CornerRadius40.value
            ButtonSize.Large -> FluentGlobalTokens.CornerRadiusTokens.CornerRadius40.value
        }
    }

    @Composable
    open fun elevation(buttonInfo: ButtonInfo): Dp {
        return buttonInfo.elevation
    }

    @Composable
    open fun iconSize(buttonInfo: ButtonInfo): Dp {
        return when (buttonInfo.style) {
            ButtonStyle.Button, ButtonStyle.TextButton, ButtonStyle.OutlinedButton ->
                when (buttonInfo.size) {
                    ButtonSize.Small -> FluentGlobalTokens.IconSizeTokens.IconSize160.value
                    ButtonSize.Medium -> FluentGlobalTokens.IconSizeTokens.IconSize200.value
                    ButtonSize.Large -> FluentGlobalTokens.IconSizeTokens.IconSize200.value
                }
        }
    }

    @Composable
    open fun trailingIconSize(buttonInfo: ButtonInfo): Dp {
        return when (buttonInfo.style) {
            ButtonStyle.Button, ButtonStyle.TextButton, ButtonStyle.OutlinedButton ->
                when (buttonInfo.size) {
                    ButtonSize.Small -> FluentGlobalTokens.IconSizeTokens.IconSize160.value
                    ButtonSize.Medium -> FluentGlobalTokens.IconSizeTokens.IconSize200.value
                    ButtonSize.Large -> FluentGlobalTokens.IconSizeTokens.IconSize200.value
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
                    horizontal = FluentGlobalTokens.SizeTokens.Size80.value,
                    vertical = FluentGlobalTokens.SizeTokens.Size40.value
                )
            ButtonSize.Medium -> PaddingValues(
                horizontal = FluentGlobalTokens.SizeTokens.Size120.value,
                vertical = FluentGlobalTokens.SizeTokens.Size80.value
            )
            ButtonSize.Large -> PaddingValues(
                horizontal = FluentGlobalTokens.SizeTokens.Size200.value,
                vertical = FluentGlobalTokens.SizeTokens.Size120.value
            )
        }
    }

    @Composable
    open fun spacing(buttonInfo: ButtonInfo): Dp {
        return when (buttonInfo.size) {
            ButtonSize.Small -> FluentGlobalTokens.SizeTokens.Size40.value
            ButtonSize.Medium -> FluentGlobalTokens.SizeTokens.Size80.value
            ButtonSize.Large -> FluentGlobalTokens.SizeTokens.Size80.value
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
