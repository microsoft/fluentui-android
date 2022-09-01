package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.icons.AvatarIcons
import com.microsoft.fluentui.icons.avataricons.Icon
import com.microsoft.fluentui.icons.avataricons.Presence
import com.microsoft.fluentui.icons.avataricons.icon.Anonymous
import com.microsoft.fluentui.icons.avataricons.icon.Standard
import com.microsoft.fluentui.icons.avataricons.icon.anonymous.*
import com.microsoft.fluentui.icons.avataricons.icon.standard.*
import com.microsoft.fluentui.icons.avataricons.presence.*
import com.microsoft.fluentui.icons.avataricons.presence.available.Large
import com.microsoft.fluentui.icons.avataricons.presence.available.Medium
import com.microsoft.fluentui.icons.avataricons.presence.available.Small
import com.microsoft.fluentui.icons.avataricons.presence.available.large.Dark
import com.microsoft.fluentui.icons.avataricons.presence.available.large.Light
import com.microsoft.fluentui.icons.avataricons.presence.available.medium.Dark
import com.microsoft.fluentui.icons.avataricons.presence.available.medium.Light
import com.microsoft.fluentui.icons.avataricons.presence.available.small.Dark
import com.microsoft.fluentui.icons.avataricons.presence.available.small.Light
import com.microsoft.fluentui.icons.avataricons.presence.availableoof.Large
import com.microsoft.fluentui.icons.avataricons.presence.availableoof.Medium
import com.microsoft.fluentui.icons.avataricons.presence.availableoof.Small
import com.microsoft.fluentui.icons.avataricons.presence.availableoof.large.Dark
import com.microsoft.fluentui.icons.avataricons.presence.availableoof.large.Light
import com.microsoft.fluentui.icons.avataricons.presence.availableoof.medium.Dark
import com.microsoft.fluentui.icons.avataricons.presence.availableoof.medium.Light
import com.microsoft.fluentui.icons.avataricons.presence.availableoof.small.Dark
import com.microsoft.fluentui.icons.avataricons.presence.availableoof.small.Light
import com.microsoft.fluentui.icons.avataricons.presence.away.Large
import com.microsoft.fluentui.icons.avataricons.presence.away.Medium
import com.microsoft.fluentui.icons.avataricons.presence.away.Small
import com.microsoft.fluentui.icons.avataricons.presence.away.large.Dark
import com.microsoft.fluentui.icons.avataricons.presence.away.large.Light
import com.microsoft.fluentui.icons.avataricons.presence.away.medium.Dark
import com.microsoft.fluentui.icons.avataricons.presence.away.medium.Light
import com.microsoft.fluentui.icons.avataricons.presence.away.small.Dark
import com.microsoft.fluentui.icons.avataricons.presence.away.small.Light
import com.microsoft.fluentui.icons.avataricons.presence.awayoof.Large
import com.microsoft.fluentui.icons.avataricons.presence.awayoof.Medium
import com.microsoft.fluentui.icons.avataricons.presence.awayoof.Small
import com.microsoft.fluentui.icons.avataricons.presence.awayoof.large.Dark
import com.microsoft.fluentui.icons.avataricons.presence.awayoof.large.Light
import com.microsoft.fluentui.icons.avataricons.presence.awayoof.medium.Dark
import com.microsoft.fluentui.icons.avataricons.presence.awayoof.medium.Light
import com.microsoft.fluentui.icons.avataricons.presence.awayoof.small.Dark
import com.microsoft.fluentui.icons.avataricons.presence.awayoof.small.Light
import com.microsoft.fluentui.icons.avataricons.presence.blocked.Large
import com.microsoft.fluentui.icons.avataricons.presence.blocked.Medium
import com.microsoft.fluentui.icons.avataricons.presence.blocked.Small
import com.microsoft.fluentui.icons.avataricons.presence.blocked.large.Dark
import com.microsoft.fluentui.icons.avataricons.presence.blocked.large.Light
import com.microsoft.fluentui.icons.avataricons.presence.blocked.medium.Dark
import com.microsoft.fluentui.icons.avataricons.presence.blocked.medium.Light
import com.microsoft.fluentui.icons.avataricons.presence.blocked.small.Dark
import com.microsoft.fluentui.icons.avataricons.presence.blocked.small.Light
import com.microsoft.fluentui.icons.avataricons.presence.busy.Large
import com.microsoft.fluentui.icons.avataricons.presence.busy.Medium
import com.microsoft.fluentui.icons.avataricons.presence.busy.Small
import com.microsoft.fluentui.icons.avataricons.presence.busy.large.Dark
import com.microsoft.fluentui.icons.avataricons.presence.busy.large.Light
import com.microsoft.fluentui.icons.avataricons.presence.busy.medium.Dark
import com.microsoft.fluentui.icons.avataricons.presence.busy.medium.Light
import com.microsoft.fluentui.icons.avataricons.presence.busy.small.Dark
import com.microsoft.fluentui.icons.avataricons.presence.busy.small.Light
import com.microsoft.fluentui.icons.avataricons.presence.busyoof.Large
import com.microsoft.fluentui.icons.avataricons.presence.busyoof.Medium
import com.microsoft.fluentui.icons.avataricons.presence.busyoof.Small
import com.microsoft.fluentui.icons.avataricons.presence.busyoof.large.Dark
import com.microsoft.fluentui.icons.avataricons.presence.busyoof.large.Light
import com.microsoft.fluentui.icons.avataricons.presence.busyoof.medium.Dark
import com.microsoft.fluentui.icons.avataricons.presence.busyoof.medium.Light
import com.microsoft.fluentui.icons.avataricons.presence.busyoof.small.Dark
import com.microsoft.fluentui.icons.avataricons.presence.busyoof.small.Light
import com.microsoft.fluentui.icons.avataricons.presence.dnd.Large
import com.microsoft.fluentui.icons.avataricons.presence.dnd.Medium
import com.microsoft.fluentui.icons.avataricons.presence.dnd.Small
import com.microsoft.fluentui.icons.avataricons.presence.dnd.large.Dark
import com.microsoft.fluentui.icons.avataricons.presence.dnd.large.Light
import com.microsoft.fluentui.icons.avataricons.presence.dnd.medium.Dark
import com.microsoft.fluentui.icons.avataricons.presence.dnd.medium.Light
import com.microsoft.fluentui.icons.avataricons.presence.dnd.small.Dark
import com.microsoft.fluentui.icons.avataricons.presence.dnd.small.Light
import com.microsoft.fluentui.icons.avataricons.presence.dndoof.Large
import com.microsoft.fluentui.icons.avataricons.presence.dndoof.Medium
import com.microsoft.fluentui.icons.avataricons.presence.dndoof.Small
import com.microsoft.fluentui.icons.avataricons.presence.dndoof.large.Dark
import com.microsoft.fluentui.icons.avataricons.presence.dndoof.large.Light
import com.microsoft.fluentui.icons.avataricons.presence.dndoof.medium.Dark
import com.microsoft.fluentui.icons.avataricons.presence.dndoof.medium.Light
import com.microsoft.fluentui.icons.avataricons.presence.dndoof.small.Dark
import com.microsoft.fluentui.icons.avataricons.presence.dndoof.small.Light
import com.microsoft.fluentui.icons.avataricons.presence.offline.Large
import com.microsoft.fluentui.icons.avataricons.presence.offline.Medium
import com.microsoft.fluentui.icons.avataricons.presence.offline.Small
import com.microsoft.fluentui.icons.avataricons.presence.offline.large.Dark
import com.microsoft.fluentui.icons.avataricons.presence.offline.large.Light
import com.microsoft.fluentui.icons.avataricons.presence.offline.medium.Dark
import com.microsoft.fluentui.icons.avataricons.presence.offline.medium.Light
import com.microsoft.fluentui.icons.avataricons.presence.offline.small.Dark
import com.microsoft.fluentui.icons.avataricons.presence.offline.small.Light
import com.microsoft.fluentui.icons.avataricons.presence.oof.Large
import com.microsoft.fluentui.icons.avataricons.presence.oof.Medium
import com.microsoft.fluentui.icons.avataricons.presence.oof.Small
import com.microsoft.fluentui.icons.avataricons.presence.oof.large.Dark
import com.microsoft.fluentui.icons.avataricons.presence.oof.large.Light
import com.microsoft.fluentui.icons.avataricons.presence.oof.medium.Dark
import com.microsoft.fluentui.icons.avataricons.presence.oof.medium.Light
import com.microsoft.fluentui.icons.avataricons.presence.oof.small.Dark
import com.microsoft.fluentui.icons.avataricons.presence.oof.small.Light
import com.microsoft.fluentui.icons.avataricons.presence.unknown.Large
import com.microsoft.fluentui.icons.avataricons.presence.unknown.Medium
import com.microsoft.fluentui.icons.avataricons.presence.unknown.Small
import com.microsoft.fluentui.icons.avataricons.presence.unknown.large.Dark
import com.microsoft.fluentui.icons.avataricons.presence.unknown.large.Light
import com.microsoft.fluentui.icons.avataricons.presence.unknown.medium.Dark
import com.microsoft.fluentui.icons.avataricons.presence.unknown.medium.Light
import com.microsoft.fluentui.icons.avataricons.presence.unknown.small.Dark
import com.microsoft.fluentui.icons.avataricons.presence.unknown.small.Light
import com.microsoft.fluentui.theme.FluentTheme.aliasTokens
import com.microsoft.fluentui.theme.FluentTheme.themeMode
import com.microsoft.fluentui.theme.token.*
import kotlinx.parcelize.Parcelize
import kotlin.math.abs

enum class AvatarType {
    Person,
    Group,
    Overflow
}

enum class AvatarSize {
    XSmall,
    Small,
    Medium,
    Large,
    XLarge,
    XXLarge
}

enum class AvatarStatus {
    Available,
    Away,
    Busy,
    DND,
    Blocked,
    Unknown,
    Offline
}

enum class AvatarStyle {
    Standard,
    StandardInverted,
    Anonymous,
    AnonymousAccent
}

enum class ActivityRingSize {
    XSmall,
    Small,
    Medium,
    Large,
    XLarge,
    XXLarge
}

data class AvatarInfo(
        val size: AvatarSize = AvatarSize.Medium,
        val type: AvatarType = AvatarType.Person,
        val active: Boolean = false,
        val status: AvatarStatus = AvatarStatus.Available,
        val isOOO: Boolean = false,
        val isImageAvailable: Boolean = false,
        val hasValidInitials: Boolean = false,
        val calculatedColorKey: String = ""
) : ControlInfo

@Parcelize
open class AvatarTokens(private val activityRingToken: ActivityRingsToken = ActivityRingsToken()) : ControlToken, Parcelable {

    companion object {
        const val Type: String = "Avatar"
    }

    @Composable
    open fun avatarStyle(avatarInfo: AvatarInfo): AvatarStyle {
        return AvatarStyle.Standard
    }

    @Composable
    open fun fontSize(avatarInfo: AvatarInfo): Dp {
        return when (avatarInfo.size) {
            AvatarSize.XSmall -> 9.dp
            AvatarSize.Small -> 12.dp
            AvatarSize.Medium -> 14.dp
            AvatarSize.Large -> 15.dp
            AvatarSize.XLarge -> 20.dp
            AvatarSize.XXLarge -> 28.dp
        }
    }

    @Composable
    open fun avatarSize(avatarInfo: AvatarInfo): Dp {
        return when (avatarInfo.size) {
            AvatarSize.XSmall -> 16.dp
            AvatarSize.Small -> 24.dp
            AvatarSize.Medium -> 32.dp
            AvatarSize.Large -> 40.dp
            AvatarSize.XLarge -> 52.dp
            AvatarSize.XXLarge -> 72.dp
        }
    }

    @Composable
    open fun icon(avatarInfo: AvatarInfo): ImageVector {
        return when (avatarStyle(avatarInfo)) {
            AvatarStyle.Standard, AvatarStyle.StandardInverted ->
                when (avatarInfo.size) {
                    AvatarSize.XSmall -> AvatarIcons.Icon.Standard.Xsmall
                    AvatarSize.Small -> AvatarIcons.Icon.Standard.Small
                    AvatarSize.Medium -> AvatarIcons.Icon.Standard.Medium
                    AvatarSize.Large -> AvatarIcons.Icon.Standard.Large
                    AvatarSize.XLarge -> AvatarIcons.Icon.Standard.Xlarge
                    AvatarSize.XXLarge -> AvatarIcons.Icon.Standard.Xxlarge
                }

            AvatarStyle.Anonymous, AvatarStyle.AnonymousAccent ->
                when (avatarInfo.size) {
                    AvatarSize.XSmall -> AvatarIcons.Icon.Anonymous.Xsmall
                    AvatarSize.Small -> AvatarIcons.Icon.Anonymous.Small
                    AvatarSize.Medium -> AvatarIcons.Icon.Anonymous.Medium
                    AvatarSize.Large -> AvatarIcons.Icon.Anonymous.Large
                    AvatarSize.XLarge -> AvatarIcons.Icon.Anonymous.Xlarge
                    AvatarSize.XXLarge -> AvatarIcons.Icon.Anonymous.Xxlarge
                }
        }
    }

    @Composable
    open fun foregroundColor(avatarInfo: AvatarInfo): Color {
        return if (avatarInfo.isImageAvailable || avatarInfo.hasValidInitials) {
            FluentColor(light = calculatedColor(avatarInfo.calculatedColorKey, GlobalTokens.SharedColorsTokens.shade30),
                    dark = calculatedColor(avatarInfo.calculatedColorKey,GlobalTokens.SharedColorsTokens.tint40)).value(
                    themeMode = themeMode
            )
        } else if (avatarInfo.type == AvatarType.Overflow) {
            aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                    themeMode = themeMode
            )
        } else {
            when (avatarStyle(avatarInfo)) {
                AvatarStyle.Standard ->
                    aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                            themeMode = themeMode
                    )
                AvatarStyle.StandardInverted ->
                    aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
                            themeMode = themeMode
                    )
                AvatarStyle.Anonymous ->
                    aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                            themeMode = themeMode
                    )
                AvatarStyle.AnonymousAccent ->
                    aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
                            themeMode = themeMode
                    )
            }
        }
    }

    @Composable
    open fun backgroundColor(avatarInfo: AvatarInfo): Color {
        return if (avatarInfo.isImageAvailable || avatarInfo.hasValidInitials) {
            FluentColor(light = calculatedColor(avatarInfo.calculatedColorKey,GlobalTokens.SharedColorsTokens.tint40),
                    dark = calculatedColor(avatarInfo.calculatedColorKey,GlobalTokens.SharedColorsTokens.shade30)).value(
                    themeMode = themeMode
            )
        } else if (avatarInfo.type == AvatarType.Overflow) {
            aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background5].value(
                    themeMode = themeMode
            )
        } else {
            when (avatarStyle(avatarInfo)) {
                AvatarStyle.Standard ->
                    aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                            themeMode = themeMode
                    )
                AvatarStyle.StandardInverted ->
                    aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                            themeMode = themeMode
                    )
                AvatarStyle.Anonymous ->
                    aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background5].value(
                            themeMode = themeMode
                    )
                AvatarStyle.AnonymousAccent ->
                    aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground4].value(
                            themeMode = themeMode
                    )
            }
        }
    }

    @Composable
    open fun presenceIcon(avatarInfo: AvatarInfo): Icon {
        return when (avatarInfo.status) {
            AvatarStatus.Available ->
                when (avatarInfo.size) {
                    AvatarSize.XSmall -> Icon()
                    AvatarSize.Small -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Availableoof.Small.Light else AvatarIcons.Presence.Available.Small.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Availableoof.Small.Dark else AvatarIcons.Presence.Available.Small.Dark
                    )

                    AvatarSize.Medium -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Availableoof.Small.Light else AvatarIcons.Presence.Available.Small.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Availableoof.Small.Dark else AvatarIcons.Presence.Available.Small.Dark
                    )

                    AvatarSize.Large -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Availableoof.Medium.Light else AvatarIcons.Presence.Available.Medium.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Availableoof.Medium.Dark else AvatarIcons.Presence.Available.Medium.Dark
                    )

                    AvatarSize.XLarge -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Availableoof.Medium.Light else AvatarIcons.Presence.Available.Medium.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Availableoof.Medium.Dark else AvatarIcons.Presence.Available.Medium.Dark
                    )

                    AvatarSize.XXLarge -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Availableoof.Large.Light else AvatarIcons.Presence.Available.Large.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Availableoof.Large.Dark else AvatarIcons.Presence.Available.Large.Dark
                    )
                }

            AvatarStatus.Busy ->
                when (avatarInfo.size) {
                    AvatarSize.XSmall -> Icon()
                    AvatarSize.Small -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Busyoof.Small.Light else AvatarIcons.Presence.Busy.Small.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Busyoof.Small.Dark else AvatarIcons.Presence.Busy.Small.Dark
                    )

                    AvatarSize.Medium -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Busyoof.Small.Light else AvatarIcons.Presence.Busy.Small.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Busyoof.Small.Dark else AvatarIcons.Presence.Busy.Small.Dark
                    )

                    AvatarSize.Large -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Busyoof.Medium.Light else AvatarIcons.Presence.Busy.Medium.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Busyoof.Medium.Dark else AvatarIcons.Presence.Busy.Medium.Dark
                    )

                    AvatarSize.XLarge -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Busyoof.Medium.Light else AvatarIcons.Presence.Busy.Medium.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Busyoof.Medium.Dark else AvatarIcons.Presence.Busy.Medium.Dark
                    )

                    AvatarSize.XXLarge -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Busyoof.Large.Light else AvatarIcons.Presence.Busy.Large.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Busyoof.Large.Dark else AvatarIcons.Presence.Busy.Large.Dark
                    )
                }

            AvatarStatus.Away ->
                when (avatarInfo.size) {
                    AvatarSize.XSmall -> Icon()
                    AvatarSize.Small -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Awayoof.Small.Light else AvatarIcons.Presence.Away.Small.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Awayoof.Small.Dark else AvatarIcons.Presence.Away.Small.Dark
                    )

                    AvatarSize.Medium -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Awayoof.Small.Light else AvatarIcons.Presence.Away.Small.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Awayoof.Small.Dark else AvatarIcons.Presence.Away.Small.Dark
                    )

                    AvatarSize.Large -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Awayoof.Medium.Light else AvatarIcons.Presence.Away.Medium.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Awayoof.Medium.Dark else AvatarIcons.Presence.Away.Medium.Dark
                    )

                    AvatarSize.XLarge -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Awayoof.Medium.Light else AvatarIcons.Presence.Away.Medium.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Awayoof.Medium.Dark else AvatarIcons.Presence.Away.Medium.Dark
                    )

                    AvatarSize.XXLarge -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Awayoof.Large.Light else AvatarIcons.Presence.Away.Large.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Awayoof.Large.Dark else AvatarIcons.Presence.Away.Large.Dark
                    )
                }

            AvatarStatus.DND ->
                when (avatarInfo.size) {
                    AvatarSize.XSmall -> Icon()
                    AvatarSize.Small -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Dndoof.Small.Light else AvatarIcons.Presence.Dnd.Small.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Dndoof.Small.Dark else AvatarIcons.Presence.Dnd.Small.Dark
                    )

                    AvatarSize.Medium -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Dndoof.Small.Light else AvatarIcons.Presence.Dnd.Small.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Dndoof.Small.Dark else AvatarIcons.Presence.Dnd.Small.Dark
                    )

                    AvatarSize.Large -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Dndoof.Medium.Light else AvatarIcons.Presence.Dnd.Medium.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Dndoof.Medium.Dark else AvatarIcons.Presence.Dnd.Medium.Dark
                    )

                    AvatarSize.XLarge -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Dndoof.Medium.Light else AvatarIcons.Presence.Dnd.Medium.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Dndoof.Medium.Dark else AvatarIcons.Presence.Dnd.Medium.Dark
                    )

                    AvatarSize.XXLarge -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Dndoof.Large.Light else AvatarIcons.Presence.Dnd.Large.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Dndoof.Large.Dark else AvatarIcons.Presence.Dnd.Large.Dark
                    )
                }

            AvatarStatus.Unknown ->
                when (avatarInfo.size) {
                    AvatarSize.XSmall -> Icon()
                    AvatarSize.Small -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Light else AvatarIcons.Presence.Unknown.Small.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Dark else AvatarIcons.Presence.Unknown.Small.Dark
                    )

                    AvatarSize.Medium -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Light else AvatarIcons.Presence.Unknown.Small.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Dark else AvatarIcons.Presence.Unknown.Small.Dark
                    )

                    AvatarSize.Large -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Medium.Light else AvatarIcons.Presence.Unknown.Medium.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Medium.Dark else AvatarIcons.Presence.Unknown.Medium.Dark
                    )

                    AvatarSize.XLarge -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Medium.Light else AvatarIcons.Presence.Unknown.Medium.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Medium.Dark else AvatarIcons.Presence.Unknown.Medium.Dark
                    )

                    AvatarSize.XXLarge -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Large.Light else AvatarIcons.Presence.Unknown.Large.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Large.Dark else AvatarIcons.Presence.Unknown.Large.Dark
                    )
                }

            AvatarStatus.Blocked ->
                when (avatarInfo.size) {
                    AvatarSize.XSmall -> Icon()
                    AvatarSize.Small -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Light else AvatarIcons.Presence.Blocked.Small.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Dark else AvatarIcons.Presence.Blocked.Small.Dark
                    )

                    AvatarSize.Medium -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Light else AvatarIcons.Presence.Blocked.Small.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Dark else AvatarIcons.Presence.Blocked.Small.Dark
                    )

                    AvatarSize.Large -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Medium.Light else AvatarIcons.Presence.Blocked.Medium.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Medium.Dark else AvatarIcons.Presence.Blocked.Medium.Dark
                    )

                    AvatarSize.XLarge -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Medium.Light else AvatarIcons.Presence.Blocked.Medium.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Medium.Dark else AvatarIcons.Presence.Blocked.Medium.Dark
                    )

                    AvatarSize.XXLarge -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Large.Light else AvatarIcons.Presence.Blocked.Large.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Large.Dark else AvatarIcons.Presence.Blocked.Large.Dark
                    )
                }

            AvatarStatus.Offline ->
                when (avatarInfo.size) {
                    AvatarSize.XSmall -> Icon()
                    AvatarSize.Small -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Light else AvatarIcons.Presence.Offline.Small.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Dark else AvatarIcons.Presence.Offline.Small.Dark
                    )

                    AvatarSize.Medium -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Light else AvatarIcons.Presence.Offline.Small.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Dark else AvatarIcons.Presence.Offline.Small.Dark
                    )

                    AvatarSize.Large -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Medium.Light else AvatarIcons.Presence.Offline.Medium.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Medium.Dark else AvatarIcons.Presence.Offline.Medium.Dark
                    )

                    AvatarSize.XLarge -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Medium.Light else AvatarIcons.Presence.Offline.Medium.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Medium.Dark else AvatarIcons.Presence.Offline.Medium.Dark
                    )

                    AvatarSize.XXLarge -> Icon(
                            light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Large.Light else AvatarIcons.Presence.Offline.Large.Light,
                            dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Large.Dark else AvatarIcons.Presence.Offline.Large.Dark
                    )
                }
        }
    }

    @Composable
    open fun presenceOffset(avatarInfo: AvatarInfo): DpOffset {
        return when (avatarInfo.size) {
            AvatarSize.XSmall -> DpOffset(0.dp, 0.dp)
            AvatarSize.Small -> DpOffset(11.dp, 13.dp)
            AvatarSize.Medium -> DpOffset(20.dp, 20.dp)
            AvatarSize.Large -> DpOffset(26.dp, 26.dp)
            AvatarSize.XLarge -> DpOffset(36.dp, 36.dp)
            AvatarSize.XXLarge -> DpOffset(51.dp, 51.dp)
        }
    }

    @Composable
    open fun borderRadius(avatarInfo: AvatarInfo): Dp {
        return when (avatarInfo.size) {
            AvatarSize.XSmall -> GlobalTokens.borderRadius(GlobalTokens.BorderRadiusTokens.Small)
            AvatarSize.Small -> GlobalTokens.borderRadius(GlobalTokens.BorderRadiusTokens.Medium)
            AvatarSize.Medium -> GlobalTokens.borderRadius(GlobalTokens.BorderRadiusTokens.Medium)
            AvatarSize.Large -> GlobalTokens.borderRadius(GlobalTokens.BorderRadiusTokens.Large)
            AvatarSize.XLarge -> GlobalTokens.borderRadius(GlobalTokens.BorderRadiusTokens.Large)
            AvatarSize.XXLarge -> GlobalTokens.borderRadius(GlobalTokens.BorderRadiusTokens.XLarge)
        }
    }

    @Composable
    open fun borderStroke(avatarInfo: AvatarInfo): List<BorderStroke> {
        val glowColor: Color = if (avatarInfo.isImageAvailable || avatarInfo.hasValidInitials) {
            FluentColor(light = calculatedColor(avatarInfo.calculatedColorKey,GlobalTokens.SharedColorsTokens.primary),
                    dark = calculatedColor(avatarInfo.calculatedColorKey,GlobalTokens.SharedColorsTokens.tint30)).value(
                    themeMode = themeMode
            )
        } else if (avatarInfo.type == AvatarType.Overflow) {
            aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.Stroke1].value(
                    themeMode = themeMode
            )
        } else {
            when (avatarStyle(avatarInfo)) {
                AvatarStyle.Standard, AvatarStyle.StandardInverted, AvatarStyle.AnonymousAccent -> aliasTokens.brandStroke[AliasTokens.BrandStrokeColorTokens.BrandStroke1].value(
                        themeMode = themeMode
                )
                AvatarStyle.Anonymous -> aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.Stroke1].value(
                        themeMode = themeMode
                )
            }
        }

        return if (avatarInfo.active)
            when (avatarInfo.size) {
                AvatarSize.XSmall -> activityRingToken.activeBorderStroke(ActivityRingSize.XSmall, glowColor)
                AvatarSize.Small -> activityRingToken.activeBorderStroke(ActivityRingSize.Small, glowColor)
                AvatarSize.Medium -> activityRingToken.activeBorderStroke(ActivityRingSize.Medium, glowColor)
                AvatarSize.Large -> activityRingToken.activeBorderStroke(ActivityRingSize.Large, glowColor)
                AvatarSize.XLarge -> activityRingToken.activeBorderStroke(ActivityRingSize.XLarge, glowColor)
                AvatarSize.XXLarge -> activityRingToken.activeBorderStroke(ActivityRingSize.XXLarge, glowColor)
            }
        else
            when (avatarInfo.size) {
                AvatarSize.XSmall -> activityRingToken.inactiveBorderStroke(ActivityRingSize.XSmall)
                AvatarSize.Small -> activityRingToken.inactiveBorderStroke(ActivityRingSize.Small)
                AvatarSize.Medium -> activityRingToken.inactiveBorderStroke(ActivityRingSize.Medium)
                AvatarSize.Large -> activityRingToken.inactiveBorderStroke(ActivityRingSize.Large)
                AvatarSize.XLarge -> activityRingToken.inactiveBorderStroke(ActivityRingSize.XLarge)
                AvatarSize.XXLarge -> activityRingToken.inactiveBorderStroke(ActivityRingSize.XXLarge)
            }
    }

    @Composable
    private fun calculatedColor(avatarString: String, token: GlobalTokens.SharedColorsTokens): Color {
        val colors = listOf(
                GlobalTokens.SharedColorSets.DarkRed,
                GlobalTokens.SharedColorSets.Cranberry,
                GlobalTokens.SharedColorSets.Red,
                GlobalTokens.SharedColorSets.Pumpkin,
                GlobalTokens.SharedColorSets.Peach,
                GlobalTokens.SharedColorSets.Marigold,
                GlobalTokens.SharedColorSets.Gold,
                GlobalTokens.SharedColorSets.Brass,
                GlobalTokens.SharedColorSets.Brown,
                GlobalTokens.SharedColorSets.Forest,
                GlobalTokens.SharedColorSets.Seafoam,
                GlobalTokens.SharedColorSets.DarkGreen,
                GlobalTokens.SharedColorSets.LightTeal,
                GlobalTokens.SharedColorSets.Teal,
                GlobalTokens.SharedColorSets.Steel,
                GlobalTokens.SharedColorSets.Blue,
                GlobalTokens.SharedColorSets.RoyalBlue,
                GlobalTokens.SharedColorSets.Cornflower,
                GlobalTokens.SharedColorSets.Navy,
                GlobalTokens.SharedColorSets.Lavender,
                GlobalTokens.SharedColorSets.Purple,
                GlobalTokens.SharedColorSets.Grape,
                GlobalTokens.SharedColorSets.Lilac,
                GlobalTokens.SharedColorSets.Pink,
                GlobalTokens.SharedColorSets.Magenta,
                GlobalTokens.SharedColorSets.Plum,
                GlobalTokens.SharedColorSets.Beige,
                GlobalTokens.SharedColorSets.Mink,
                GlobalTokens.SharedColorSets.Platinum,
                GlobalTokens.SharedColorSets.Anchor
        )

        return GlobalTokens.sharedColors(colors[abs(avatarString.hashCode()) % colors.size], token)
    }
}

@Parcelize
open class ActivityRingsToken : Parcelable {
    @Composable
    open fun inactiveBorderStroke(activityRingSize: ActivityRingSize): List<BorderStroke> {
        return when (activityRingSize) {
            ActivityRingSize.XSmall -> listOf(
                    BorderStroke(
                            GlobalTokens.borderSize(GlobalTokens.BorderSizeTokens.Thin),
                            aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                                    themeMode = themeMode
                            )
                    )
            )
            ActivityRingSize.Small -> listOf(
                    BorderStroke(
                            GlobalTokens.borderSize(GlobalTokens.BorderSizeTokens.Thin),
                            aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                                    themeMode = themeMode
                            )
                    )
            )
            ActivityRingSize.Medium -> listOf(
                    BorderStroke(
                            GlobalTokens.borderSize(GlobalTokens.BorderSizeTokens.Thick),
                            aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                                    themeMode = themeMode
                            )
                    )
            )
            ActivityRingSize.Large -> listOf(
                    BorderStroke(
                            GlobalTokens.borderSize(GlobalTokens.BorderSizeTokens.Thick),
                            aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                                    themeMode = themeMode
                            )
                    )
            )
            ActivityRingSize.XLarge -> listOf(
                    BorderStroke(
                            GlobalTokens.borderSize(GlobalTokens.BorderSizeTokens.Thick),
                            aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                                    themeMode = themeMode
                            )
                    )
            )
            ActivityRingSize.XXLarge -> listOf(
                    BorderStroke(
                            GlobalTokens.borderSize(GlobalTokens.BorderSizeTokens.Thicker),
                            aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                                    themeMode = themeMode
                            )
                    )
            )
        }
    }

    @Composable
    open fun activeBorderStroke(activityRingSize: ActivityRingSize, glowColor: Color): List<BorderStroke> {
        return when (activityRingSize) {
            ActivityRingSize.XSmall -> listOf(
                    BorderStroke(
                            GlobalTokens.borderSize(GlobalTokens.BorderSizeTokens.Thin),
                            aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                                    themeMode = themeMode
                            )
                    ),
                    BorderStroke(
                            GlobalTokens.borderSize(GlobalTokens.BorderSizeTokens.Thick),
                            glowColor
                    ),
                    BorderStroke(
                            GlobalTokens.borderSize(GlobalTokens.BorderSizeTokens.Thick),
                            aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                                    themeMode = themeMode
                            )
                    )
            )
            ActivityRingSize.Small -> listOf(
                    BorderStroke(
                            GlobalTokens.borderSize(GlobalTokens.BorderSizeTokens.Thin),
                            aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                                    themeMode = themeMode
                            )
                    ),
                    BorderStroke(
                            GlobalTokens.borderSize(GlobalTokens.BorderSizeTokens.Thick),
                            glowColor
                    ),
                    BorderStroke(
                            GlobalTokens.borderSize(GlobalTokens.BorderSizeTokens.Thick),
                            aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                                    themeMode = themeMode
                            )
                    )
            )
            ActivityRingSize.Medium -> listOf(
                    BorderStroke(
                            GlobalTokens.borderSize(GlobalTokens.BorderSizeTokens.Thick),
                            aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                                    themeMode = themeMode
                            )
                    ),
                    BorderStroke(
                            GlobalTokens.borderSize(GlobalTokens.BorderSizeTokens.Thick),
                            glowColor
                    ),
                    BorderStroke(
                            GlobalTokens.borderSize(GlobalTokens.BorderSizeTokens.Thick),
                            aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                                    themeMode = themeMode
                            )
                    )
            )
            ActivityRingSize.Large -> listOf(
                    BorderStroke(
                            GlobalTokens.borderSize(GlobalTokens.BorderSizeTokens.Thick),
                            aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                                    themeMode = themeMode
                            )
                    ),
                    BorderStroke(
                            GlobalTokens.borderSize(GlobalTokens.BorderSizeTokens.Thick),
                            glowColor
                    ),
                    BorderStroke(
                            GlobalTokens.borderSize(GlobalTokens.BorderSizeTokens.Thick),
                            aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                                    themeMode = themeMode
                            )
                    )
            )
            ActivityRingSize.XLarge -> listOf(
                    BorderStroke(
                            GlobalTokens.borderSize(GlobalTokens.BorderSizeTokens.Thick),
                            aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                                    themeMode = themeMode
                            )
                    ),
                    BorderStroke(
                            GlobalTokens.borderSize(GlobalTokens.BorderSizeTokens.Thick),
                            glowColor
                    ),
                    BorderStroke(
                            GlobalTokens.borderSize(GlobalTokens.BorderSizeTokens.Thick),
                            aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                                    themeMode = themeMode
                            )
                    )
            )
            ActivityRingSize.XXLarge -> listOf(
                    BorderStroke(
                            GlobalTokens.borderSize(GlobalTokens.BorderSizeTokens.Thicker),
                            aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                                    themeMode = themeMode
                            )
                    ),
                    BorderStroke(
                            GlobalTokens.borderSize(GlobalTokens.BorderSizeTokens.Thicker),
                            glowColor
                    ),
                    BorderStroke(
                            GlobalTokens.borderSize(GlobalTokens.BorderSizeTokens.Thicker),
                            aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                                    themeMode = themeMode
                            )
                    )
            )
        }
    }
}