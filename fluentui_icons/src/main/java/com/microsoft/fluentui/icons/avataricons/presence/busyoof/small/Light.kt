package com.microsoft.fluentui.icons.avataricons.presence.busyoof.small

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.icons.avataricons.presence.busyoof.SmallGroup

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
                moveTo(6.9987f, 3.0f)
                curveTo(4.7903f, 3.0f, 3.0f, 4.7903f, 3.0f, 6.9987f)
                curveTo(3.0f, 9.2071f, 4.7903f, 10.9974f, 6.9987f, 10.9974f)
                curveTo(9.2071f, 10.9974f, 10.9974f, 9.2071f, 10.9974f, 6.9987f)
                curveTo(10.9974f, 4.7903f, 9.2071f, 3.0f, 6.9987f, 3.0f)
                close()
                moveTo(2.0f, 6.9987f)
                curveTo(2.0f, 4.238f, 4.238f, 2.0f, 6.9987f, 2.0f)
                curveTo(9.7594f, 2.0f, 11.9974f, 4.238f, 11.9974f, 6.9987f)
                curveTo(11.9974f, 9.7594f, 9.7594f, 11.9974f, 6.9987f, 11.9974f)
                curveTo(4.238f, 11.9974f, 2.0f, 9.7594f, 2.0f, 6.9987f)
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
