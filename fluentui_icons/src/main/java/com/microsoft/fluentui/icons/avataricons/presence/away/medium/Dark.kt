package com.microsoft.fluentui.icons.avataricons.presence.away.medium

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.icons.avataricons.presence.away.MediumGroup

val MediumGroup.Dark: ImageVector
    get() {
        if (_dark != null) {
            return _dark!!
        }
        _dark = Builder(name = "Dark", defaultWidth = 16.0.dp, defaultHeight = 16.0.dp,
                viewportWidth = 16.0f, viewportHeight = 16.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 2.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(8.0f, 8.0f)
                moveToRelative(-7.0f, 0.0f)
                arcToRelative(7.0f, 7.0f, 0.0f, true, true, 14.0f, 0.0f)
                arcToRelative(7.0f, 7.0f, 0.0f, true, true, -14.0f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFFF8D22A)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(8.0f, 14.0f)
                curveTo(11.3137f, 14.0f, 14.0f, 11.3137f, 14.0f, 8.0f)
                curveTo(14.0f, 4.6863f, 11.3137f, 2.0f, 8.0f, 2.0f)
                curveTo(4.6863f, 2.0f, 2.0f, 4.6863f, 2.0f, 8.0f)
                curveTo(2.0f, 11.3137f, 4.6863f, 14.0f, 8.0f, 14.0f)
                close()
                moveTo(8.5f, 5.25f)
                verticalLineTo(7.655f)
                lineTo(9.9881f, 8.9306f)
                curveTo(10.3026f, 9.2001f, 10.339f, 9.6736f, 10.0694f, 9.9881f)
                curveTo(9.7999f, 10.3026f, 9.3264f, 10.339f, 9.0119f, 10.0694f)
                lineTo(7.2619f, 8.5694f)
                curveTo(7.0957f, 8.427f, 7.0f, 8.2189f, 7.0f, 8.0f)
                verticalLineTo(5.25f)
                curveTo(7.0f, 4.8358f, 7.3358f, 4.5f, 7.75f, 4.5f)
                curveTo(8.1642f, 4.5f, 8.5f, 4.8358f, 8.5f, 5.25f)
                close()
            }
        }
                .build()
        return _dark!!
    }

private var _dark: ImageVector? = null
