package com.microsoft.fluentui.icons.avataricons.presence.offline.medium

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.icons.avataricons.presence.offline.MediumGroup

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
            path(fill = SolidColor(Color(0xFF979593)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(10.0303f, 5.9697f)
                curveTo(10.3232f, 6.2626f, 10.3232f, 6.7374f, 10.0303f, 7.0303f)
                lineTo(9.0606f, 8.0f)
                lineTo(10.0303f, 8.9697f)
                curveTo(10.3232f, 9.2626f, 10.3232f, 9.7375f, 10.0303f, 10.0304f)
                curveTo(9.7374f, 10.3233f, 9.2626f, 10.3233f, 8.9697f, 10.0304f)
                lineTo(8.0f, 9.0607f)
                lineTo(7.0303f, 10.0303f)
                curveTo(6.7374f, 10.3232f, 6.2626f, 10.3232f, 5.9697f, 10.0303f)
                curveTo(5.6768f, 9.7374f, 5.6768f, 9.2626f, 5.9697f, 8.9697f)
                lineTo(6.9393f, 8.0f)
                lineTo(5.9697f, 7.0304f)
                curveTo(5.6768f, 6.7375f, 5.6768f, 6.2626f, 5.9697f, 5.9697f)
                curveTo(6.2626f, 5.6768f, 6.7374f, 5.6768f, 7.0303f, 5.9697f)
                lineTo(8.0f, 6.9394f)
                lineTo(8.9697f, 5.9697f)
                curveTo(9.2626f, 5.6768f, 9.7374f, 5.6768f, 10.0303f, 5.9697f)
                close()
                moveTo(2.0f, 8.0f)
                curveTo(2.0f, 4.6863f, 4.6863f, 2.0f, 8.0f, 2.0f)
                curveTo(11.3137f, 2.0f, 14.0f, 4.6863f, 14.0f, 8.0f)
                curveTo(14.0f, 11.3137f, 11.3137f, 14.0f, 8.0f, 14.0f)
                curveTo(4.6863f, 14.0f, 2.0f, 11.3137f, 2.0f, 8.0f)
                close()
                moveTo(8.0f, 3.5f)
                curveTo(5.5147f, 3.5f, 3.5f, 5.5147f, 3.5f, 8.0f)
                curveTo(3.5f, 10.4853f, 5.5147f, 12.5f, 8.0f, 12.5f)
                curveTo(10.4853f, 12.5f, 12.5f, 10.4853f, 12.5f, 8.0f)
                curveTo(12.5f, 5.5147f, 10.4853f, 3.5f, 8.0f, 3.5f)
                close()
            }
        }
                .build()
        return _dark!!
    }

private var _dark: ImageVector? = null
