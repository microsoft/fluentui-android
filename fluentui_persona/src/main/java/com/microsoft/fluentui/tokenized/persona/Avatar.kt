package com.microsoft.fluentui.tokenized.persona

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.FluentTheme.themeMode
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.controlTokens.*

val LocalAvatarTokens = compositionLocalOf { AvatarTokens() }
val LocalAvatarInfo = compositionLocalOf { AvatarInfo() }

const val IMAGE_TEST_TAG = "Image"
const val ICON_TEST_TAG = "Icon"

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

    val token = avatarToken
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Avatar] as AvatarTokens

    val personInitials = person.getInitials()

    CompositionLocalProvider(
        LocalAvatarTokens provides token,
        LocalAvatarInfo provides AvatarInfo(
            size, AvatarType.Person, person.isActive,

            person.status, person.isOOO, person.isImageAvailable(),
            personInitials.isNotEmpty(), person.getName(), cutoutStyle
        )
    ) {
        val avatarSize = getAvatarTokens().avatarSize(getAvatarInfo())
        val backgroundColor = getAvatarTokens().backgroundColor(getAvatarInfo())
        val foregroundColor = getAvatarTokens().foregroundColor(getAvatarInfo())
        val borders = getAvatarTokens().borderStroke(getAvatarInfo())
        val fontTextStyle = getAvatarTokens().fontTypography(getAvatarInfo())
        val cutoutCornerRadius = getAvatarTokens().cutoutCornerRadius(getAvatarInfo())
        val cutoutBackgroundColor =
            getAvatarTokens().cutoutBackgroundColor(avatarInfo = getAvatarInfo())
        val cutoutBorderColor = getAvatarTokens().cutoutBorderColor(avatarInfo = getAvatarInfo())
        val cutoutIconSize = getAvatarTokens().cutoutIconSize(avatarInfo = getAvatarInfo())
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
                            modifier = Modifier
                                .size(avatarSize)
                                .clip(CircleShape)
                                .semantics {
                                    testTag = IMAGE_TEST_TAG
                                }
                        )
                    }
                    person.imageBitmap != null -> {
                        Image(
                            bitmap = person.imageBitmap, null,
                            modifier = Modifier
                                .size(avatarSize)
                                .clip(CircleShape)
                                .semantics {
                                    testTag = IMAGE_TEST_TAG
                                }
                        )
                    }
                    personInitials.isNotEmpty() -> {
                        Text(personInitials,
                            style = fontTextStyle,
                            color = foregroundColor,
                            modifier = Modifier
                                .clearAndSetSemantics { })
                    }
                    else -> {
                        isImageOrInitialsAvailable = false
                        Icon(
                            getAvatarTokens().icon(getAvatarInfo()),
                            null,
                            modifier = Modifier
                                .background(backgroundColor, CircleShape)
                                .semantics {
                                    testTag = ICON_TEST_TAG
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
                    val presenceOffset: DpOffset = getAvatarTokens().presenceOffset(getAvatarInfo())
                    val image: FluentIcon = getAvatarTokens().presenceIcon(getAvatarInfo())
                    Image(
                        image.value(themeMode),
                        null,
                        Modifier
                            .align(Alignment.TopStart)
                            .offset(presenceOffset.x, presenceOffset.y)
                    )
                }
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

    val token = avatarToken
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Avatar] as AvatarTokens

    CompositionLocalProvider(
        LocalAvatarTokens provides token,
        LocalAvatarInfo provides AvatarInfo(
            size, AvatarType.Group,
            isImageAvailable = group.isImageAvailable(),
            hasValidInitials = group.getInitials().isNotEmpty(),
            calculatedColorKey = group.groupName
        )
    ) {
        val avatarSize = getAvatarTokens().avatarSize(getAvatarInfo())
        val cornerRadius = getAvatarTokens().cornerRadius(getAvatarInfo())
        val fontTextStyle = getAvatarTokens().fontTypography(getAvatarInfo())
        val backgroundColor = getAvatarTokens().backgroundColor(getAvatarInfo())
        val foregroundColor = getAvatarTokens().foregroundColor(getAvatarInfo())

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
                        contentDescription = null,
                        modifier = Modifier
                            .size(avatarSize)
                            .clip(RoundedCornerShape(cornerRadius))
                            .semantics {
                                testTag = IMAGE_TEST_TAG
                            }
                    )
                } else if (group.imageBitmap != null) {
                    Image(
                        bitmap = group.imageBitmap,
                        contentDescription = null,
                        modifier = Modifier
                            .size(avatarSize)
                            .clip(RoundedCornerShape(cornerRadius))
                            .semantics {
                                testTag = IMAGE_TEST_TAG
                            }
                    )
                } else if (group.groupName.isNotEmpty()) {
                    Text(group.getInitials(),
                        style = fontTextStyle,
                        color = foregroundColor,
                        modifier = Modifier.clearAndSetSemantics { })
                } else {
                    Icon(
                        getAvatarTokens().icon(getAvatarInfo()),
                        null,
                        modifier = Modifier.semantics {
                            testTag = ICON_TEST_TAG
                        },
                        tint = foregroundColor
                    )
                }
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
    val token = avatarToken
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Avatar] as AvatarTokens

    CompositionLocalProvider(
        LocalAvatarTokens provides token,
        LocalAvatarInfo provides AvatarInfo(size, AvatarType.Overflow)
    ) {
        val avatarSize = getAvatarTokens().avatarSize(getAvatarInfo())
        val borders = getAvatarTokens().borderStroke(getAvatarInfo())
        val fontTextStyle = getAvatarTokens().fontTypography(getAvatarInfo())

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
                    .background(getAvatarTokens().backgroundColor(getAvatarInfo()))
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("+${overflowCount}",
                    style = fontTextStyle,
                    color = getAvatarTokens().foregroundColor(getAvatarInfo()),
                    modifier = Modifier.clearAndSetSemantics { })
            }

            if (enableActivityRings)
                ActivityRing(radius = avatarSize / 2, borders)
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
