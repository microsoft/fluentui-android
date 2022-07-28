package com.microsoft.fluentui.icons.avataricons.presence.available.medium

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.icons.avataricons.presence.available.MediumGroup

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
            path(fill = SolidColor(Color(0xFF6BB700)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(8.0f, 14.0f)
                curveTo(11.3137f, 14.0f, 14.0f, 11.3137f, 14.0f, 8.0f)
                curveTo(14.0f, 4.6863f, 11.3137f, 2.0f, 8.0f, 2.0f)
                curveTo(4.6863f, 2.0f, 2.0f, 4.6863f, 2.0f, 8.0f)
                curveTo(2.0f, 11.3137f, 4.6863f, 14.0f, 8.0f, 14.0f)
                close()
                moveTo(10.5303f, 7.2803f)
                lineTo(7.7803f, 10.0303f)
                curveTo(7.4874f, 10.3232f, 7.0126f, 10.3232f, 6.7197f, 10.0303f)
                lineTo(5.7197f, 9.0303f)
                curveTo(5.4268f, 8.7374f, 5.4268f, 8.2626f, 5.7197f, 7.9697f)
                curveTo(6.0126f, 7.6768f, 6.4874f, 7.6768f, 6.7803f, 7.9697f)
                lineTo(7.25f, 8.4393f)
                lineTo(9.4697f, 6.2197f)
                curveTo(9.7626f, 5.9268f, 10.2374f, 5.9268f, 10.5303f, 6.2197f)
                curveTo(10.8232f, 6.5126f, 10.8232f, 6.9874f, 10.5303f, 7.2803f)
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
