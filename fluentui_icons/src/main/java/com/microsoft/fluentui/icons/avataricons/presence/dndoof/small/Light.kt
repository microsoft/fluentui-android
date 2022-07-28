package com.microsoft.fluentui.icons.avataricons.presence.dndoof.small

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.icons.avataricons.presence.dndoof.SmallGroup

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
            path(fill = SolidColor(Color(0xFFC50F1F)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(6.9977f, 2.0f)
                curveTo(4.2375f, 2.0f, 2.0f, 4.2375f, 2.0f, 6.9977f)
                curveTo(2.0f, 9.7578f, 4.2375f, 11.9954f, 6.9977f, 11.9954f)
                curveTo(9.7578f, 11.9954f, 11.9954f, 9.7578f, 11.9954f, 6.9977f)
                curveTo(11.9954f, 4.2375f, 9.7578f, 2.0f, 6.9977f, 2.0f)
                close()
                moveTo(3.0f, 6.9977f)
                curveTo(3.0f, 4.7898f, 4.7898f, 3.0f, 6.9977f, 3.0f)
                curveTo(9.2056f, 3.0f, 10.9954f, 4.7898f, 10.9954f, 6.9977f)
                curveTo(10.9954f, 9.2056f, 9.2056f, 10.9954f, 6.9977f, 10.9954f)
                curveTo(4.7898f, 10.9954f, 3.0f, 9.2056f, 3.0f, 6.9977f)
                close()
                moveTo(5.0f, 7.0f)
                curveTo(5.0f, 6.7239f, 5.2239f, 6.5f, 5.5f, 6.5f)
                horizontalLineTo(8.5f)
                curveTo(8.7761f, 6.5f, 9.0f, 6.7239f, 9.0f, 7.0f)
                curveTo(9.0f, 7.2761f, 8.7761f, 7.5f, 8.5f, 7.5f)
                horizontalLineTo(5.5f)
                curveTo(5.2239f, 7.5f, 5.0f, 7.2761f, 5.0f, 7.0f)
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
