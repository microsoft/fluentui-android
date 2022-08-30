package com.microsoft.fluentui.icons.avataricons.presence.offline.large

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.icons.avataricons.presence.offline.LargeGroup

val LargeGroup.Dark: ImageVector
    get() {
        if (_dark != null) {
            return _dark!!
        }
        _dark = Builder(name = "Dark", defaultWidth = 20.0.dp, defaultHeight = 20.0.dp,
                viewportWidth = 20.0f, viewportHeight = 20.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 2.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(10.0f, 10.0f)
                moveToRelative(-9.0f, 0.0f)
                arcToRelative(9.0f, 9.0f, 0.0f, true, true, 18.0f, 0.0f)
                arcToRelative(9.0f, 9.0f, 0.0f, true, true, -18.0f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF979593)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(12.7071f, 7.2929f)
                curveTo(13.0976f, 7.6834f, 13.0976f, 8.3166f, 12.7071f, 8.7071f)
                lineTo(11.4142f, 10.0f)
                lineTo(12.7071f, 11.2929f)
                curveTo(13.0976f, 11.6834f, 13.0976f, 12.3166f, 12.7071f, 12.7071f)
                curveTo(12.3166f, 13.0976f, 11.6834f, 13.0976f, 11.2929f, 12.7071f)
                lineTo(10.0f, 11.4142f)
                lineTo(8.7071f, 12.7071f)
                curveTo(8.3166f, 13.0976f, 7.6834f, 13.0976f, 7.2929f, 12.7071f)
                curveTo(6.9024f, 12.3166f, 6.9024f, 11.6834f, 7.2929f, 11.2929f)
                lineTo(8.5858f, 10.0f)
                lineTo(7.2929f, 8.7071f)
                curveTo(6.9024f, 8.3166f, 6.9024f, 7.6834f, 7.2929f, 7.2929f)
                curveTo(7.6834f, 6.9024f, 8.3166f, 6.9024f, 8.7071f, 7.2929f)
                lineTo(10.0f, 8.5858f)
                lineTo(11.2929f, 7.2929f)
                curveTo(11.6834f, 6.9024f, 12.3166f, 6.9024f, 12.7071f, 7.2929f)
                close()
                moveTo(2.0f, 10.0f)
                curveTo(2.0f, 5.5817f, 5.5817f, 2.0f, 10.0f, 2.0f)
                curveTo(14.4183f, 2.0f, 18.0f, 5.5817f, 18.0f, 10.0f)
                curveTo(18.0f, 14.4183f, 14.4183f, 18.0f, 10.0f, 18.0f)
                curveTo(5.5817f, 18.0f, 2.0f, 14.4183f, 2.0f, 10.0f)
                close()
                moveTo(10.0f, 4.0f)
                curveTo(6.6863f, 4.0f, 4.0f, 6.6863f, 4.0f, 10.0f)
                curveTo(4.0f, 13.3137f, 6.6863f, 16.0f, 10.0f, 16.0f)
                curveTo(13.3137f, 16.0f, 16.0f, 13.3137f, 16.0f, 10.0f)
                curveTo(16.0f, 6.6863f, 13.3137f, 4.0f, 10.0f, 4.0f)
                close()
            }
        }
                .build()
        return _dark!!
    }

private var _dark: ImageVector? = null
