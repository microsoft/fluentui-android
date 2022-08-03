package com.microsoft.fluentui.icons.avataricons.presence.availableoof.large

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.icons.avataricons.presence.availableoof.LargeGroup

val LargeGroup.Light: ImageVector
    get() {
        if (_light != null) {
            return _light!!
        }
        _light = Builder(name = "Light", defaultWidth = 20.0.dp, defaultHeight = 20.0.dp,
                viewportWidth = 20.0f, viewportHeight = 20.0f).apply {
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(10.0f, 1.0f)
                lineTo(10.0f, 1.0f)
                arcTo(9.0f, 9.0f, 0.0f, false, true, 19.0f, 10.0f)
                lineTo(19.0f, 10.0f)
                arcTo(9.0f, 9.0f, 0.0f, false, true, 10.0f, 19.0f)
                lineTo(10.0f, 19.0f)
                arcTo(9.0f, 9.0f, 0.0f, false, true, 1.0f, 10.0f)
                lineTo(1.0f, 10.0f)
                arcTo(9.0f, 9.0f, 0.0f, false, true, 10.0f, 1.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFFffffff)),
                    strokeLineWidth = 2.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(10.0f, 10.0f)
                moveToRelative(-9.0f, 0.0f)
                arcToRelative(9.0f, 9.0f, 0.0f, true, true, 18.0f, 0.0f)
                arcToRelative(9.0f, 9.0f, 0.0f, true, true, -18.0f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF6BB700)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(13.7071f, 8.7071f)
                curveTo(14.0976f, 8.3166f, 14.0976f, 7.6834f, 13.7071f, 7.2929f)
                curveTo(13.3166f, 6.9024f, 12.6834f, 6.9024f, 12.2929f, 7.2929f)
                lineTo(9.0f, 10.5858f)
                lineTo(7.7071f, 9.2929f)
                curveTo(7.3166f, 8.9024f, 6.6834f, 8.9024f, 6.2929f, 9.2929f)
                curveTo(5.9024f, 9.6834f, 5.9024f, 10.3166f, 6.2929f, 10.7071f)
                lineTo(8.2929f, 12.7071f)
                curveTo(8.6834f, 13.0976f, 9.3166f, 13.0976f, 9.7071f, 12.7071f)
                lineTo(13.7071f, 8.7071f)
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
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
                    strokeLineWidth = 2.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(10.0f, 1.0f)
                lineTo(10.0f, 1.0f)
                arcTo(9.0f, 9.0f, 0.0f, false, true, 19.0f, 10.0f)
                lineTo(19.0f, 10.0f)
                arcTo(9.0f, 9.0f, 0.0f, false, true, 10.0f, 19.0f)
                lineTo(10.0f, 19.0f)
                arcTo(9.0f, 9.0f, 0.0f, false, true, 1.0f, 10.0f)
                lineTo(1.0f, 10.0f)
                arcTo(9.0f, 9.0f, 0.0f, false, true, 10.0f, 1.0f)
                close()
            }
        }
                .build()
        return _light!!
    }

private var _light: ImageVector? = null
