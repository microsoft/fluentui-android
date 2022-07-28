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

val StandardGroup.Xxlarge: ImageVector
    get() {
        if (_xxlarge != null) {
            return _xxlarge!!
        }
        _xxlarge = Builder(name = "Xxlarge", defaultWidth = 48.0.dp, defaultHeight = 48.0.dp,
                viewportWidth = 48.0f, viewportHeight = 48.0f).apply {
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(24.0f, 4.0f)
                curveTo(18.4772f, 4.0f, 14.0f, 8.4771f, 14.0f, 14.0f)
                curveTo(14.0f, 19.5228f, 18.4772f, 24.0f, 24.0f, 24.0f)
                curveTo(29.5228f, 24.0f, 34.0f, 19.5228f, 34.0f, 14.0f)
                curveTo(34.0f, 8.4771f, 29.5228f, 4.0f, 24.0f, 4.0f)
                close()
                moveTo(12.2499f, 28.0f)
                curveTo(9.9033f, 28.0f, 8.0f, 29.9013f, 8.0f, 32.2489f)
                lineTo(8.0f, 33.0f)
                curveTo(8.0f, 36.7555f, 9.9417f, 39.5669f, 12.9202f, 41.3802f)
                curveTo(15.8491f, 43.1633f, 19.7861f, 44.0f, 24.0f, 44.0f)
                curveTo(28.2139f, 44.0f, 32.1509f, 43.1633f, 35.0798f, 41.3802f)
                curveTo(38.0583f, 39.5669f, 40.0f, 36.7555f, 40.0f, 33.0f)
                lineTo(40.0f, 32.2487f)
                curveTo(40.0f, 29.9011f, 38.0967f, 28.0f, 35.7502f, 28.0f)
                horizontalLineTo(12.2499f)
                close()
            }
        }
                .build()
        return _xxlarge!!
    }

private var _xxlarge: ImageVector? = null
