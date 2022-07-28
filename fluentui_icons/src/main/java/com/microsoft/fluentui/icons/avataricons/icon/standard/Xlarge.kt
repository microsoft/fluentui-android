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

val StandardGroup.Xlarge: ImageVector
    get() {
        if (_xlarge != null) {
            return _xlarge!!
        }
        _xlarge = Builder(name = "Xlarge", defaultWidth = 28.0.dp, defaultHeight = 28.0.dp,
                viewportWidth = 28.0f, viewportHeight = 28.0f).apply {
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(21.0f, 16.0f)
                curveTo(22.6569f, 16.0f, 24.0f, 17.3431f, 24.0f, 19.0f)
                verticalLineTo(19.7146f)
                curveTo(24.0f, 23.2924f, 19.7895f, 26.0f, 14.0f, 26.0f)
                curveTo(8.2105f, 26.0f, 4.0f, 23.4333f, 4.0f, 19.7146f)
                verticalLineTo(19.0f)
                curveTo(4.0f, 17.3431f, 5.3432f, 16.0f, 7.0f, 16.0f)
                horizontalLineTo(21.0f)
                close()
                moveTo(14.0f, 2.0f)
                curveTo(17.3137f, 2.0f, 20.0f, 4.6863f, 20.0f, 8.0f)
                curveTo(20.0f, 11.3137f, 17.3137f, 14.0f, 14.0f, 14.0f)
                curveTo(10.6863f, 14.0f, 8.0f, 11.3137f, 8.0f, 8.0f)
                curveTo(8.0f, 4.6863f, 10.6863f, 2.0f, 14.0f, 2.0f)
                close()
            }
        }
                .build()
        return _xlarge!!
    }

private var _xlarge: ImageVector? = null
