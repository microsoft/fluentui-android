package com.microsoft.fluentui.tokenized.persona

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.controlTokens.*

val LocalSearchBoxPersonaChipTokens = compositionLocalOf { SearchBoxPersonaChipTokens() }
val LocalSearchBoxPersonaChipInfo = compositionLocalOf { SearchBoxPersonaChipInfo() }

@Composable
fun SearchBoxPersonaChip(
    text: String,
    modifier: Modifier = Modifier,
    style: FluentStyle = FluentStyle.Neutral,
    size: SearchBoxPersonaChipSize = SearchBoxPersonaChipSize.Medium,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,
    showCloseButton: Boolean = false,
    person: Person? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    searchbarPersonaChipTokens: SearchBoxPersonaChipTokens? = null
) {
    val token = searchbarPersonaChipTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.SearchBarPersonaChip] as SearchBoxPersonaChipTokens
    CompositionLocalProvider(
        LocalSearchBoxPersonaChipTokens provides token,
        LocalSearchBoxPersonaChipInfo provides SearchBoxPersonaChipInfo(
            style,
            enabled
        )
    ) {
        var isChipSelected by remember { mutableStateOf(false) }
        val backgroundColor = getColorByState(
            stateData = getSearchBoxPersonaChipTokens().backgroundColor(searchBoxPersonaChipInfo = getSearchBoxPersonaChipInfo()),
            enabled = enabled, selected = isChipSelected, interactionSource = interactionSource
        )
        val textColor = getColorByState(
            stateData = getSearchBoxPersonaChipTokens().textColor(searchBoxPersonaChipInfo = getSearchBoxPersonaChipInfo()),
            enabled = enabled, selected = isChipSelected, interactionSource = interactionSource
        )
        val font = getSearchBoxPersonaChipTokens().fontSize(searchBoxPersonaChipInfo = getSearchBoxPersonaChipInfo())
        val avatarSize = getSearchBoxPersonaChipTokens().avatarSize(searchBoxPersonaChipInfo = getSearchBoxPersonaChipInfo())
        val verticalPadding =
            getSearchBoxPersonaChipTokens().verticalPadding(searchBoxPersonaChipInfo = getSearchBoxPersonaChipInfo())
        val horizontalPadding =
            getSearchBoxPersonaChipTokens().horizontalPadding(searchBoxPersonaChipInfo = getSearchBoxPersonaChipInfo())
        val avatarToTextSpacing =
            getSearchBoxPersonaChipTokens().avatarToTextSpacing(searchBoxPersonaChipInfo = getSearchBoxPersonaChipInfo())
        val cornerRadius =
            getSearchBoxPersonaChipTokens().borderRadius(searchBoxPersonaChipInfo = getSearchBoxPersonaChipInfo())


        Box(
            modifier = modifier
                .clickable(
                    enabled = enabled,
                    onClick = {
                        if (isChipSelected && onClick != null)
                            onClick()
                        isChipSelected = !isChipSelected
                    },
                    interactionSource = interactionSource,
                    indication = rememberRipple()
                )
                .clip(RoundedCornerShape(cornerRadius))
                .background(backgroundColor), contentAlignment = Alignment.Center
        ) {
            Row(
                modifier.padding(
                    horizontal = horizontalPadding,
                    vertical = verticalPadding
                ),
                horizontalArrangement = Arrangement.spacedBy(avatarToTextSpacing),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showCloseButton && size == SearchBoxPersonaChipSize.Medium && isChipSelected) {
                    Icon(
                        Icons.Filled.Close,
                        modifier = Modifier.size(16.dp).clickable(enabled = true, onClick = (if(onClick!=null) onClick else {}) as () -> Unit, role = Role.Button),
                        contentDescription = "Close",
                        tint = textColor
                    )

                } else {
                    if (person != null && size == SearchBoxPersonaChipSize.Medium) {
                        Avatar(person = person, size = avatarSize)
                    }
                }
                Text(text = text, color = textColor, fontSize = font.fontSize.size, textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun getSearchBoxPersonaChipTokens(): SearchBoxPersonaChipTokens {
    return LocalSearchBoxPersonaChipTokens.current
}

@Composable
fun getSearchBoxPersonaChipInfo(): SearchBoxPersonaChipInfo {
    return LocalSearchBoxPersonaChipInfo.current
}