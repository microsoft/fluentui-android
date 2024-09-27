package com.microsoft.fluentui.tokenized.persona

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.FluentTheme.themeMode
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.*

// Tags used for testing
const val AVATAR_IMAGE = "Fluent Avatar Image"
const val AVATAR_ICON = "Fluent Avatar Icon"

/**
 * API to generate an avatar for a [Person]. Avatar behavior uses person image(if available),
 * Initials generated from First and Last name of Person(if provided)
 * or Anonymous Icons otherwise (as set in AvatarTokens).
 *
 * @param person Data Class for the person whose Avatar is to be generated.
 * @param modifier Optional Modifier for avatar
 * @param size Set Size of Avatar. Default: [AvatarSize.Size32]
 * @param enableActivityRings Enable/Disable Activity Rings on Avatar
 * @param enablePresence Enable/Disable Presence Indicator on Avatar, if cutout is provided then presence indicator is not displayed
 * @param cutoutIconDrawable cutout drawable
 * @param cutoutIconImageVector cutout image vector
 * @param cutoutStyle shape of the cutout. Default: [CutoutStyle.Circle]
 * @param cutoutContentDescription accessibility description for the cutout
 * @param avatarToken Token to provide appearance values to Avatar
 */
@Composable
fun Avatar(
    person: Person,
    modifier: Modifier = Modifier,
    size: AvatarSize = AvatarSize.Size32,
    enableActivityRings: Boolean = false,
    enablePresence: Boolean = true,
    @DrawableRes cutoutIconDrawable: Int? = null,
    cutoutIconImageVector: ImageVector? = null,
    cutoutStyle: CutoutStyle = CutoutStyle.Circle,
    cutoutContentDescription: String? = null,
    avatarToken: AvatarTokens? = null
) {

    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = avatarToken
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.AvatarControlType] as AvatarTokens

    val personInitials = person.getInitials()
    val avatarInfo = AvatarInfo(
        size, AvatarType.Person, person.isActive,

        person.status, person.isOOO, person.isImageAvailable(),
        personInitials.isNotEmpty(), person.getName(), cutoutStyle
    )
    val avatarSize = token.avatarSize(avatarInfo)
    val backgroundColor = token.backgroundBrush(avatarInfo)
    val foregroundColor = token.foregroundColor(avatarInfo)
    val borders = token.borderStroke(avatarInfo)
    val fontTextStyle = token.fontTypography(avatarInfo)
    val cutoutCornerRadius = token.cutoutCornerRadius(avatarInfo)
    val cutoutBackgroundColor =
        token.cutoutBackgroundColor(avatarInfo = avatarInfo)
    val cutoutBorderColor = token.cutoutBorderColor(avatarInfo = avatarInfo)
    val cutoutIconSize = token.cutoutIconSize(avatarInfo = avatarInfo)
    val isCutoutEnabled = (cutoutIconDrawable != null || cutoutIconImageVector != null)
    var isImageOrInitialsAvailable = true

    Box(modifier = Modifier
        .semantics(mergeDescendants = true) {
            contentDescription = "${person.getName()}. " +
                    "${if (enablePresence) "Status, ${person.status}," else ""} " +
                    "${if (enablePresence && person.isOOO) "Out Of Office," else ""} " +
                    if (enableActivityRings) {
                        if (person.isActive) "Active" else "Inactive"
                    } else ""
        }
    ) {
        Box(
            Modifier
                .then(modifier)
                .requiredSize(avatarSize)
                .background(backgroundColor, CircleShape), contentAlignment = Alignment.Center
        ) {
            when {
                person.image != null -> {
                    Image(
                        painter = painterResource(person.image), null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(avatarSize)
                            .clip(CircleShape)
                            .semantics {
                                testTag = AVATAR_IMAGE
                            }
                    )
                }
                person.bitmap != null -> {
                    Image(
                        bitmap = person.bitmap.asImageBitmap(), null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(avatarSize)
                            .clip(CircleShape)
                            .semantics {
                                testTag = AVATAR_IMAGE
                            }
                    )
                }
                personInitials.isNotEmpty() -> {
                    BasicText(personInitials,
                        style = fontTextStyle.merge(
                            TextStyle(color = foregroundColor)
                        ),
                        modifier = Modifier
                            .clearAndSetSemantics { })
                }
                else -> {
                    isImageOrInitialsAvailable = false
                    Icon(
                        token.icon(avatarInfo),
                        null,
                        modifier = Modifier
                            .background(backgroundColor, CircleShape)
                            .semantics {
                                testTag = AVATAR_ICON
                            },
                        tint = foregroundColor,
                    )
                }
            }

            if (enableActivityRings)
                ActivityRing(radius = avatarSize / 2, borders)

            if (isCutoutEnabled && isImageOrInitialsAvailable && cutoutIconSize > 0.dp) {
                Box(
                    modifier = Modifier
                        .offset(6.dp, 6.dp)
                        .align(Alignment.BottomEnd)
                        .clip(shape = RoundedCornerShape(size = cutoutCornerRadius))
                ) {
                    if (cutoutIconDrawable != null) {
                        Image(
                            painter = painterResource(cutoutIconDrawable),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .background(cutoutBackgroundColor)
                                .border(
                                    2.dp,
                                    cutoutBorderColor,
                                    RoundedCornerShape(cutoutCornerRadius)
                                )
                                .padding(4.dp)
                                .size(cutoutIconSize),
                            contentDescription = cutoutContentDescription
                        )
                    } else if (cutoutIconImageVector != null) {
                        Image(
                            imageVector = cutoutIconImageVector,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .background(cutoutBackgroundColor)
                                .border(
                                    2.dp,
                                    cutoutBorderColor,
                                    RoundedCornerShape(cutoutCornerRadius)
                                )
                                .padding(4.dp)
                                .size(cutoutIconSize),
                            contentDescription = cutoutContentDescription
                        )
                    }
                }
            }

            if (!isCutoutEnabled && enablePresence) {
                val presenceOffset: DpOffset = token.presenceOffset(avatarInfo)
                val image: FluentIcon = token.presenceIcon(avatarInfo)
                Image(
                    image.value(themeMode),
                    null,
                    Modifier
                        .align(Alignment.BottomEnd)
                        // Adding 2.dp to both side to incorporate border which is an image in Fluent Android.
                        .offset(presenceOffset.x + 2.dp, -presenceOffset.y + 2.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

/**
 * API to generate an avatar for a [Group]. Avatar behavior uses group image(if available),
 * Initials generated from Group Name(if provided)
 * or Anonymous Icons otherwise (as set in AvatarTokens).
 *
 * @param group Data Class for the person whose Avatar is to be generated.
 * @param modifier Optional Modifier for avatar
 * @param size Set Size of Avatar. Default: [AvatarSize.Size32]
 * @param avatarToken Token to provide appearance values to Avatar
 */
@Composable
fun Avatar(
    group: Group,
    modifier: Modifier = Modifier,
    size: AvatarSize = AvatarSize.Size32,
    avatarToken: AvatarTokens? = null,
) {

    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = avatarToken
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.AvatarControlType] as AvatarTokens

    val avatarInfo = AvatarInfo(
        size, AvatarType.Group,
        isImageAvailable = group.isImageAvailable(),
        hasValidInitials = group.getInitials().isNotEmpty(),
        calculatedColorKey = group.groupName
    )
    val avatarSize = token.avatarSize(avatarInfo)
    val cornerRadius = token.cornerRadius(avatarInfo)
    val fontTextStyle = token.fontTypography(avatarInfo)
    val backgroundColor = token.backgroundBrush(avatarInfo)
    val foregroundColor = token.foregroundColor(avatarInfo)

    var membersList = ""
    for (person in group.members)
        membersList += (person.firstName + person.lastName + "\n")

    Box(
        modifier
            .requiredSize(avatarSize)
            .semantics(mergeDescendants = false) {
                contentDescription =
                    "Group Name ${group.getName()} ${group.members.size} members. $membersList"
            }, contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .clip(RoundedCornerShape(cornerRadius))
                .background(backgroundColor)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (group.image != null) {
                Image(
                    painter = painterResource(group.image),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .size(avatarSize)
                        .clip(RoundedCornerShape(cornerRadius))
                        .semantics {
                            testTag = AVATAR_IMAGE
                        }
                )
            } else if (group.bitmap != null) {
                Image(
                    bitmap = group.bitmap.asImageBitmap(),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .size(avatarSize)
                        .clip(RoundedCornerShape(cornerRadius))
                        .semantics {
                            testTag = AVATAR_IMAGE
                        }
                )
            } else if (group.groupName.isNotEmpty()) {
                BasicText(group.getInitials(),
                    style = fontTextStyle.merge(TextStyle(color = foregroundColor)),
                    modifier = Modifier.clearAndSetSemantics { })
            } else {
                Icon(
                    token.icon(avatarInfo),
                    null,
                    modifier = Modifier.semantics {
                        testTag = AVATAR_ICON
                    },
                    tint = foregroundColor
                )
            }
        }
    }
}

/**
 * API to create an overflow avatar to depict overflow count.
 *
 * @param overflowCount Magnitude of overflow
 * @param modifier Optional modifier for Overflow avatar
 * @param size Set Size of Avatar. Default: [AvatarSize. Medium]
 * @param enableActivityRings Enable/Disable Activity Rings on Avatar
 * @param avatarToken Token to provide appearance values to Avatar
 */
@Composable
fun Avatar(
    overflowCount: Int,
    modifier: Modifier = Modifier,
    size: AvatarSize = AvatarSize.Size32,
    enableActivityRings: Boolean = false,
    avatarToken: AvatarTokens? = null
) {
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = avatarToken
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.AvatarControlType] as AvatarTokens

    val avatarInfo = AvatarInfo(size, AvatarType.Overflow)
    val avatarSize = token.avatarSize(avatarInfo)
    val borders = token.borderStroke(avatarInfo)
    val fontTextStyle = token.fontTypography(avatarInfo)

    Box(
        modifier
            .requiredSize(avatarSize)
            .semantics(mergeDescendants = false) {
                contentDescription = "+ $overflowCount Avatar More"
            }, contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .clip(CircleShape)
                .background(token.backgroundBrush(avatarInfo))
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            BasicText("+${overflowCount}",
                style = fontTextStyle.merge(TextStyle(color = token.foregroundColor(avatarInfo))),
                modifier = Modifier.clearAndSetSemantics { })
        }

        if (enableActivityRings)
            ActivityRing(radius = avatarSize / 2, borders)
    }
}

@Composable
fun ActivityRing(radius: Dp, borders: List<BorderStroke>) {
    val firstBorderMid = with(LocalDensity.current) { borders[0].width.toPx() / 2 }
    val radiusPx = with(LocalDensity.current) { radius.toPx() }
    Canvas(Modifier) {
        var ringRadius = radiusPx - firstBorderMid + 1
        var ringStroke: Float
        for (border in borders) {
            ringStroke = border.width.toPx()
            drawCircle(border.brush, ringRadius, style = Stroke(ringStroke))
            ringRadius += ringStroke
        }
    }
}
