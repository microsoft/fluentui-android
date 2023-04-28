package com.microsoft.fluentui.theme.token

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toolingGraphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.ThemeMode

data class FluentIcon(
    val light: ImageVector = ImageVector.Builder("", 0.dp, 0.dp, 0F, 0F).build(),
    val dark: ImageVector = light,
    val contentDescription: String? = null,
    val tint: Color? = null,
    val flipOnRtl: Boolean = false,
    val enabled: Boolean = true,
    val onClick: (() -> Unit)? = null
) {
    @Composable
    fun value(themeMode: ThemeMode = com.microsoft.fluentui.theme.FluentTheme.themeMode): ImageVector {
        return when (themeMode) {
            ThemeMode.Light -> light
            ThemeMode.Dark -> dark
            ThemeMode.Auto -> if (isSystemInDarkTheme()) dark else light
        }
    }

    fun isIconAvailable(): Boolean {
        return (this.light.defaultWidth > 0.dp && this.light.defaultHeight > 0.dp) ||
                (this.dark.defaultWidth > 0.dp && this.dark.defaultHeight > 0.dp)
    }
}

/**
 * Wrapper over Icon API to incorporate FluentIcon class.
 * Icon tint provided in FluentIcon override's the value provided in API.
 *
 * @param icon [FluentIcon] object to be displayed.
 * @param modifier Optional modifier for Icon.
 * @param tint Tint Color to be used if not provided in icon.
 */
@Composable
fun Icon(
    icon: FluentIcon,
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified,
) {
    Icon(
        rememberVectorPainter(icon.value()),
        icon.contentDescription,
        modifier = modifier,
        flipOnRtl = icon.flipOnRtl,
        tint = icon.tint ?: tint,
        enabled = icon.enabled,
        onClick = icon.onClick
    )
}

/**
 * Icon component that draws [imageVector] using [Icon].
 *
 * @param imageVector [ImageVector] to draw inside this Icon
 * @param contentDescription text used by accessibility services to describe what this icon
 * represents. This should always be provided unless this icon is used for decorative purposes,
 * and does not represent a meaningful action that a user can take. This text should be
 * localized.
 * @param modifier optional [Modifier] for this Icon
 * @param flipOnRtl Boolean to specify if the icon is directional and needs flipping on layoout change
 * @param tint tint to be applied to [painter]. If [Color.Unspecified] is provided, then no
 * tint is applied
 */
@Composable
@NonRestartableComposable
fun Icon(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    flipOnRtl: Boolean = false,
    tint: Color = Color.Unspecified,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null
) {
    Icon(
        painter = rememberVectorPainter(imageVector),
        contentDescription = contentDescription,
        modifier = modifier,
        flipOnRtl = flipOnRtl,
        tint = tint,
        enabled = enabled,
        onClick = onClick
    )
}

/**
 * Icon component that draws a [painter] using [tint].
 *
 * @param painter [Painter] to draw inside this Icon
 * @param contentDescription text used by accessibility services to describe what this icon
 * represents. This should always be provided unless this icon is used for decorative purposes,
 * and does not represent a meaningful action that a user can take. This text should be
 * localized.
 * @param modifier optional [Modifier] for this Icon
 * @param flipOnRtl Boolean to specify if the icon is directional and needs flipping on layoout change
 * @param tint tint to be applied to [painter]. If [Color.Unspecified] is provided, then no
 *  tint is applied
 */
@Composable
fun Icon(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    flipOnRtl: Boolean = false,
    tint: Color = Color.Unspecified,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null
) {
    val colorFilter = if (tint == Color.Unspecified) null else ColorFilter.tint(tint)
    val semantics = if (contentDescription != null) {
        Modifier.semantics {
            this.contentDescription = contentDescription
            this.role = Role.Image
        }
    } else {
        Modifier
    }

    val clickableModifier = Modifier.then(
        if (onClick != null) Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = LocalIndication.current,
            enabled = enabled,
            onClickLabel = contentDescription,
            onClick = onClick
        ) else Modifier
    )

    Box(
        clickableModifier
            .then(modifier)
            .toolingGraphicsLayer()
            .defaultSizeFor(painter)
            .paint(
                painter,
                colorFilter = colorFilter,
                contentScale = ContentScale.Fit
            )
            .then(
                if (flipOnRtl && LocalLayoutDirection.current == LayoutDirection.Rtl)
                    Modifier.scale(-1F, -1F)
                else
                    Modifier
            )
            .then(semantics)
    )
}

private fun Modifier.defaultSizeFor(painter: Painter) =
    this.then(
        if (painter.intrinsicSize == Size.Unspecified || painter.intrinsicSize.isInfinite()) {
            DefaultIconSizeModifier
        } else {
            Modifier
        }
    )

private fun Size.isInfinite() = width.isInfinite() && height.isInfinite()

// Default icon size, for icons with no intrinsic size information
private val DefaultIconSizeModifier = Modifier.size(24.dp)
