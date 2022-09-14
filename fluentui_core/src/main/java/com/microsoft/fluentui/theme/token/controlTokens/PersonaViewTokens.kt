package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralBackgroundColorTokens.Background1
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralBackgroundColorTokens.Background1Pressed
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralForegroundColorTokens.Foreground1
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralForegroundColorTokens.Foreground2
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralForegroundColorTokens.Foreground3
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralStrokeColorTokens.Stroke2
import com.microsoft.fluentui.theme.token.AliasTokens.TypographyTokens.Body1
import com.microsoft.fluentui.theme.token.AliasTokens.TypographyTokens.Body2
import com.microsoft.fluentui.theme.token.AliasTokens.TypographyTokens.Caption1
import com.microsoft.fluentui.theme.token.ControlToken
import com.microsoft.fluentui.theme.token.FontInfo
import com.microsoft.fluentui.theme.token.GlobalTokens
import com.microsoft.fluentui.theme.token.GlobalTokens.BorderSizeTokens.Thin
import com.microsoft.fluentui.theme.token.GlobalTokens.SpacingTokens
import com.microsoft.fluentui.theme.token.StateColor
import com.microsoft.fluentui.theme.token.controlTokens.PersonaViewType.OneLine
import com.microsoft.fluentui.theme.token.controlTokens.PersonaViewType.ThreeLine
import com.microsoft.fluentui.theme.token.controlTokens.PersonaViewType.TwoLine
import com.microsoft.fluentui.theme.token.controlTokens.TextType.SecondaryText
import com.microsoft.fluentui.theme.token.controlTokens.TextType.TertiaryText
import com.microsoft.fluentui.theme.token.controlTokens.TextType.Text
import kotlinx.android.parcel.Parcelize

enum class PersonaViewType {
    OneLine,
    TwoLine,
    ThreeLine
}

enum class TextType {
    Text,
    SecondaryText,
    TertiaryText
}

@Parcelize
open class PersonaViewTokens : ControlToken, Parcelable {
    @Composable
    open fun backgroundColor(): StateColor {
        return StateColor(
            rest = FluentTheme.aliasTokens.neutralBackgroundColor[Background1].value(
                themeMode = FluentTheme.themeMode
            ),
            pressed = FluentTheme.aliasTokens.neutralBackgroundColor[Background1Pressed].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun borderColor(): StateColor {
        return StateColor(
            rest = FluentTheme.aliasTokens.neutralStrokeColor[Stroke2].value(
                themeMode = FluentTheme.themeMode
            ),
            pressed = FluentTheme.aliasTokens.neutralStrokeColor[Stroke2].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun cellHeight(personaViewType: PersonaViewType): Dp {
        return when (personaViewType) {
            OneLine -> 48.dp
            TwoLine -> 68.dp
            ThreeLine -> 88.dp
        }
    }

    @Composable
    open fun textSize(textType: TextType): FontInfo {
        return when (textType) {
            Text -> FluentTheme.aliasTokens.typography[Body1]
            SecondaryText -> FluentTheme.aliasTokens.typography[Body2]
            TertiaryText -> FluentTheme.aliasTokens.typography[Caption1]
        }
    }

    @Composable
    open fun textColor(textType: TextType): StateColor {
        return when (textType) {
            Text ->
                StateColor(
                    rest = FluentTheme.aliasTokens.neutralForegroundColor[Foreground1].value(
                        themeMode = FluentTheme.themeMode
                    )
                )
            SecondaryText ->
                StateColor(
                    rest = FluentTheme.aliasTokens.neutralForegroundColor[Foreground2].value(
                        themeMode = FluentTheme.themeMode
                    )
                )
            TertiaryText ->
                StateColor(
                    rest = FluentTheme.aliasTokens.neutralForegroundColor[Foreground2].value(
                        themeMode = FluentTheme.themeMode
                    )
                )
        }
    }

    @Composable
    open fun padding(size: SpacingTokens): Dp {
        return GlobalTokens.spacing(size)
    }

    @Composable
    open fun borderSize(): Dp {
        return GlobalTokens.borderSize(Thin)
    }

    @Composable
    open fun borderInset(inset:BorderInset): Dp{
        return when (inset) {
            BorderInset.None -> 0.dp
            BorderInset.Medium -> 16.dp
            BorderInset.Large -> 56.dp
            BorderInset.XXLarge -> 68.dp
            BorderInset.XXXXLarge -> 72.dp
            BorderInset.XXXXXXLarge -> 108.dp
        }
    }
}
