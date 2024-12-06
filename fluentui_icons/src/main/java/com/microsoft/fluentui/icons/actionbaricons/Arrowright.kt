package com.microsoft.fluentui.icons.actionbaricons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.icons.ActionBarIcons

val ActionBarIcons.Arrowright: ImageVector
    get() {
        if (_arrowright != null) {
            return _arrowright!!
        }
        _arrowright = Builder(name = "Arrowright", defaultWidth = 20.0.dp, defaultHeight = 20.0.dp,
                viewportWidth = 20.0f, viewportHeight = 20.0f).apply {
            path(fill = SolidColor(Color(0xFF212121)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(10.8371f, 3.1307f)
                curveTo(10.6332f, 2.9446f, 10.3169f, 2.959f, 10.1307f, 3.1629f)
                curveTo(9.9446f, 3.3668f, 9.959f, 3.6831f, 10.1629f, 3.8693f)
                lineTo(16.3307f, 9.5f)
                horizontalLineTo(2.5f)
                curveTo(2.2239f, 9.5f, 2.0f, 9.7239f, 2.0f, 10.0f)
                curveTo(2.0f, 10.2761f, 2.2239f, 10.5f, 2.5f, 10.5f)
                horizontalLineTo(16.3279f)
                lineTo(10.1629f, 16.1281f)
                curveTo(9.959f, 16.3143f, 9.9446f, 16.6305f, 10.1307f, 16.8345f)
                curveTo(10.3169f, 17.0384f, 10.6332f, 17.0528f, 10.8371f, 16.8666f)
                lineTo(17.7535f, 10.5526f)
                curveTo(17.8934f, 10.4248f, 17.9732f, 10.2573f, 17.993f, 10.0841f)
                curveTo(17.9976f, 10.0568f, 18.0f, 10.0287f, 18.0f, 10.0f)
                curveTo(18.0f, 9.9731f, 17.9979f, 9.9467f, 17.9938f, 9.921f)
                curveTo(17.9756f, 9.7451f, 17.8955f, 9.5745f, 17.7535f, 9.4448f)
                lineTo(10.8371f, 3.1307f)
                close()
            }
        }
        .build()
        return _arrowright!!
    }

private var _arrowright: ImageVector? = null
