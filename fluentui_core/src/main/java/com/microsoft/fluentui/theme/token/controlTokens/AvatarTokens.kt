package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
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

data class AvatarInfo(
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
    ControlToken, Parcelable {

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
                fontWeight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Regular)
            )
            AvatarSize.Size20 -> TextStyle(
                fontSize = 9.sp,
                lineHeight = 12.sp,
                fontWeight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Regular)
            )
            AvatarSize.Size24 -> TextStyle(
                fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size100),
                lineHeight = GlobalTokens.lineHeight(GlobalTokens.LineHeightTokens.Size100),
                fontWeight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Regular)
            )
            AvatarSize.Size32 -> TextStyle(
                fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size200),
                lineHeight = GlobalTokens.lineHeight(GlobalTokens.LineHeightTokens.Size200),
                fontWeight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Regular)
            )
            AvatarSize.Size40 -> TextStyle(
                fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size300),
                lineHeight = GlobalTokens.lineHeight(GlobalTokens.LineHeightTokens.Size300),
                fontWeight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Regular)
            )
            AvatarSize.Size56 -> TextStyle(
                fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size500),
                lineHeight = GlobalTokens.lineHeight(GlobalTokens.LineHeightTokens.Size500),
                fontWeight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Medium)
            )
            AvatarSize.Size72 -> TextStyle(
                fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size700),
                lineHeight = GlobalTokens.lineHeight(GlobalTokens.LineHeightTokens.Size700),
                fontWeight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Medium)
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
            AvatarStyle.Standard, AvatarStyle.StandardInverted ->
                when (avatarInfo.size) {
                    AvatarSize.Size16 -> AvatarIcons.Icon.Standard.Xsmall
                    AvatarSize.Size20 -> AvatarIcons.Icon.Standard.Small
                    AvatarSize.Size24 -> AvatarIcons.Icon.Standard.Small
                    AvatarSize.Size32 -> AvatarIcons.Icon.Standard.Medium
                    AvatarSize.Size40 -> AvatarIcons.Icon.Standard.Large
                    AvatarSize.Size56 -> AvatarIcons.Icon.Standard.Xlarge
                    AvatarSize.Size72 -> AvatarIcons.Icon.Standard.Xxlarge
                }

            AvatarStyle.Anonymous, AvatarStyle.AnonymousAccent ->
                when (avatarInfo.size) {
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
                    avatarInfo.calculatedColorKey,
                    GlobalTokens.SharedColorsTokens.Shade30
                ),
                dark = calculatedColor(
                    avatarInfo.calculatedColorKey,
                    GlobalTokens.SharedColorsTokens.Tint40
                )
            ).value(
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
            FluentColor(
                light = calculatedColor(
                    avatarInfo.calculatedColorKey,
                    GlobalTokens.SharedColorsTokens.Tint40
                ),
                dark = calculatedColor(
                    avatarInfo.calculatedColorKey,
                    GlobalTokens.SharedColorsTokens.Shade30
                )
            ).value(
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
                    aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackgroundTint].value(
                        themeMode = themeMode
                    )
            }
        }
    }

    @Composable
    open fun presenceIcon(avatarInfo: AvatarInfo): FluentIcon {
        return when (avatarInfo.status) {
            AvatarStatus.Available ->
                when (avatarInfo.size) {
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

            AvatarStatus.Busy ->
                when (avatarInfo.size) {
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

            AvatarStatus.Away ->
                when (avatarInfo.size) {
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

            AvatarStatus.DND ->
                when (avatarInfo.size) {
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

            AvatarStatus.Unknown ->
                when (avatarInfo.size) {
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

            AvatarStatus.Blocked ->
                when (avatarInfo.size) {
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

            AvatarStatus.Offline ->
                when (avatarInfo.size) {
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
    open fun presenceOffset(avatarInfo: AvatarInfo): DpOffset {
        return when (avatarInfo.size) {
            AvatarSize.Size16 -> DpOffset(0.dp, 0.dp)
            AvatarSize.Size20 -> DpOffset(11.dp, 13.dp)
            AvatarSize.Size24 -> DpOffset(11.dp, 13.dp)
            AvatarSize.Size32 -> DpOffset(20.dp, 20.dp)
            AvatarSize.Size40 -> DpOffset(26.dp, 26.dp)
            AvatarSize.Size56 -> DpOffset(40.dp, 40.dp)
            AvatarSize.Size72 -> DpOffset(51.dp, 51.dp)
        }
    }

    @Composable
    open fun cornerRadius(avatarInfo: AvatarInfo): Dp {
        return when (avatarInfo.size) {
            AvatarSize.Size16 -> GlobalTokens.cornerRadius(GlobalTokens.CornerRadiusTokens.CornerRadius20)
            AvatarSize.Size20 -> GlobalTokens.cornerRadius(GlobalTokens.CornerRadiusTokens.CornerRadius40)
            AvatarSize.Size24 -> GlobalTokens.cornerRadius(GlobalTokens.CornerRadiusTokens.CornerRadius40)
            AvatarSize.Size32 -> GlobalTokens.cornerRadius(GlobalTokens.CornerRadiusTokens.CornerRadius40)
            AvatarSize.Size40 -> GlobalTokens.cornerRadius(GlobalTokens.CornerRadiusTokens.CornerRadius80)
            AvatarSize.Size56 -> GlobalTokens.cornerRadius(GlobalTokens.CornerRadiusTokens.CornerRadius80)
            AvatarSize.Size72 -> GlobalTokens.cornerRadius(GlobalTokens.CornerRadiusTokens.CornerRadius120)
        }
    }

    @Composable
    open fun borderStroke(avatarInfo: AvatarInfo): List<BorderStroke> {
        val glowColor: Color = if (avatarInfo.isImageAvailable || avatarInfo.hasValidInitials) {
            FluentColor(
                light = calculatedColor(
                    avatarInfo.calculatedColorKey,
                    GlobalTokens.SharedColorsTokens.Primary
                ),
                dark = calculatedColor(
                    avatarInfo.calculatedColorKey,
                    GlobalTokens.SharedColorsTokens.Tint30
                )
            ).value(
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
                AvatarSize.Size16 -> activityRingToken.activeBorderStroke(
                    ActivityRingSize.Size16,
                    glowColor
                )
                AvatarSize.Size20 -> activityRingToken.activeBorderStroke(
                    ActivityRingSize.Size20,
                    glowColor
                )
                AvatarSize.Size24 -> activityRingToken.activeBorderStroke(
                    ActivityRingSize.Size24,
                    glowColor
                )
                AvatarSize.Size32 -> activityRingToken.activeBorderStroke(
                    ActivityRingSize.Size32,
                    glowColor
                )
                AvatarSize.Size40 -> activityRingToken.activeBorderStroke(
                    ActivityRingSize.Size40,
                    glowColor
                )
                AvatarSize.Size56 -> activityRingToken.activeBorderStroke(
                    ActivityRingSize.Size56,
                    glowColor
                )
                AvatarSize.Size72 -> activityRingToken.activeBorderStroke(
                    ActivityRingSize.Size72,
                    glowColor
                )
            }
        else
            when (avatarInfo.size) {
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
    open fun cutoutCornerRadius(avatarInfo: AvatarInfo): Dp {
        return when (avatarInfo.cutoutStyle) {
            CutoutStyle.Circle -> GlobalTokens.cornerRadius(GlobalTokens.CornerRadiusTokens.CornerRadiusCircle)
            CutoutStyle.Square -> GlobalTokens.cornerRadius(GlobalTokens.CornerRadiusTokens.CornerRadius40)
        }
    }

    @Composable
    open fun cutoutBackgroundColor(avatarInfo: AvatarInfo): Color {
        return aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background5].value(
            themeMode = themeMode
        )
    }

    @Composable
    open fun cutoutBorderColor(avatarInfo: AvatarInfo): Color {
        return aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
            themeMode = themeMode
        )
    }

    @Composable
    open fun cutoutIconSize(avatarInfo: AvatarInfo): Dp {
        return when (avatarInfo.size) {
            AvatarSize.Size16, AvatarSize.Size20, AvatarSize.Size24, AvatarSize.Size32, AvatarSize.Size72 -> 0.dp
            AvatarSize.Size40 -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.IconSize160)
            AvatarSize.Size56 -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.IconSize240)
        }
    }

    @Composable
    private fun calculatedColor(
        avatarString: String,
        token: GlobalTokens.SharedColorsTokens
    ): Color {
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
            ActivityRingSize.Size16 -> listOf(
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth15),
                    aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                )
            )
            ActivityRingSize.Size20 -> listOf(
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth20),
                    aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                )
            )
            ActivityRingSize.Size24 -> listOf(
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth20),
                    aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                )
            )
            ActivityRingSize.Size32 -> listOf(
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth20),
                    aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                )
            )
            ActivityRingSize.Size40 -> listOf(
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth20),
                    aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                )
            )
            ActivityRingSize.Size56 -> listOf(
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth20),
                    aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                )
            )
            ActivityRingSize.Size72 -> listOf(
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth40),
                    aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                )
            )
        }
    }

    @Composable
    open fun activeBorderStroke(
        activityRingSize: ActivityRingSize,
        glowColor: Color
    ): List<BorderStroke> {
        return when (activityRingSize) {
            ActivityRingSize.Size16 -> listOf(
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth15),
                    aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                ),
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth15),
                    glowColor
                ),
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth15),
                    aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                )
            )
            ActivityRingSize.Size20 -> listOf(
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth20),
                    aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                ),
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth15),
                    glowColor
                ),
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth20),
                    aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                )
            )
            ActivityRingSize.Size24 -> listOf(
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth20),
                    aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                ),
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth20),
                    glowColor
                ),
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth20),
                    aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                )
            )
            ActivityRingSize.Size32 -> listOf(
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth20),
                    aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                ),
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth20),
                    glowColor
                ),
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth20),
                    aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                )
            )
            ActivityRingSize.Size40 -> listOf(
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth20),
                    aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                ),
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth20),
                    glowColor
                ),
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth20),
                    aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                )
            )
            ActivityRingSize.Size56 -> listOf(
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth20),
                    aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                ),
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth20),
                    glowColor
                ),
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth20),
                    aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                )
            )
            ActivityRingSize.Size72 -> listOf(
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth40),
                    aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                ),
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth40),
                    glowColor
                ),
                BorderStroke(
                    GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth40),
                    aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                        themeMode = themeMode
                    )
                )
            )
        }
    }
}