package com.microsoft.fluentui.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.microsoft.fluentui.theme.token.StateBorderStroke
import com.microsoft.fluentui.theme.token.StateColor
import com.microsoft.fluentui.theme.token.controlTokens.ButtonInfo
import com.microsoft.fluentui.theme.token.controlTokens.ButtonTokens
import com.microsoft.fluentui.theme.token.controlTokens.FABInfo
import com.microsoft.fluentui.theme.token.controlTokens.FABTokens
import java.security.InvalidParameterException

@Composable
fun <S, T> backgroundColor(tokens: S, info: T, enabled: Boolean, interactionSource: InteractionSource): Color {
    if (!(tokens is ButtonTokens && info is ButtonInfo) && !(tokens is FABTokens && info is FABInfo))
        throw InvalidParameterException()

    val fetchBackgroundColor: @Composable () -> StateColor = {
        if (tokens is ButtonTokens)
            (tokens as ButtonTokens).backgroundColor(info as ButtonInfo)
        else
            (tokens as FABTokens).backgroundColor(info as FABInfo)
    }

    if (enabled) {
        val isPressed by interactionSource.collectIsPressedAsState()
        if (isPressed)
            return fetchBackgroundColor().pressed

        val isFocused by interactionSource.collectIsFocusedAsState()
        if (isFocused)
            return fetchBackgroundColor().focused

        val isHovered by interactionSource.collectIsHoveredAsState()
        if (isHovered)
            return fetchBackgroundColor().focused

        return fetchBackgroundColor().rest
    } else
        return fetchBackgroundColor().disabled
}

@Composable
fun <S, T> iconColor(tokens: S, info: T, enabled: Boolean, interactionSource: InteractionSource): Color {
    if (!(tokens is ButtonTokens && info is ButtonInfo) && !(tokens is FABTokens && info is FABInfo))
        throw InvalidParameterException()

    val fetchIconColor: @Composable () -> StateColor = {
        if (tokens is ButtonTokens)
            (tokens as ButtonTokens).iconColor(info as ButtonInfo)
        else
            (tokens as FABTokens).iconColor(info as FABInfo)
    }

    if (enabled) {
        val isPressed by interactionSource.collectIsPressedAsState()
        if (isPressed)
            return fetchIconColor().pressed

        val isFocused by interactionSource.collectIsFocusedAsState()
        if (isFocused)
            return fetchIconColor().focused

        val isHovered by interactionSource.collectIsHoveredAsState()
        if (isHovered)
            return fetchIconColor().focused

        return fetchIconColor().rest
    } else
        return fetchIconColor().disabled
}

@Composable
fun <S, T> textColor(tokens: S, info: T, enabled: Boolean, interactionSource: InteractionSource): Color {
    if (!(tokens is ButtonTokens && info is ButtonInfo) && !(tokens is FABTokens && info is FABInfo))
        throw InvalidParameterException()

    val fetchTextColor: @Composable () -> StateColor = {
        if (tokens is ButtonTokens)
            (tokens as ButtonTokens).textColor(info as ButtonInfo)
        else
            (tokens as FABTokens).textColor(info as FABInfo)
    }

    if (enabled) {
        val isPressed by interactionSource.collectIsPressedAsState()
        if (isPressed)
            return fetchTextColor().pressed

        val isFocused by interactionSource.collectIsFocusedAsState()
        if (isFocused)
            return fetchTextColor().focused

        val isHovered by interactionSource.collectIsHoveredAsState()
        if (isHovered)
            return fetchTextColor().focused

        return fetchTextColor().rest
    } else
        return fetchTextColor().disabled
}

@Composable
fun <S, T> borderStroke(tokens: S, info: T, enabled: Boolean, interactionSource: InteractionSource): List<BorderStroke> {
    if (!(tokens is ButtonTokens && info is ButtonInfo) && !(tokens is FABTokens && info is FABInfo))
        throw InvalidParameterException()

    val fetchBorderStroke: @Composable () -> StateBorderStroke = {
        if (tokens is ButtonTokens)
            (tokens as ButtonTokens).borderStroke(info as ButtonInfo)
        else
            (tokens as FABTokens).borderStroke(info as FABInfo)
    }

    if (enabled) {
        val isPressed by interactionSource.collectIsPressedAsState()
        if (isPressed)
            return fetchBorderStroke().pressed

        val isFocused by interactionSource.collectIsFocusedAsState()
        if (isFocused)
            return fetchBorderStroke().focused

        val isHovered by interactionSource.collectIsHoveredAsState()
        if (isHovered)
            return fetchBorderStroke().focused

        return fetchBorderStroke().rest
    } else
        return fetchBorderStroke().disabled
}
