package com.microsoft.fluentui.icons.avataricons.presence.blocked.medium

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.icons.avataricons.presence.blocked.MediumGroup

val MediumGroup.Light: ImageVector
    get() {
        if (_light != null) {
            return _light!!
        }
        _light = Builder(name = "Light", defaultWidth = 16.0.dp, defaultHeight = 16.0.dp,
                viewportWidth = 16.0f, viewportHeight = 16.0f).apply {
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(8.0f, 1.0f)
                lineTo(8.0f, 1.0f)
                arcTo(7.0f, 7.0f, 0.0f, false, true, 15.0f, 8.0f)
                lineTo(15.0f, 8.0f)
                arcTo(7.0f, 7.0f, 0.0f, false, true, 8.0f, 15.0f)
                lineTo(8.0f, 15.0f)
                arcTo(7.0f, 7.0f, 0.0f, false, true, 1.0f, 8.0f)
                lineTo(1.0f, 8.0f)
                arcTo(7.0f, 7.0f, 0.0f, false, true, 8.0f, 1.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFFffffff)),
                    strokeLineWidth = 2.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(8.0f, 8.0f)
                moveToRelative(-7.0f, 0.0f)
                arcToRelative(7.0f, 7.0f, 0.0f, true, true, 14.0f, 0.0f)
                arcToRelative(7.0f, 7.0f, 0.0f, true, true, -14.0f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFFC50F1F)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(14.0f, 8.0f)
                curveTo(14.0f, 11.3137f, 11.3137f, 14.0f, 8.0f, 14.0f)
                curveTo(4.6863f, 14.0f, 2.0f, 11.3137f, 2.0f, 8.0f)
                curveTo(2.0f, 4.6863f, 4.6863f, 2.0f, 8.0f, 2.0f)
                curveTo(11.3137f, 2.0f, 14.0f, 4.6863f, 14.0f, 8.0f)
                close()
                moveTo(12.5f, 8.0f)
                curveTo(12.5f, 7.0281f, 12.1919f, 6.1282f, 11.668f, 5.3926f)
                lineTo(5.3926f, 11.668f)
                curveTo(6.1282f, 12.1919f, 7.0281f, 12.5f, 8.0f, 12.5f)
                curveTo(10.4853f, 12.5f, 12.5f, 10.4853f, 12.5f, 8.0f)
                close()
                moveTo(10.6074f, 4.3319f)
                curveTo(9.8718f, 3.8081f, 8.9719f, 3.5f, 8.0f, 3.5f)
                curveTo(5.5147f, 3.5f, 3.5f, 5.5147f, 3.5f, 8.0f)
                curveTo(3.5f, 8.9719f, 3.8081f, 9.8718f, 4.3319f, 10.6074f)
                lineTo(10.6074f, 4.3319f)
                close()
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
                    strokeLineWidth = 2.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(8.0f, 1.0f)
                lineTo(8.0f, 1.0f)
                arcTo(7.0f, 7.0f, 0.0f, false, true, 15.0f, 8.0f)
                lineTo(15.0f, 8.0f)
                arcTo(7.0f, 7.0f, 0.0f, false, true, 8.0f, 15.0f)
                lineTo(8.0f, 15.0f)
                arcTo(7.0f, 7.0f, 0.0f, false, true, 1.0f, 8.0f)
                lineTo(1.0f, 8.0f)
                arcTo(7.0f, 7.0f, 0.0f, false, true, 8.0f, 1.0f)
                close()
            }
        }
                .build()
        return _light!!
    }

private var _light: ImageVector? = null
