package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.microsoft.fluentui.theme.FluentTheme
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
    Size16,
    Size20,
    Size24,
    Size32,
    Size40,
    Size56,
    Size72
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
    Size16,
    Size20,
    Size24,
    Size32,
    Size40,
    Size56,
    Size72
}

enum class CutoutStyle {
    Square,
    Circle
}

open class AvatarInfo(
    val size: AvatarSize = AvatarSize.Size40,
    val type: AvatarType = AvatarType.Person,
    val active: Boolean = false,
    val status: AvatarStatus = AvatarStatus.Available,
    val isOOO: Boolean = false,
    val isImageAvailable: Boolean = false,
    val hasValidInitials: Boolean = false,
    val calculatedColorKey: String = "",
    val cutoutStyle: CutoutStyle = CutoutStyle.Circle
) : ControlInfo

@Parcelize
open class AvatarTokens(private val activityRingToken: ActivityRingsToken = ActivityRingsToken()) :
    IControlToken, Parcelable {

    @Composable
    open fun avatarStyle(avatarInfo: AvatarInfo): AvatarStyle {
        return AvatarStyle.Standard
    }

    @Composable
    open fun fontTypography(avatarInfo: AvatarInfo): TextStyle {
        return when (avatarInfo.size) {
            AvatarSize.Size16 -> TextStyle(
                fontSize = 9.sp,
                lineHeight = 12.sp,
                fontWeight = FluentGlobalTokens.FontWeightTokens.Regular.value
            )

            AvatarSize.Size20 -> TextStyle(
                fontSize = 9.sp,
                lineHeight = 12.sp,
                fontWeight = FluentGlobalTokens.FontWeightTokens.Regular.value
            )

            AvatarSize.Size24 -> TextStyle(
                fontSize = FluentGlobalTokens.FontSizeTokens.Size100.value,
                lineHeight = FluentGlobalTokens.LineHeightTokens.Size100.value,
                fontWeight = FluentGlobalTokens.FontWeightTokens.Regular.value
            )

            AvatarSize.Size32 -> TextStyle(
                fontSize = FluentGlobalTokens.FontSizeTokens.Size200.value,
                lineHeight = FluentGlobalTokens.LineHeightTokens.Size200.value,
                fontWeight = FluentGlobalTokens.FontWeightTokens.Regular.value
            )

            AvatarSize.Size40 -> TextStyle(
                fontSize = FluentGlobalTokens.FontSizeTokens.Size300.value,
                lineHeight = FluentGlobalTokens.LineHeightTokens.Size300.value,
                fontWeight = FluentGlobalTokens.FontWeightTokens.Regular.value
            )

            AvatarSize.Size56 -> TextStyle(
                fontSize = FluentGlobalTokens.FontSizeTokens.Size500.value,
                lineHeight = FluentGlobalTokens.LineHeightTokens.Size500.value,
                fontWeight = FluentGlobalTokens.FontWeightTokens.Medium.value
            )

            AvatarSize.Size72 -> TextStyle(
                fontSize = FluentGlobalTokens.FontSizeTokens.Size700.value,
                lineHeight = FluentGlobalTokens.LineHeightTokens.Size700.value,
                fontWeight = FluentGlobalTokens.FontWeightTokens.Medium.value
            )
        }
    }

    @Composable
    open fun avatarSize(avatarInfo: AvatarInfo): Dp {
        return when (avatarInfo.size) {
            AvatarSize.Size16 -> 16.dp
            AvatarSize.Size20 -> 24.dp
            AvatarSize.Size24 -> 24.dp
            AvatarSize.Size32 -> 32.dp
            AvatarSize.Size40 -> 40.dp
            AvatarSize.Size56 -> 56.dp
            AvatarSize.Size72 -> 72.dp
        }
    }

    @Composable
    open fun icon(avatarInfo: AvatarInfo): ImageVector {
        return when (avatarStyle(avatarInfo)) {
            AvatarStyle.Standard, AvatarStyle.StandardInverted -> when (avatarInfo.size) {
                AvatarSize.Size16 -> AvatarIcons.Icon.Standard.Xsmall
                AvatarSize.Size20 -> AvatarIcons.Icon.Standard.Small
                AvatarSize.Size24 -> AvatarIcons.Icon.Standard.Small
                AvatarSize.Size32 -> AvatarIcons.Icon.Standard.Medium
                AvatarSize.Size40 -> AvatarIcons.Icon.Standard.Large
                AvatarSize.Size56 -> AvatarIcons.Icon.Standard.Xlarge
                AvatarSize.Size72 -> AvatarIcons.Icon.Standard.Xxlarge
            }

            AvatarStyle.Anonymous, AvatarStyle.AnonymousAccent -> when (avatarInfo.size) {
                AvatarSize.Size16 -> AvatarIcons.Icon.Anonymous.Xsmall
                AvatarSize.Size20 -> AvatarIcons.Icon.Anonymous.Small
                AvatarSize.Size24 -> AvatarIcons.Icon.Anonymous.Small
                AvatarSize.Size32 -> AvatarIcons.Icon.Anonymous.Medium
                AvatarSize.Size40 -> AvatarIcons.Icon.Anonymous.Large
                AvatarSize.Size56 -> AvatarIcons.Icon.Anonymous.Xlarge
                AvatarSize.Size72 -> AvatarIcons.Icon.Anonymous.Xxlarge
            }
        }
    }

    @Composable
    open fun foregroundColor(avatarInfo: AvatarInfo): Color {
        return if (avatarInfo.isImageAvailable || avatarInfo.hasValidInitials) {
            FluentColor(
                light = calculatedColor(
                    avatarInfo.calculatedColorKey, FluentGlobalTokens.SharedColorsTokens.Shade30
                ), dark = calculatedColor(
                    avatarInfo.calculatedColorKey, FluentGlobalTokens.SharedColorsTokens.Tint40
                )
            ).value(
                themeMode = themeMode
            )
        } else if (avatarInfo.type == AvatarType.Overflow) {
            aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                themeMode = themeMode
            )
        } else {
            when (avatarStyle(avatarInfo)) {
                AvatarStyle.Standard -> aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                    themeMode = themeMode
                )

                AvatarStyle.StandardInverted -> aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
                    themeMode = themeMode
                )

                AvatarStyle.Anonymous -> aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                    themeMode = themeMode
                )

                AvatarStyle.AnonymousAccent -> aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
                    themeMode = themeMode
                )
            }
        }
    }

    @Composable
    open fun backgroundBrush(avatarInfo: AvatarInfo): Brush {
        return SolidColor(
            if (avatarInfo.isImageAvailable || avatarInfo.hasValidInitials) {
                FluentColor(
                    light = calculatedColor(
                        avatarInfo.calculatedColorKey, FluentGlobalTokens.SharedColorsTokens.Tint40
                    ), dark = calculatedColor(
                        avatarInfo.calculatedColorKey, FluentGlobalTokens.SharedColorsTokens.Shade30
                    )
                ).value(
                    themeMode = themeMode
                )
            } else if (avatarInfo.type == AvatarType.Overflow) {
                aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background5].value(
                    themeMode = themeMode
                )
            } else {
                when (avatarStyle(avatarInfo)) {
                    AvatarStyle.Standard -> aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                        themeMode = themeMode
                    )

                    AvatarStyle.StandardInverted -> aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )

                    AvatarStyle.Anonymous -> aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background5].value(
                        themeMode = themeMode
                    )

                    AvatarStyle.AnonymousAccent -> aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackgroundTint].value(
                        themeMode = themeMode
                    )
                }
            }
        )
    }

    @Composable
    open fun presenceIcon(avatarInfo: AvatarInfo): FluentIcon {
        return when (avatarInfo.status) {
            AvatarStatus.Available -> when (avatarInfo.size) {
                AvatarSize.Size16 -> FluentIcon()
                AvatarSize.Size20 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Availableoof.Small.Light else AvatarIcons.Presence.Available.Small.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Availableoof.Small.Dark else AvatarIcons.Presence.Available.Small.Dark
                )

                AvatarSize.Size24 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Availableoof.Small.Light else AvatarIcons.Presence.Available.Small.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Availableoof.Small.Dark else AvatarIcons.Presence.Available.Small.Dark
                )

                AvatarSize.Size32 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Availableoof.Small.Light else AvatarIcons.Presence.Available.Small.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Availableoof.Small.Dark else AvatarIcons.Presence.Available.Small.Dark
                )

                AvatarSize.Size40 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Availableoof.Medium.Light else AvatarIcons.Presence.Available.Medium.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Availableoof.Medium.Dark else AvatarIcons.Presence.Available.Medium.Dark
                )

                AvatarSize.Size56 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Availableoof.Medium.Light else AvatarIcons.Presence.Available.Medium.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Availableoof.Medium.Dark else AvatarIcons.Presence.Available.Medium.Dark
                )

                AvatarSize.Size72 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Availableoof.Large.Light else AvatarIcons.Presence.Available.Large.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Availableoof.Large.Dark else AvatarIcons.Presence.Available.Large.Dark
                )
            }

            AvatarStatus.Busy -> when (avatarInfo.size) {
                AvatarSize.Size16 -> FluentIcon()
                AvatarSize.Size20 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Busyoof.Small.Light else AvatarIcons.Presence.Busy.Small.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Busyoof.Small.Dark else AvatarIcons.Presence.Busy.Small.Dark
                )

                AvatarSize.Size24 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Busyoof.Small.Light else AvatarIcons.Presence.Busy.Small.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Busyoof.Small.Dark else AvatarIcons.Presence.Busy.Small.Dark
                )

                AvatarSize.Size32 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Busyoof.Small.Light else AvatarIcons.Presence.Busy.Small.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Busyoof.Small.Dark else AvatarIcons.Presence.Busy.Small.Dark
                )

                AvatarSize.Size40 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Busyoof.Medium.Light else AvatarIcons.Presence.Busy.Medium.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Busyoof.Medium.Dark else AvatarIcons.Presence.Busy.Medium.Dark
                )

                AvatarSize.Size56 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Busyoof.Medium.Light else AvatarIcons.Presence.Busy.Medium.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Busyoof.Medium.Dark else AvatarIcons.Presence.Busy.Medium.Dark
                )

                AvatarSize.Size72 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Busyoof.Large.Light else AvatarIcons.Presence.Busy.Large.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Busyoof.Large.Dark else AvatarIcons.Presence.Busy.Large.Dark
                )
            }

            AvatarStatus.Away -> when (avatarInfo.size) {
                AvatarSize.Size16 -> FluentIcon()
                AvatarSize.Size20 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Awayoof.Small.Light else AvatarIcons.Presence.Away.Small.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Awayoof.Small.Dark else AvatarIcons.Presence.Away.Small.Dark
                )

                AvatarSize.Size24 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Awayoof.Small.Light else AvatarIcons.Presence.Away.Small.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Awayoof.Small.Dark else AvatarIcons.Presence.Away.Small.Dark
                )

                AvatarSize.Size32 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Awayoof.Small.Light else AvatarIcons.Presence.Away.Small.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Awayoof.Small.Dark else AvatarIcons.Presence.Away.Small.Dark
                )

                AvatarSize.Size40 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Awayoof.Medium.Light else AvatarIcons.Presence.Away.Medium.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Awayoof.Medium.Dark else AvatarIcons.Presence.Away.Medium.Dark
                )

                AvatarSize.Size56 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Awayoof.Medium.Light else AvatarIcons.Presence.Away.Medium.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Awayoof.Medium.Dark else AvatarIcons.Presence.Away.Medium.Dark
                )

                AvatarSize.Size72 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Awayoof.Large.Light else AvatarIcons.Presence.Away.Large.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Awayoof.Large.Dark else AvatarIcons.Presence.Away.Large.Dark
                )
            }

            AvatarStatus.DND -> when (avatarInfo.size) {
                AvatarSize.Size16 -> FluentIcon()
                AvatarSize.Size20 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Dndoof.Small.Light else AvatarIcons.Presence.Dnd.Small.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Dndoof.Small.Dark else AvatarIcons.Presence.Dnd.Small.Dark
                )

                AvatarSize.Size24 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Dndoof.Small.Light else AvatarIcons.Presence.Dnd.Small.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Dndoof.Small.Dark else AvatarIcons.Presence.Dnd.Small.Dark
                )

                AvatarSize.Size32 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Dndoof.Small.Light else AvatarIcons.Presence.Dnd.Small.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Dndoof.Small.Dark else AvatarIcons.Presence.Dnd.Small.Dark
                )

                AvatarSize.Size40 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Dndoof.Medium.Light else AvatarIcons.Presence.Dnd.Medium.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Dndoof.Medium.Dark else AvatarIcons.Presence.Dnd.Medium.Dark
                )

                AvatarSize.Size56 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Dndoof.Medium.Light else AvatarIcons.Presence.Dnd.Medium.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Dndoof.Medium.Dark else AvatarIcons.Presence.Dnd.Medium.Dark
                )

                AvatarSize.Size72 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Dndoof.Large.Light else AvatarIcons.Presence.Dnd.Large.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Dndoof.Large.Dark else AvatarIcons.Presence.Dnd.Large.Dark
                )
            }

            AvatarStatus.Unknown -> when (avatarInfo.size) {
                AvatarSize.Size16 -> FluentIcon()
                AvatarSize.Size20 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Light else AvatarIcons.Presence.Unknown.Small.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Dark else AvatarIcons.Presence.Unknown.Small.Dark
                )

                AvatarSize.Size24 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Light else AvatarIcons.Presence.Unknown.Small.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Dark else AvatarIcons.Presence.Unknown.Small.Dark
                )

                AvatarSize.Size32 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Light else AvatarIcons.Presence.Unknown.Small.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Dark else AvatarIcons.Presence.Unknown.Small.Dark
                )

                AvatarSize.Size40 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Medium.Light else AvatarIcons.Presence.Unknown.Medium.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Medium.Dark else AvatarIcons.Presence.Unknown.Medium.Dark
                )

                AvatarSize.Size56 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Medium.Light else AvatarIcons.Presence.Unknown.Medium.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Medium.Dark else AvatarIcons.Presence.Unknown.Medium.Dark
                )

                AvatarSize.Size72 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Large.Light else AvatarIcons.Presence.Unknown.Large.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Large.Dark else AvatarIcons.Presence.Unknown.Large.Dark
                )
            }

            AvatarStatus.Blocked -> when (avatarInfo.size) {
                AvatarSize.Size16 -> FluentIcon()
                AvatarSize.Size20 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Light else AvatarIcons.Presence.Blocked.Small.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Dark else AvatarIcons.Presence.Blocked.Small.Dark
                )

                AvatarSize.Size24 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Light else AvatarIcons.Presence.Blocked.Small.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Dark else AvatarIcons.Presence.Blocked.Small.Dark
                )

                AvatarSize.Size32 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Light else AvatarIcons.Presence.Blocked.Small.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Dark else AvatarIcons.Presence.Blocked.Small.Dark
                )

                AvatarSize.Size40 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Medium.Light else AvatarIcons.Presence.Blocked.Medium.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Medium.Dark else AvatarIcons.Presence.Blocked.Medium.Dark
                )

                AvatarSize.Size56 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Medium.Light else AvatarIcons.Presence.Blocked.Medium.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Medium.Dark else AvatarIcons.Presence.Blocked.Medium.Dark
                )

                AvatarSize.Size72 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Large.Light else AvatarIcons.Presence.Blocked.Large.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Large.Dark else AvatarIcons.Presence.Blocked.Large.Dark
                )
            }

            AvatarStatus.Offline -> when (avatarInfo.size) {
                AvatarSize.Size16 -> FluentIcon()
                AvatarSize.Size20 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Light else AvatarIcons.Presence.Offline.Small.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Dark else AvatarIcons.Presence.Offline.Small.Dark
                )

                AvatarSize.Size24 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Light else AvatarIcons.Presence.Offline.Small.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Dark else AvatarIcons.Presence.Offline.Small.Dark
                )

                AvatarSize.Size32 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Light else AvatarIcons.Presence.Offline.Small.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Small.Dark else AvatarIcons.Presence.Offline.Small.Dark
                )

                AvatarSize.Size40 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Medium.Light else AvatarIcons.Presence.Offline.Medium.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Medium.Dark else AvatarIcons.Presence.Offline.Medium.Dark
                )

                AvatarSize.Size56 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Medium.Light else AvatarIcons.Presence.Offline.Medium.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Medium.Dark else AvatarIcons.Presence.Offline.Medium.Dark
                )

                AvatarSize.Size72 -> FluentIcon(
                    light = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Large.Light else AvatarIcons.Presence.Offline.Large.Light,
                    dark = if (avatarInfo.isOOO) AvatarIcons.Presence.Oof.Large.Dark else AvatarIcons.Presence.Offline.Large.Dark
                )
            }
        }
    }

    @Composable
    open fun unreadDotBorderStroke(avatarInfo: AvatarInfo): BorderStroke {
        return BorderStroke(
            FluentGlobalTokens.StrokeWidthTokens.StrokeWidth20.value,
            aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.StrokeFocus1].value(
                themeMode = themeMode
            )
        )
    }

    @Composable
    open fun unreadDotBackgroundBrush(avatarInfo: AvatarInfo): Brush {
        return SolidColor(
            aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                themeMode = themeMode
            )
        )
    }

    @Composable
    open fun unreadDotSize(avatarInfo: AvatarInfo): Dp {
        return when (avatarInfo.size) {
            AvatarSize.Size16 -> 8.dp
            AvatarSize.Size20 -> 8.dp
            AvatarSize.Size24 -> 8.dp
            AvatarSize.Size32 -> 10.dp
            AvatarSize.Size40 -> 12.dp
            AvatarSize.Size56 -> 14.dp
            AvatarSize.Size72 -> 16.dp
        }
    }

    @Composable
    open fun unreadDotOffset(avatarInfo: AvatarInfo): DpOffset {
       return when(avatarInfo.size) {
              AvatarSize.Size16 -> DpOffset(4.dp, (0).dp)
              AvatarSize.Size20 -> DpOffset(4.dp, (-2).dp)
              AvatarSize.Size24 -> DpOffset(4.dp, (-3).dp)
              AvatarSize.Size32 -> DpOffset(4.dp, (-3).dp)
              AvatarSize.Size40 -> DpOffset(4.dp, (-3).dp)
              AvatarSize.Size56 -> DpOffset(4.dp, (-4).dp)
              AvatarSize.Size72 -> DpOffset(4.dp, (-5).dp)
       }
    }

    @Composable
    open fun presenceOffset(avatarInfo: AvatarInfo): DpOffset {
        return when (avatarInfo.size) {
            AvatarSize.Size16 -> DpOffset(0.dp, 0.dp)
            AvatarSize.Size20 -> DpOffset(0.dp, (-3).dp)
            AvatarSize.Size24 -> DpOffset((-1).dp, (-1).dp)
            AvatarSize.Size32 -> DpOffset(0.dp, 0.dp)
            AvatarSize.Size40 -> DpOffset(0.dp, 0.dp)
            AvatarSize.Size56 -> DpOffset((-2).dp, 2.dp)
            AvatarSize.Size72 -> DpOffset((-3).dp, 3.dp)
        }
    }

    @Composable
    open fun cornerRadius(avatarInfo: AvatarInfo): Dp {
        return when (avatarInfo.size) {
            AvatarSize.Size16 -> FluentGlobalTokens.CornerRadiusTokens.CornerRadius20.value
            AvatarSize.Size20 -> FluentGlobalTokens.CornerRadiusTokens.CornerRadius40.value
            AvatarSize.Size24 -> FluentGlobalTokens.CornerRadiusTokens.CornerRadius40.value
            AvatarSize.Size32 -> FluentGlobalTokens.CornerRadiusTokens.CornerRadius40.value
            AvatarSize.Size40 -> FluentGlobalTokens.CornerRadiusTokens.CornerRadius80.value
            AvatarSize.Size56 -> FluentGlobalTokens.CornerRadiusTokens.CornerRadius80.value
            AvatarSize.Size72 -> FluentGlobalTokens.CornerRadiusTokens.CornerRadius120.value
        }
    }

    @Composable
    open fun borderStroke(avatarInfo: AvatarInfo): List<BorderStroke> {
        val glowColor: Color = if (avatarInfo.isImageAvailable || avatarInfo.hasValidInitials) {
            FluentColor(
                light = calculatedColor(
                    avatarInfo.calculatedColorKey, FluentGlobalTokens.SharedColorsTokens.Primary
                ), dark = calculatedColor(
                    avatarInfo.calculatedColorKey, FluentGlobalTokens.SharedColorsTokens.Tint30
                )
            ).value(
                themeMode = themeMode
            )
        } else if (avatarInfo.type == AvatarType.Overflow) {
            aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.Stroke1].value(
                themeMode = themeMode
            )
        } else {
            when (avatarStyle(avatarInfo)) {
                AvatarStyle.Standard, AvatarStyle.StandardInverted, AvatarStyle.AnonymousAccent -> aliasTokens.brandStroke[FluentAliasTokens.BrandStrokeColorTokens.BrandStroke1].value(
                    themeMode = themeMode
                )

                AvatarStyle.Anonymous -> aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.Stroke1].value(
                    themeMode = themeMode
                )
            }
        }

        return if (avatarInfo.active) when (avatarInfo.size) {
            AvatarSize.Size16 -> activityRingToken.activeBorderStroke(
                ActivityRingSize.Size16, glowColor
            )

            AvatarSize.Size20 -> activityRingToken.activeBorderStroke(
                ActivityRingSize.Size20, glowColor
            )

            AvatarSize.Size24 -> activityRingToken.activeBorderStroke(
                ActivityRingSize.Size24, glowColor
            )

            AvatarSize.Size32 -> activityRingToken.activeBorderStroke(
                ActivityRingSize.Size32, glowColor
            )

            AvatarSize.Size40 -> activityRingToken.activeBorderStroke(
                ActivityRingSize.Size40, glowColor
            )

            AvatarSize.Size56 -> activityRingToken.activeBorderStroke(
                ActivityRingSize.Size56, glowColor
            )

            AvatarSize.Size72 -> activityRingToken.activeBorderStroke(
                ActivityRingSize.Size72, glowColor
            )
        }
        else when (avatarInfo.size) {
            AvatarSize.Size16 -> activityRingToken.inactiveBorderStroke(ActivityRingSize.Size16)
            AvatarSize.Size20 -> activityRingToken.inactiveBorderStroke(ActivityRingSize.Size20)
            AvatarSize.Size24 -> activityRingToken.inactiveBorderStroke(ActivityRingSize.Size24)
            AvatarSize.Size32 -> activityRingToken.inactiveBorderStroke(ActivityRingSize.Size32)
            AvatarSize.Size40 -> activityRingToken.inactiveBorderStroke(ActivityRingSize.Size40)
            AvatarSize.Size56 -> activityRingToken.inactiveBorderStroke(ActivityRingSize.Size56)
            AvatarSize.Size72 -> activityRingToken.inactiveBorderStroke(ActivityRingSize.Size72)
        }
    }

    @Composable
    open fun cutoutColorFilter(avatarInfo: AvatarInfo): ColorFilter? {
        return null
    }
    
    @Composable
    open fun cutoutCornerRadius(avatarInfo: AvatarInfo): Dp {
        return when (avatarInfo.cutoutStyle) {
            CutoutStyle.Circle -> FluentGlobalTokens.CornerRadiusTokens.CornerRadiusCircle.value
            CutoutStyle.Square -> FluentGlobalTokens.CornerRadiusTokens.CornerRadius40.value
        }
    }

    @Composable
    open fun cutoutBackgroundColor(avatarInfo: AvatarInfo): Color {
        return aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background5].value(
            themeMode = themeMode
        )
    }

    @Composable
    open fun cutoutBorderColor(avatarInfo: AvatarInfo): Color {
        return aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
            themeMode = themeMode
        )
    }

    @Composable
    open fun cutoutIconSize(avatarInfo: AvatarInfo): Dp {
        return when (avatarInfo.size) {
            AvatarSize.Size16, AvatarSize.Size20, AvatarSize.Size24, AvatarSize.Size32, AvatarSize.Size72 -> 0.dp
            AvatarSize.Size40 -> FluentGlobalTokens.IconSizeTokens.IconSize160.value
            AvatarSize.Size56 -> FluentGlobalTokens.IconSizeTokens.IconSize240.value
        }
    }

    @Composable
    private fun calculatedColor(
        avatarString: String, token: FluentGlobalTokens.SharedColorsTokens
    ): Color {
        val colors = listOf(
            FluentGlobalTokens.SharedColorSets.DarkRed,
            FluentGlobalTokens.SharedColorSets.Cranberry,
            FluentGlobalTokens.SharedColorSets.Red,
            FluentGlobalTokens.SharedColorSets.Pumpkin,
            FluentGlobalTokens.SharedColorSets.Peach,
            FluentGlobalTokens.SharedColorSets.Marigold,
            FluentGlobalTokens.SharedColorSets.Gold,
            FluentGlobalTokens.SharedColorSets.Brass,
            FluentGlobalTokens.SharedColorSets.Brown,
            FluentGlobalTokens.SharedColorSets.Forest,
            FluentGlobalTokens.SharedColorSets.Seafoam,
            FluentGlobalTokens.SharedColorSets.DarkGreen,
            FluentGlobalTokens.SharedColorSets.LightTeal,
            FluentGlobalTokens.SharedColorSets.Teal,
            FluentGlobalTokens.SharedColorSets.Steel,
            FluentGlobalTokens.SharedColorSets.Blue,
            FluentGlobalTokens.SharedColorSets.RoyalBlue,
            FluentGlobalTokens.SharedColorSets.Cornflower,
            FluentGlobalTokens.SharedColorSets.Navy,
            FluentGlobalTokens.SharedColorSets.Lavender,
            FluentGlobalTokens.SharedColorSets.Purple,
            FluentGlobalTokens.SharedColorSets.Grape,
            FluentGlobalTokens.SharedColorSets.Lilac,
            FluentGlobalTokens.SharedColorSets.Pink,
            FluentGlobalTokens.SharedColorSets.Magenta,
            FluentGlobalTokens.SharedColorSets.Plum,
            FluentGlobalTokens.SharedColorSets.Beige,
            FluentGlobalTokens.SharedColorSets.Mink,
            FluentGlobalTokens.SharedColorSets.Platinum,
            FluentGlobalTokens.SharedColorSets.Anchor
        )

        when (token) {
            FluentGlobalTokens.SharedColorsTokens.Primary -> {
                return colors[abs(avatarString.hashCode()) % colors.size].primary
            }

            FluentGlobalTokens.SharedColorsTokens.Tint10 -> {
                return colors[abs(avatarString.hashCode()) % colors.size].tint10
            }

            FluentGlobalTokens.SharedColorsTokens.Tint20 -> {
                return colors[abs(avatarString.hashCode()) % colors.size].tint20
            }

            FluentGlobalTokens.SharedColorsTokens.Tint30 -> {
                return colors[abs(avatarString.hashCode()) % colors.size].tint30
            }

            FluentGlobalTokens.SharedColorsTokens.Tint40 -> {
                return colors[abs(avatarString.hashCode()) % colors.size].tint40
            }

            FluentGlobalTokens.SharedColorsTokens.Tint50 -> {
                return colors[abs(avatarString.hashCode()) % colors.size].tint50
            }

            FluentGlobalTokens.SharedColorsTokens.Tint60 -> {
                return colors[abs(avatarString.hashCode()) % colors.size].tint60
            }

            FluentGlobalTokens.SharedColorsTokens.Shade10 -> {
                return colors[abs(avatarString.hashCode()) % colors.size].shade10
            }

            FluentGlobalTokens.SharedColorsTokens.Shade20 -> {
                return colors[abs(avatarString.hashCode()) % colors.size].shade20
            }

            FluentGlobalTokens.SharedColorsTokens.Shade30 -> {
                return colors[abs(avatarString.hashCode()) % colors.size].shade30
            }

            FluentGlobalTokens.SharedColorsTokens.Shade40 -> {
                return colors[abs(avatarString.hashCode()) % colors.size].shade40
            }

            FluentGlobalTokens.SharedColorsTokens.Shade50 -> {
                return colors[abs(avatarString.hashCode()) % colors.size].shade50
            }
        }
    }
}

@Parcelize
open class ActivityRingsToken : Parcelable {
    @Composable
    open fun inactiveBorderStroke(activityRingSize: ActivityRingSize): List<BorderStroke> {
        return when (activityRingSize) {
            ActivityRingSize.Size16 -> listOf(
                BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth15.value,
                    aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                )
            )

            ActivityRingSize.Size20 -> listOf(
                BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth20.value,
                    aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                )
            )

            ActivityRingSize.Size24 -> listOf(
                BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth20.value,
                    aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                )
            )

            ActivityRingSize.Size32 -> listOf(
                BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth20.value,
                    aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                )
            )

            ActivityRingSize.Size40 -> listOf(
                BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth20.value,
                    aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                )
            )

            ActivityRingSize.Size56 -> listOf(
                BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth20.value,
                    aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                )
            )

            ActivityRingSize.Size72 -> listOf(
                BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth40.value,
                    aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                )
            )
        }
    }

    @Composable
    open fun activeBorderStroke(
        activityRingSize: ActivityRingSize, glowColor: Color
    ): List<BorderStroke> {
        return when (activityRingSize) {
            ActivityRingSize.Size16 -> listOf(
                BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth15.value,
                    aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                ), BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth15.value, glowColor
                ), BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth15.value,
                    aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                )
            )

            ActivityRingSize.Size20 -> listOf(
                BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth20.value,
                    aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                ), BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth15.value, glowColor
                ), BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth20.value,
                    aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                )
            )

            ActivityRingSize.Size24 -> listOf(
                BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth20.value,
                    aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                ), BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth20.value, glowColor
                ), BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth20.value,
                    aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                )
            )

            ActivityRingSize.Size32 -> listOf(
                BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth20.value,
                    aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                ), BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth20.value, glowColor
                ), BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth20.value,
                    aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                )
            )

            ActivityRingSize.Size40 -> listOf(
                BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth20.value,
                    aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                ), BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth20.value, glowColor
                ), BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth20.value,
                    aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                )
            )

            ActivityRingSize.Size56 -> listOf(
                BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth20.value,
                    aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                ), BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth20.value, glowColor
                ), BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth20.value,
                    aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                )
            )

            ActivityRingSize.Size72 -> listOf(
                BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth40.value,
                    aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                ), BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth40.value, glowColor
                ), BorderStroke(
                    FluentGlobalTokens.StrokeWidthTokens.StrokeWidth40.value,
                    aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                )
            )
        }
    }
}