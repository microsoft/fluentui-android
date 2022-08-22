package com.microsoft.fluentui.tokenized.persona

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.*
import java.lang.Math.max

val LocalAvatarGroupTokens = compositionLocalOf { AvatarGroupTokens() }
val LocalAvatarGroupInfo = compositionLocalOf { AvatarGroupInfo() }

const val DEFAULT_MAX_AVATAR = 5

/**
 * API to create a group of Avatar. This can be done in 2 formats, [AvatarGroupStyle.Stack] or [AvatarGroupStyle.Pile].
 * Stack has negative spacing between Avatars whereas Pile has positive.
 * Activity Rings Can be enabled in both type of Groups, but presence indicator can be seen only in Pile
 *
 * @param group [Group] of people whose Avatar has to be created
 * @param modifier Optional modifier for AvatarGroup
 * @param size Set size of AvatarGroup. Default: [AvatarSize.Medium]
 * @param style Set style of AvatarGroup. Default: [AvatarGroupStyle.Stack]
 * @param maxVisibleAvatar Maximum number of avatars to be displayed. If number is less than total Group size, Overflow Avatar is added.
 * @param enablePresence Enable/Disable Presence Indicator in Avatars. Works only for [AvatarGroupStyle.Pile]
 * @param avatarToken Token to provide appearance values to Avatar
 * @param avatarGroupToken Token to provide appearance values to AvatarGroup
 */
@Composable
fun AvatarGroup(
        group: Group,
        modifier: Modifier = Modifier,
        size: AvatarSize = AvatarSize.Medium,
        style: AvatarGroupStyle = AvatarGroupStyle.Stack,
        maxVisibleAvatar: Int = DEFAULT_MAX_AVATAR,
        enablePresence: Boolean = false,
        avatarToken: AvatarTokens? = null,
        avatarGroupToken: AvatarGroupTokens? = null
) {
    val token = avatarGroupToken
            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.AvatarGroup] as AvatarGroupTokens

    val visibleAvatar: Int = if (maxVisibleAvatar < 0)
        0
    else if (maxVisibleAvatar > group.members.size)
        group.members.size
    else
        maxVisibleAvatar

    var enablePresence: Boolean = enablePresence
    if (style == AvatarGroupStyle.Stack)
        enablePresence = false

    CompositionLocalProvider(
            LocalAvatarGroupTokens provides token,
            LocalAvatarGroupInfo provides AvatarGroupInfo(size, style)
    ) {
        val spacing: MutableList<Int> = mutableListOf()
        for (i in 0 until visibleAvatar) {
            val person = group.members[i]
            if (i != 0) {
                spacing.add(with(LocalDensity.current) {
                    getAvatarGroupTokens().spacing(getAvatarGroupInfo(), person.isActive).roundToPx()
                })
            }
        }
        if (group.members.size > visibleAvatar || group.members.isEmpty()) {
            spacing.add(with(LocalDensity.current) {
                getAvatarGroupTokens().spacing(getAvatarGroupInfo(), false).roundToPx()
            })
        }

        val semanticModifier: Modifier = Modifier.semantics(true) {
            contentDescription = "Group Name: ${group.groupName}. Total ${group.members.size} members. "
        }

        Layout(modifier = modifier
                .padding(8.dp)
                .then(semanticModifier), content = {
            for (i in 0 until visibleAvatar) {
                val person = group.members[i]
                Avatar(person, size = size, enableActivityRings = true, enablePresence = enablePresence, avatarToken = avatarToken)
            }
            if (group.members.size > visibleAvatar || group.members.isEmpty()) {
                Avatar(group.members.size - visibleAvatar, size = size,
                        enableActivityRings = true, avatarToken = avatarToken)
            }
        }) { measurables, constraints ->
            val placeables = measurables.map { measurable ->
                measurable.measure(constraints)
            }

            var layoutHeight = 0
            var layoutWidth = 0
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