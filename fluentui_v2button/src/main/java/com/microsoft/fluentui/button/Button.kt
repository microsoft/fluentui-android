package com.microsoft.fluentui.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalContentColor
import androidx.compose.material.ProvideTextStyle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.microsoft.fluentui.theme.token.ButtonStyle
import com.microsoft.fluentui.theme.token.ButtonToken
import com.microsoft.fluentui.theme.token.ButtonType
import com.microsoft.fluentui.theme.token.ControlType


val LocalButtonToken = compositionLocalOf { ButtonToken() }


@Composable
fun CreateButton(
    modifier: Modifier = Modifier,
    type:ButtonType = ButtonType.Button,
    style: ButtonStyle = ButtonStyle.Primary,
    size:com.microsoft.fluentui.theme.token.ButtonSize = com.microsoft.fluentui.theme.token.ButtonSize.Medium,
    enabled: Boolean = true,
    buttonToken:ButtonToken = FluentTheme.tokens[ControlType.Button] as ButtonToken,
    onClick:() -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    icon: @Composable (RowScope.() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit
) {
    buttonToken.style = style
    buttonToken.size = size

    CompositionLocalProvider(
        LocalButtonToken provides buttonToken
    ){
         when(type){
            ButtonType.Button -> Button(onClick = onClick,
                enabled = enabled,
                modifier = modifier,
                size = getSize(size = size),
                border = getBorder(),
                interactionSource = interactionSource,
                icon = icon,
                content = content)
            ButtonType.OutlinedButton -> OutlinedButton(onClick = onClick,
                enabled = enabled,
                modifier = modifier,
                size = getSize(size = size),
                border = getBorder(),
                interactionSource = interactionSource,
                icon = icon,
                content = content)
            ButtonType.TextButton -> TextButton(onClick = onClick,
                enabled = enabled,
                modifier = modifier,
                size = getSize(size = size),
                interactionSource = interactionSource,
                content = content)
        }
    }
}

@Composable
fun getSize(size : com.microsoft.fluentui.theme.token.ButtonSize) : ButtonSize{
    return when(size){
        com.microsoft.fluentui.theme.token.ButtonSize.Large -> ButtonDefaults.primaryButtonSize()
        com.microsoft.fluentui.theme.token.ButtonSize.Medium -> ButtonDefaults.secondaryButtonSize()
        com.microsoft.fluentui.theme.token.ButtonSize.Small -> ButtonDefaults.textButtonSize()

    }
}

@Composable
fun getBorder() : BorderStroke{
    return BorderStroke(getButtonToken().borderStrokeThickness(), getButtonToken().borderStrokeColor())
}

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    icon: @Composable (RowScope.() -> Unit)? = null,
    size: ButtonSize = ButtonDefaults.primaryButtonSize(),
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    content: @Composable RowScope.() -> Unit
) {
    val clickAndSemanticsModifier = Modifier.clickable(
        interactionSource = interactionSource,
        indication = LocalIndication.current,
        enabled = enabled,
        onClickLabel = null,
        role = Role.Button,
        onClick = onClick
    )
    val backgroundColor = colors.backgroundColor(enabled).value
    val contentColor = colors.contentColor(enabled).value
    val contentPadding = size.contentPadding().value
    val iconSpacing = size.iconSpacing().value
    val textStyle = size.textStyle().value
    val shape = RoundedCornerShape(3.dp) //RoundedCornerShape(50)//RoundedCornerShape(3.dp)

    CompositionLocalProvider(
        LocalContentColor provides contentColor,
    ) {
        Box(
            modifier
                .then(if (border != null) Modifier.border(border, shape) else Modifier)
                .background(
                    color = backgroundColor,
                    shape = shape
                )
                .clip(shape)
                .then(clickAndSemanticsModifier),
            propagateMinConstraints = true
        ) {
            ProvideTextStyle(textStyle) {
                Row(
                    Modifier
                        .defaultMinSize(
                            minWidth = ButtonDefaults.MinWidth,
                            minHeight = ButtonDefaults.MinHeight
                        )
                        .padding(contentPadding),
                    horizontalArrangement = Arrangement.spacedBy(iconSpacing, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (icon != null) {
                        icon()
                    }
                    content()
                }
            }
        }
    }
}

@Composable
fun OutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    icon: @Composable (RowScope.() -> Unit)? = null,
    size: ButtonSize = ButtonDefaults.primaryButtonSize(),
    border: BorderStroke? = ButtonDefaults.outlinedBorder,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
    content: @Composable RowScope.() -> Unit
) = Button(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    interactionSource = interactionSource,
    icon = icon,
    size = size,
    border = border,
    colors = colors,
    content = content
)

@Composable
fun TextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    size: ButtonSize = ButtonDefaults.textButtonSize(),
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    content: @Composable RowScope.() -> Unit
) = Button(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    interactionSource = interactionSource,
    size = size,
    colors = colors,
    content = content
)

interface ButtonColors {
    @Composable
    fun backgroundColor(enabled: Boolean): State<Color>

    @Composable
    fun contentColor(enabled: Boolean): State<Color>
}

interface ButtonSize {
    @Composable
    fun contentPadding(): State<PaddingValues>

    @Composable
    fun iconSpacing(): State<Dp>

    @Composable
    fun textStyle(): State<TextStyle>
}
@Composable
fun getButtonToken() : ButtonToken{
    return LocalButtonToken.current//FluentTheme.controlTokens["Button"] as ButtonToken
}
object ButtonDefaults {
    val MinWidth = 48.dp
    val MinHeight = 36.dp

    val IconSize = 18.dp
    val IconSpacing = 8.dp

    @Composable
    fun buttonColors(
        backgroundColor: Color = getButtonToken().background(),
        contentColor: Color = getButtonToken().contentColor(),
        disabledBackgroundColor: Color = getButtonToken().disabledBackgroundColor(),
        disabledContentColor: Color = getButtonToken().disabledContentColor(),//MaterialTheme.colors.onSurface
        //.copy(alpha = ContentAlpha.disabled)
    ): ButtonColors = DefaultButtonColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledBackgroundColor = disabledBackgroundColor,
        disabledContentColor = disabledContentColor
    )

    @Composable
    fun outlinedButtonColors(
        backgroundColor: Color = Color.Transparent,
        contentColor: Color = getButtonToken().contentColor(),
        disabledContentColor: Color = getButtonToken().disabledBackgroundColor()
    ): ButtonColors = DefaultButtonColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledBackgroundColor = backgroundColor,
        disabledContentColor = disabledContentColor
    )

    @Composable
    fun textButtonColors(
        backgroundColor: Color = Color.Transparent,
        contentColor: Color = getButtonToken().contentColor(),
        disabledContentColor: Color = getButtonToken().disabledBackgroundColor()
    ): ButtonColors = DefaultButtonColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledBackgroundColor = backgroundColor,
        disabledContentColor = disabledContentColor
    )

    @Composable
    fun primaryButtonSize(
        contentPadding: PaddingValues = PaddingValues(
            horizontal = 20.dp,
            vertical = 12.dp
        ),
        iconSpacing: Dp = 8.dp,
        textStyle: TextStyle = TextStyle(fontSize =  getButtonToken().textFont().size, fontWeight = getButtonToken().textFont().weight)
    ): ButtonSize = DefaultButtonSize(
        contentPadding = contentPadding,
        iconSpacing = iconSpacing,
        textStyle = textStyle
    )

    @Composable
    fun secondaryButtonSize(
        contentPadding: PaddingValues = PaddingValues(
            horizontal = 12.dp,
            vertical = 10.dp
        ),
        iconSpacing: Dp = 4.dp,
        textStyle: TextStyle = TextStyle(fontSize =  getButtonToken().textFont().size, fontWeight = getButtonToken().textFont().weight)
    ): ButtonSize = DefaultButtonSize(
        contentPadding = contentPadding,
        iconSpacing = iconSpacing,
        textStyle = textStyle
    )

    @Composable
    fun tertiaryButtonSize(
        contentPadding: PaddingValues = PaddingValues(
            horizontal = 8.dp,
            vertical = 6.dp
        ),
        iconSpacing: Dp = 4.dp,
        textStyle: TextStyle = TextStyle(fontSize =  getButtonToken().textFont().size, fontWeight = getButtonToken().textFont().weight)
    ): ButtonSize = DefaultButtonSize(
        contentPadding = contentPadding,
        iconSpacing = iconSpacing,
        textStyle = textStyle
    )

    @Composable
    fun textButtonSize(
        contentPadding: PaddingValues = PaddingValues(
            horizontal = 8.dp,
            vertical = 10.dp
        ),
        textStyle: TextStyle = TextStyle(fontSize =  getButtonToken().textFont().size, fontWeight = getButtonToken().textFont().weight)
    ): ButtonSize = DefaultButtonSize(
        contentPadding = contentPadding,
        textStyle = textStyle
    )

    val OutlinedBorderSize = 1.dp


    val buttonBorder: BorderStroke
        @Composable
        get() = BorderStroke(getButtonToken().borderStrokeThickness(), getButtonToken().borderStrokeColor())

    val outlinedBorder: BorderStroke
        @Composable
        get() = BorderStroke(getButtonToken().borderStrokeThickness(), getButtonToken().borderStrokeColor())

}

@Immutable
private data class DefaultButtonColors(
    private val backgroundColor: Color,
    private val contentColor: Color,
    private val disabledBackgroundColor: Color,
    private val disabledContentColor: Color
) : ButtonColors {
    @Composable
    override fun backgroundColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) backgroundColor else disabledBackgroundColor)
    }

    @Composable
    override fun contentColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) contentColor else disabledContentColor)
    }
}

@Immutable
private data class DefaultButtonSize(
    private val contentPadding: PaddingValues,
    private val iconSpacing: Dp = 0.dp,
    private val textStyle: TextStyle,
) : ButtonSize {
    @Composable
    override fun contentPadding(): State<PaddingValues> {
        return rememberUpdatedState(contentPadding)
    }

    @Composable
    override fun iconSpacing(): State<Dp> {
        return rememberUpdatedState(iconSpacing)
    }

    @Composable
    override fun textStyle(): State<TextStyle> {
        return rememberUpdatedState(textStyle)
    }
}