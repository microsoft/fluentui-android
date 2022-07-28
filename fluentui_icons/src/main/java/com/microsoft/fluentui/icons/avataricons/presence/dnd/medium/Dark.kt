package com.microsoft.fluentui.icons.avataricons.presence.dnd.medium

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.icons.avataricons.presence.dnd.MediumGroup

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
            path(fill = SolidColor(Color(0xFFD74553)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(8.0f, 14.0f)
                curveTo(11.3137f, 14.0f, 14.0f, 11.3137f, 14.0f, 8.0f)
                curveTo(14.0f, 4.6863f, 11.3137f, 2.0f, 8.0f, 2.0f)
                curveTo(4.6863f, 2.0f, 2.0f, 4.6863f, 2.0f, 8.0f)
                curveTo(2.0f, 11.3137f, 4.6863f, 14.0f, 8.0f, 14.0f)
                close()
                moveTo(5.75f, 7.25f)
                horizontalLineTo(10.25f)
                curveTo(10.6642f, 7.25f, 11.0f, 7.5858f, 11.0f, 8.0f)
                curveTo(11.0f, 8.4142f, 10.6642f, 8.75f, 10.25f, 8.75f)
                horizontalLineTo(5.75f)
                curveTo(5.3358f, 8.75f, 5.0f, 8.4142f, 5.0f, 8.0f)
                curveTo(5.0f, 7.5858f, 5.3358f, 7.25f, 5.75f, 7.25f)
                close()
            }
        }
                .build()
        return _dark!!
    }

private var _dark: ImageVector? = null
