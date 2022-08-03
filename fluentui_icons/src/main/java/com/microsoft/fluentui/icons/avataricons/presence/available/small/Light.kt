package com.microsoft.fluentui.icons.avataricons.presence.available.small

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.icons.avataricons.presence.available.SmallGroup

val SmallGroup.Light: ImageVector
    get() {
        if (_light != null) {
            return _light!!
        }
        _light = Builder(name = "Light", defaultWidth = 14.0.dp, defaultHeight = 14.0.dp,
                viewportWidth = 14.0f, viewportHeight = 14.0f).apply {
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(7.0f, 1.0f)
                lineTo(7.0f, 1.0f)
                arcTo(6.0f, 6.0f, 0.0f, false, true, 13.0f, 7.0f)
                lineTo(13.0f, 7.0f)
                arcTo(6.0f, 6.0f, 0.0f, false, true, 7.0f, 13.0f)
                lineTo(7.0f, 13.0f)
                arcTo(6.0f, 6.0f, 0.0f, false, true, 1.0f, 7.0f)
                lineTo(1.0f, 7.0f)
                arcTo(6.0f, 6.0f, 0.0f, false, true, 7.0f, 1.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFFffffff)),
                    strokeLineWidth = 2.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(7.0f, 7.0f)
                moveToRelative(-6.0f, 0.0f)
                arcToRelative(6.0f, 6.0f, 0.0f, true, true, 12.0f, 0.0f)
                arcToRelative(6.0f, 6.0f, 0.0f, true, true, -12.0f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF6BB700)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(7.0f, 12.0f)
                curveTo(9.7614f, 12.0f, 12.0f, 9.7614f, 12.0f, 7.0f)
                curveTo(12.0f, 4.2386f, 9.7614f, 2.0f, 7.0f, 2.0f)
                curveTo(4.2386f, 2.0f, 2.0f, 4.2386f, 2.0f, 7.0f)
                curveTo(2.0f, 9.7614f, 4.2386f, 12.0f, 7.0f, 12.0f)
                close()
                moveTo(9.1036f, 6.1035f)
                lineTo(6.8536f, 8.3536f)
                curveTo(6.6583f, 8.5488f, 6.3417f, 8.5488f, 6.1465f, 8.3536f)
                lineTo(5.1465f, 7.3536f)
                curveTo(4.9512f, 7.1583f, 4.9512f, 6.8417f, 5.1464f, 6.6465f)
                curveTo(5.3417f, 6.4512f, 5.6583f, 6.4512f, 5.8535f, 6.6465f)
                lineTo(6.5f, 7.2929f)
                lineTo(8.3964f, 5.3965f)
                curveTo(8.5917f, 5.2012f, 8.9083f, 5.2012f, 9.1035f, 5.3964f)
                curveTo(9.2988f, 5.5917f, 9.2988f, 5.9083f, 9.1036f, 6.1035f)
                close()
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
                    strokeLineWidth = 2.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(7.0f, 1.0f)
                lineTo(7.0f, 1.0f)
                arcTo(6.0f, 6.0f, 0.0f, false, true, 13.0f, 7.0f)
                lineTo(13.0f, 7.0f)
                arcTo(6.0f, 6.0f, 0.0f, false, true, 7.0f, 13.0f)
                lineTo(7.0f, 13.0f)
                arcTo(6.0f, 6.0f, 0.0f, false, true, 1.0f, 7.0f)
                lineTo(1.0f, 7.0f)
                arcTo(6.0f, 6.0f, 0.0f, false, true, 7.0f, 1.0f)
                close()
            }
        }
                .build()
        return _light!!
    }

private var _light: ImageVector? = null
