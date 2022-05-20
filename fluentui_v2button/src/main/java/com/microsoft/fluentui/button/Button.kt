package com.microsoft.fluentui.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.microsoft.fluentui.theme.token.*


val LocalButtonToken = compositionLocalOf { ButtonToken() }
val LocalButtonInfo = compositionLocalOf {ButtonInfo()}

@Composable
fun CreateButton (
    modifier: Modifier = Modifier,
    style:ButtonStyle = ButtonStyle.Button,
    size:ButtonSize = ButtonSize.Medium,
    enabled: Boolean = true,
    buttonToken:ButtonToken? = null,
    onClick:() -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    icon: @Composable (RowScope.() -> Unit)? = null,
    text: String
) {
    val buttonInfo = remember{
        ButtonInfo(style,size)
    }

    val token  = remember {
        (buttonToken ?: FluentTheme.tokens[ControlType.Button] as ButtonToken)
    }

    CompositionLocalProvider(LocalButtonToken provides token,
        LocalButtonInfo provides buttonInfo ) {
        Button(
            onClick = onClick,
            enabled = enabled,
            modifier = modifier,
            interactionSource = interactionSource,
            icon = icon,
            content = getText(text)
        )
    }
}

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    icon: @Composable (RowScope.() -> Unit)? = null,
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
    val backgroundColor = backgroundColor(enabled,interactionSource)
    val contentColor = textColor(enabled,interactionSource)
    val contentPadding = getButtonToken().padding(getButtonInfo())
    val iconSpacing = getButtonToken().spacing(getButtonInfo())
    val border = getBorder()
    val textStyle = TextStyle(fontSize =  getButtonToken().fontInfo(getButtonInfo()).fontSize.size, fontWeight = getButtonToken().fontInfo(getButtonInfo()).weight)
    val shape = RoundedCornerShape(getButtonToken().borderRadius(getButtonInfo()))

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
                        minHeight = getButtonToken().minHeight(buttonInfo = getButtonInfo())
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

@Composable
fun getButtonToken() : ButtonToken{
    return LocalButtonToken.current
}

@Composable
fun getButtonInfo() :ButtonInfo{
    return LocalButtonInfo.current
}

@Composable
fun getText (text:String): @Composable RowScope.() -> Unit {
    return {
        Text(text = text,
            fontSize = getButtonToken().fontInfo(getButtonInfo()).fontSize.size,
            lineHeight = getButtonToken().fontInfo(getButtonInfo()).fontSize.lineHeight,
            fontWeight = getButtonToken().fontInfo(getButtonInfo()).weight
        )
    }
}

@Composable
fun getBorder() : BorderStroke?{
    val borderSize = getButtonToken().outerBorderSize(getButtonInfo())
    return if (borderSize == 0.dp)
        null
    else
        BorderStroke(borderSize, getButtonToken().outerStrokeColor(getButtonInfo()).focused)
}

@Composable
fun backgroundColor(enabled: Boolean, interactionSource: InteractionSource): Color {
    if(enabled) {
        val isPressed by interactionSource.collectIsPressedAsState()
        if(isPressed)
            return getButtonToken().backgroundColor(getButtonInfo()).pressed

        val isFocused by interactionSource.collectIsFocusedAsState()
        if(isFocused)
            return getButtonToken().backgroundColor(getButtonInfo()).focused

        val isHovered by interactionSource.collectIsHoveredAsState()
        if(isHovered)
            return getButtonToken().backgroundColor(getButtonInfo()).focused

        return getButtonToken().backgroundColor(getButtonInfo()).rest
    }
    else {
        return getButtonToken().backgroundColor(getButtonInfo()).disabled
    }
}

@Composable
fun textColor(enabled: Boolean, interactionSource: InteractionSource): Color {
    if(enabled) {
        val isPressed by interactionSource.collectIsPressedAsState()
        if(isPressed)
            return getButtonToken().textColor(getButtonInfo()).pressed

        val isFocused by interactionSource.collectIsFocusedAsState()
        if(isFocused)
            return getButtonToken().textColor(getButtonInfo()).focused

        val isHovered by interactionSource.collectIsHoveredAsState()
        if(isHovered)
            return getButtonToken().textColor(getButtonInfo()).focused

        return getButtonToken().textColor(getButtonInfo()).rest
    }
    else {
        return getButtonToken().textColor(getButtonInfo()).disabled
    }
}

