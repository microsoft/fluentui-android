package com.microsoft.fluentui.tokenised.persona

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.FluentTheme.themeMode
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.AvatarImageNA
import com.microsoft.fluentui.theme.token.controlTokens.AvatarInfo
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize
import com.microsoft.fluentui.theme.token.controlTokens.AvatarTokens

val LocalAvatarTokens = compositionLocalOf { AvatarTokens() }
val LocalAvatarInfo = compositionLocalOf { AvatarInfo() }

@Composable
fun Avatar(person: Person,
           size: AvatarSize = AvatarSize.Medium,
           imageNAStyle: AvatarImageNA = AvatarImageNA.Standard,
           modifier: Modifier = Modifier,
           avatarToken: AvatarTokens? = null,
           enableActivityRings: Boolean = false,
           enablePresence: Boolean = true
) {

    val token = avatarToken
            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Avatar] as AvatarTokens

    CompositionLocalProvider(
            LocalAvatarTokens provides token,
            LocalAvatarInfo provides AvatarInfo(size, imageNAStyle, person.isActive, person.status, person.isOOO)
    ) {
        val avatarSize = getAvatarTokens().avatarSize(getAvatarInfo())
        val borders = getAvatarTokens().borderStroke(getAvatarInfo())
        val fontSize = getAvatarTokens().fontSize(getAvatarInfo())

        Box(modifier.requiredSize(avatarSize), contentAlignment = Alignment.Center) {
            Box(Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(getAvatarTokens().backgroundColor(getAvatarInfo())),
                    contentAlignment = Alignment.Center
            ) {
                if (person.image != null) {
                    Image(
                            painter = painterResource(person.image),
                            contentDescription = person.firstName + person.lastName,
                            modifier = Modifier
                                    .size(avatarSize)
                                    .clip(CircleShape)
                    )
                } else if (person.imageBitmap != null) {
                    Image(
                            bitmap = person.imageBitmap,
                            contentDescription = person.firstName + person.lastName,
                            modifier = Modifier
                                    .size(avatarSize)
                                    .clip(CircleShape)
                    )
                } else if (imageNAStyle == AvatarImageNA.Initials) {
                    Text(person.getInitials(), fontSize = with(LocalDensity.current) { fontSize.toSp() },
                            color = Color(0xFF6E0911))
                } else {
                    Icon(
                            getAvatarTokens().icon(getAvatarInfo())!!,
                            "${person.firstName} Anonymous",
                            tint = getAvatarTokens().iconColor(getAvatarInfo()),
                    )
                }
            }

            if (enableActivityRings)
                ActivityRing(radius = with(LocalDensity.current) { avatarSize.toPx() / 2 }, borders)

            Box(Modifier.fillMaxSize()) {
                var presenceOffset: DpOffset = getAvatarTokens().presenceOffset(getAvatarInfo())
                var image: Icon = getAvatarTokens().presenceIcon(getAvatarInfo())
                if (enablePresence)
                    Image(image.value(themeMode), "Status", Modifier.offset(presenceOffset.x, presenceOffset.y))
            }
        }
    }
}

@Composable
fun Avatar(
        group: Group,
        size: AvatarSize = AvatarSize.Medium,
        imageNAStyle: AvatarImageNA = AvatarImageNA.Standard,
        modifier: Modifier = Modifier,
        avatarToken: AvatarTokens? = null,
) {

    val token = avatarToken
            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Avatar] as AvatarTokens

    CompositionLocalProvider(
            LocalAvatarTokens provides token,
            LocalAvatarInfo provides AvatarInfo(size, imageNAStyle, false)
    ) {
        val avatarSize = getAvatarTokens().avatarSize(getAvatarInfo())
        val bordersRadius = getAvatarTokens().borderRadius(getAvatarInfo())
        val fontSize = getAvatarTokens().fontSize(getAvatarInfo())


        Box(modifier.requiredSize(avatarSize), contentAlignment = Alignment.Center
        ) {
            Box(Modifier
                    .clip(RoundedCornerShape(bordersRadius))
                    .background(getAvatarTokens().backgroundColor(getAvatarInfo()))
                    .fillMaxSize(),
                    contentAlignment = Alignment.Center
            ) {
                if (group.image != null) {
                    Image(
                            painter = painterResource(group.image),
                            contentDescription = group.groupName,
                            modifier = Modifier
                                    .size(avatarSize)
                                    .clip(RoundedCornerShape(bordersRadius))
                    )
                } else if (group.imageBitmap != null) {
                    Image(
                            bitmap = group.imageBitmap,
                            contentDescription = group.groupName,
                            modifier = Modifier
                                    .size(avatarSize)
                                    .clip(RoundedCornerShape(bordersRadius))
                    )
                } else if (imageNAStyle == AvatarImageNA.Initials) {
                    Text(group.getInitials(), fontSize = with(LocalDensity.current) { fontSize.toSp() },
                            color = Color(0xFF6E0911))
                } else {
                    Icon(
                            getAvatarTokens().icon(getAvatarInfo()),
                            "${group.groupName} Anonymous",
                            tint = getAvatarTokens().iconColor(getAvatarInfo())
                    )
                }
            }
        }
    }
}

@Composable
fun Avatar(overflowCount: Int,
           size: AvatarSize = AvatarSize.Medium,
           modifier: Modifier = Modifier,
           avatarToken: AvatarTokens? = null,
           enableActivityRings: Boolean = false
) {
    val token = avatarToken
            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Avatar] as AvatarTokens

    CompositionLocalProvider(
            LocalAvatarTokens provides token,
            LocalAvatarInfo provides AvatarInfo(size, AvatarImageNA.Initials, false)
    ) {
        val avatarSize = getAvatarTokens().avatarSize(getAvatarInfo())
        val borders = getAvatarTokens().borderStroke(getAvatarInfo())
        val fontSize = getAvatarTokens().fontSize(getAvatarInfo())

        Box(modifier.requiredSize(avatarSize), contentAlignment = Alignment.Center
        ) {
            Box(Modifier
                    .clip(CircleShape)
                    .background(getAvatarTokens().backgroundColor(getAvatarInfo()))
                    .fillMaxSize(),
                    contentAlignment = Alignment.Center
            ) {
                Text("+${overflowCount}", fontSize = with(LocalDensity.current) { fontSize.toSp() },
                        color = Color(0xFF6E0911))
            }

            if (enableActivityRings)
                ActivityRing(radius = with(LocalDensity.current) { avatarSize.toPx() / 2 }, borders)
        }
    }
}

@Composable
fun getAvatarTokens(): AvatarTokens {
    return LocalAvatarTokens.current
}

@Composable
fun getAvatarInfo(): AvatarInfo {
    return LocalAvatarInfo.current
}

@Composable
fun ActivityRing(radius: Float, borders: List<BorderStroke>) {
    val firstBorderMid = with(LocalDensity.current) { borders[0].width.toPx() / 2 }
    Canvas(Modifier) {
        var ringRadius = radius - firstBorderMid
        var ringStroke: Float = 0F
        for (border in borders) {
            ringStroke = border.width.toPx()
            drawCircle(border.brush, ringRadius, style = Stroke(ringStroke))
            ringRadius += ringStroke
        }
    }
}