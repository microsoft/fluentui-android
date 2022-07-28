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

val StandardGroup.Small: ImageVector
    get() {
        if (_small != null) {
            return _small!!
        }
        _small = Builder(name = "Small", defaultWidth = 16.0.dp, defaultHeight = 16.0.dp,
                viewportWidth = 16.0f, viewportHeight = 16.0f).apply {
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(11.5f, 8.0f)
                curveTo(12.3284f, 8.0f, 13.0f, 8.6716f, 13.0f, 9.5f)
                verticalLineTo(10.0f)
                curveTo(13.0f, 11.9714f, 11.1405f, 14.0f, 8.0f, 14.0f)
                curveTo(4.8595f, 14.0f, 3.0f, 11.9714f, 3.0f, 10.0f)
                verticalLineTo(9.5f)
                curveTo(3.0f, 8.6716f, 3.6716f, 8.0f, 4.5f, 8.0f)
                horizontalLineTo(11.5f)
                close()
                moveTo(8.0f, 1.5f)
                curveTo(9.5188f, 1.5f, 10.75f, 2.7312f, 10.75f, 4.25f)
                curveTo(10.75f, 5.7688f, 9.5188f, 7.0f, 8.0f, 7.0f)
                curveTo(6.4812f, 7.0f, 5.25f, 5.7688f, 5.25f, 4.25f)
                curveTo(5.25f, 2.7312f, 6.4812f, 1.5f, 8.0f, 1.5f)
                close()
            }
        }
                .build()
        return _small!!
    }

private var _small: ImageVector? = null
