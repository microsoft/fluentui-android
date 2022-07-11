package com.microsoft.fluentui.tokenised.persona

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.*
import java.lang.Integer.max
import java.lang.Integer.min

val LocalAvatarGroupTokens = compositionLocalOf { AvatarGroupTokens() }
val LocalAvatarGroupInfo = compositionLocalOf { AvatarGroupInfo() }

@Composable
fun AvatarGroup(
        group: Group,
        size: AvatarSize = AvatarSize.Medium,
        imageNAStyle: AvatarImageNA = AvatarImageNA.Standard,
        style: AvatarGroupStyle = AvatarGroupStyle.Stack,
        modifier: Modifier = Modifier,
        avatarGroupToken: AvatarGroupTokens? = null,
        maxAvatar: Int = 5,
        enablePresence: Boolean = true
) {

    val token = avatarGroupToken
            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.AvatarGroup] as AvatarGroupTokens

    var maxAvatars = 1
    if (maxAvatar < 1)
        maxAvatars = 1
    else if (maxAvatar > group.members.size)
        maxAvatars = group.members.size
    else
        maxAvatars = maxAvatar

    CompositionLocalProvider(
            LocalAvatarGroupTokens provides token,
            LocalAvatarGroupInfo provides AvatarGroupInfo(size, style)
    ) {
        var spacing: MutableList<Int> = mutableListOf()
        for (i in 0 until min(maxAvatars, group.members.size)) {
            val person = group.members[i]
            if (i != 0) {
                spacing.add(with(LocalDensity.current) {
                    getAvatarGroupTokens().spacing(getAvatarGroupInfo(), person.isPersonActive()).roundToPx()
                })
            }
        }
        if (group.members.size > maxAvatars) {
            spacing.add(with(LocalDensity.current) {
                getAvatarGroupTokens().spacing(getAvatarGroupInfo(), false).roundToPx()
            })
        }

        Layout(modifier = Modifier.padding(8.dp), content = {
            for (i in 0 until min(maxAvatars, group.members.size)) {
                val person = group.members[i]
                Avatar(person, size, imageNAStyle, enableActivityRings = true,
                        enablePresence = if (style == AvatarGroupStyle.Stack) false else enablePresence)
            }
            if (group.members.size > maxAvatars) {
                Avatar(group.members.size - maxAvatars, size, enableActivityRings = true)
            }
        }) { measurables, constraints ->
            val placeables = measurables.map { measurable ->
                measurable.measure(constraints)
            }

            var layoutHeight: Int = 0
            var layoutWidth: Int = 0
            placeables.forEach {
                layoutHeight = max(layoutHeight, it.height)
                layoutWidth += it.width
            }
            layoutWidth += spacing.sum()

            layout(layoutWidth, layoutHeight) {
                var xPosition = 0
                placeables.forEach { placeable ->
                    placeable.placeRelative(y = 0, x = xPosition)
                    if (placeable != placeables.last())
                        xPosition += placeable.width + spacing[placeables.indexOf(placeable)]
                }
            }
        }
    }
}

@Composable
fun getAvatarGroupTokens(): AvatarGroupTokens {
    return LocalAvatarGroupTokens.current
}

@Composable
fun getAvatarGroupInfo(): AvatarGroupInfo {
    return LocalAvatarGroupInfo.current
}