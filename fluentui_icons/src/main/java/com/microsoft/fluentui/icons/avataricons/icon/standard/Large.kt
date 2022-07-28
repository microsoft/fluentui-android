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

val StandardGroup.Large: ImageVector
    get() {
        if (_large != null) {
            return _large!!
        }
        _large = Builder(name = "Large", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(17.7542f, 13.9997f)
                curveTo(18.9962f, 13.9997f, 20.003f, 15.0065f, 20.003f, 16.2486f)
                verticalLineTo(17.167f)
                curveTo(20.003f, 17.7404f, 19.8238f, 18.2994f, 19.4905f, 18.7659f)
                curveTo(17.9446f, 20.9292f, 15.4203f, 22.0008f, 12.0f, 22.0008f)
                curveTo(8.579f, 22.0008f, 6.0561f, 20.9287f, 4.5139f, 18.7643f)
                curveTo(4.182f, 18.2984f, 4.0035f, 17.7406f, 4.0035f, 17.1685f)
                verticalLineTo(16.2486f)
                curveTo(4.0035f, 15.0065f, 5.0104f, 13.9997f, 6.2524f, 13.9997f)
                horizontalLineTo(17.7542f)
                close()
                moveTo(12.0f, 2.0044f)
                curveTo(14.7614f, 2.0044f, 17.0f, 4.243f, 17.0f, 7.0044f)
                curveTo(17.0f, 9.7658f, 14.7614f, 12.0044f, 12.0f, 12.0044f)
                curveTo(9.2386f, 12.0044f, 7.0f, 9.7658f, 7.0f, 7.0044f)
                curveTo(7.0f, 4.243f, 9.2386f, 2.0044f, 12.0f, 2.0044f)
                close()
            }
        }
                .build()
        return _large!!
    }

private var _large: ImageVector? = null
