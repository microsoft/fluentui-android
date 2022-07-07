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
import com.microsoft.fluentui.theme.FluentTheme.globalTokens
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
open class ButtonTokens : ControlToken, Parcelable {

    companion object {
        const val Type: String = "Button"
    }

    @Composable
    open fun iconColor(buttonInfo: ButtonInfo): StateColor {
        return when (buttonInfo.style) {
            ButtonStyle.Button ->
                StateColor(
                        rest = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                                themeMode = themeMode
                        ),
                        pressed = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                                themeMode = themeMode
                        ),
                        selected = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                                themeMode = themeMode
                        ),
                        focused = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                                themeMode = themeMode
                        ),
                        disabled = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                                themeMode = themeMode
                        )
                )

            ButtonStyle.OutlinedButton, ButtonStyle.TextButton ->
                StateColor(
                        rest = aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
                                themeMode = themeMode
                        ),
                        pressed = aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1Pressed].value(
                                themeMode = themeMode
                        ),
                        selected = aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1Selected].value(
                                themeMode = themeMode
                        ),
                        focused = aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
                                themeMode = themeMode
                        ),
                        disabled = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                                themeMode = themeMode
                        )
                )
        }
    }

    @Composable
    open fun textColor(buttonInfo: ButtonInfo): StateColor {
        return when (buttonInfo.style) {
            ButtonStyle.Button -> StateColor(
                    rest = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                            themeMode = themeMode
                    ),
                    pressed = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                            themeMode = themeMode
                    ),
                    selected = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                            themeMode = themeMode
                    ),
                    focused = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                            themeMode = themeMode
                    ),
                    disabled = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                            themeMode = themeMode
                    )
            )
            ButtonStyle.OutlinedButton, ButtonStyle.TextButton ->
                StateColor(
                        rest = aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
                                themeMode = themeMode
                        ),
                        pressed = aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1Pressed].value(
                                themeMode = themeMode
                        ),
                        selected = aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1Selected].value(
                                themeMode = themeMode
                        ),
                        focused = aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
                                themeMode = themeMode
                        ),
                        disabled = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
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
                                    globalTokens.borderSize[GlobalTokens.BorderSizeTokens.Thick],
                                    aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.StrokeFocus2].value(
                                            themeMode = themeMode
                                    )
                            ),
                            BorderStroke(
                                    globalTokens.borderSize[GlobalTokens.BorderSizeTokens.Thin],
                                    aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.StrokeFocus1].value(
                                            themeMode = themeMode
                                    )
                            )
                    )
            )

            ButtonStyle.OutlinedButton -> StateBorderStroke(
                    pressed = listOf(
                            BorderStroke(
                                    globalTokens.borderSize[GlobalTokens.BorderSizeTokens.Thin],
                                    aliasTokens.brandStroke[AliasTokens.BrandStrokeColorTokens.BrandStroke1Pressed].value(
                                            themeMode = themeMode
                                    )
                            )
                    ),
                    rest = listOf(
                            BorderStroke(
                                    globalTokens.borderSize[GlobalTokens.BorderSizeTokens.Thin],
                                    aliasTokens.brandStroke[AliasTokens.BrandStrokeColorTokens.BrandStroke1].value(
                                            themeMode = themeMode
                                    )
                            )
                    ),
                    disabled = listOf(
                            BorderStroke(
                                    globalTokens.borderSize[GlobalTokens.BorderSizeTokens.Thin],
                                    aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.StrokeDisabled].value(
                                            themeMode = themeMode
                                    )
                            )
                    ),
                    focused = listOf(
                            BorderStroke(
                                    globalTokens.borderSize[GlobalTokens.BorderSizeTokens.Thick],
                                    aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.StrokeFocus2].value(
                                            themeMode = themeMode
                                    )
                            ),
                            BorderStroke(
                                    globalTokens.borderSize[GlobalTokens.BorderSizeTokens.Thin],
                                    aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.StrokeFocus1].value(
                                            themeMode = themeMode
                                    )
                            )
                    )
            )
        }
    }

    @Composable
    open fun borderRadius(buttonInfo: ButtonInfo): Dp {
        return when (buttonInfo.size) {
            ButtonSize.Small -> globalTokens.borderRadius[GlobalTokens.BorderRadiusTokens.Large]
            ButtonSize.Medium -> globalTokens.borderRadius[GlobalTokens.BorderRadiusTokens.Medium]
            ButtonSize.Large -> globalTokens.borderRadius[GlobalTokens.BorderRadiusTokens.Medium]
        }
    }

    @Composable
    open fun iconSize(buttonInfo: ButtonInfo): IconSize {
        return when (buttonInfo.style) {
            ButtonStyle.Button, ButtonStyle.TextButton, ButtonStyle.OutlinedButton ->
                when (buttonInfo.size) {
                    ButtonSize.Small -> globalTokens.iconSize[GlobalTokens.IconSizeTokens.XSmallSelected]
                    ButtonSize.Medium -> globalTokens.iconSize[GlobalTokens.IconSizeTokens.SmallSelected]
                    ButtonSize.Large -> globalTokens.iconSize[GlobalTokens.IconSizeTokens.SmallSelected]
                }
        }
    }

    @Composable
    open fun fontInfo(buttonInfo: ButtonInfo): FontInfo {
        return when (buttonInfo.size) {
            ButtonSize.Small -> aliasTokens.typography[AliasTokens.TypographyTokens.Body2Strong]
            ButtonSize.Medium -> aliasTokens.typography[AliasTokens.TypographyTokens.Body2Strong]
            ButtonSize.Large -> aliasTokens.typography[AliasTokens.TypographyTokens.Body1Strong]
        }
    }

    @Composable
    open fun padding(buttonInfo: ButtonInfo): PaddingValues {
        return when (buttonInfo.size) {
            ButtonSize.Small ->
                PaddingValues(
                        horizontal = globalTokens.spacing[GlobalTokens.SpacingTokens.XSmall],
                        vertical = globalTokens.spacing[GlobalTokens.SpacingTokens.XXSmall]
                )
            ButtonSize.Medium -> PaddingValues(
                    horizontal = globalTokens.spacing[GlobalTokens.SpacingTokens.Small],
                    vertical = globalTokens.spacing[GlobalTokens.SpacingTokens.XSmall]
            )
            ButtonSize.Large -> PaddingValues(
                    horizontal = globalTokens.spacing[GlobalTokens.SpacingTokens.Large],
                    vertical = globalTokens.spacing[GlobalTokens.SpacingTokens.Small]
            )
        }
    }

    @Composable
    open fun spacing(buttonInfo: ButtonInfo): Dp {
        return when (buttonInfo.size) {
            ButtonSize.Small -> globalTokens.spacing[GlobalTokens.SpacingTokens.XXSmall]
            ButtonSize.Medium -> globalTokens.spacing[GlobalTokens.SpacingTokens.XSmall]
            ButtonSize.Large -> globalTokens.spacing[GlobalTokens.SpacingTokens.XSmall]
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
