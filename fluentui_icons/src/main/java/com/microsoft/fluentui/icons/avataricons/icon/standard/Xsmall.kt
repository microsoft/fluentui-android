package com.microsoft.fluentui.icons.avataricons.icon.standard

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.icons.avataricons.icon.StandardGroup

val StandardGroup.Xsmall: ImageVector
    get() {
        if (_xsmall != null) {
            return _xsmall!!
        }
        _xsmall = Builder(name = "Xsmall", defaultWidth = 12.0.dp, defaultHeight = 12.0.dp,
                viewportWidth = 12.0f, viewportHeight = 12.0f).apply {
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(6.0f, 1.0f)
                curveTo(4.8954f, 1.0f, 4.0f, 1.8954f, 4.0f, 3.0f)
                curveTo(4.0f, 4.1046f, 4.8954f, 5.0f, 6.0f, 5.0f)
                curveTo(7.1046f, 5.0f, 8.0f, 4.1046f, 8.0f, 3.0f)
                curveTo(8.0f, 1.8954f, 7.1046f, 1.0f, 6.0f, 1.0f)
                close()
                moveTo(8.5f, 6.0f)
                lineTo(3.5f, 6.0f)
                curveTo(2.6716f, 6.0f, 2.0f, 6.6716f, 2.0f, 7.5f)
                curveTo(2.0f, 8.6161f, 2.459f, 9.5103f, 3.2122f, 10.1148f)
                curveTo(3.9534f, 10.7098f, 4.9469f, 11.0f, 6.0f, 11.0f)
                curveTo(7.0531f, 11.0f, 8.0466f, 10.7098f, 8.7879f, 10.1148f)
                curveTo(9.541f, 9.5103f, 10.0f, 8.6161f, 10.0f, 7.5f)
                curveTo(10.0f, 6.6716f, 9.3284f, 6.0f, 8.5f, 6.0f)
                close()
            }
        }
                .build()
        return _xsmall!!
    }

private var _xsmall: ImageVector? = null
