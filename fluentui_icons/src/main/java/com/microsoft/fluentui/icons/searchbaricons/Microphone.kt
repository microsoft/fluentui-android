package com.microsoft.fluentui.icons.searchbaricons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.icons.SearchBarIcons

public val SearchBarIcons.Microphone: ImageVector
    get() {
        if (_microphone != null) {
            return _microphone!!
        }
        _microphone = Builder(name = "Microphone", defaultWidth = 20.0.dp, defaultHeight = 20.0.dp,
                viewportWidth = 20.0f, viewportHeight = 20.0f).apply {
            path(fill = SolidColor(Color(0xFF616161)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(10.0f, 13.0f)
                curveTo(11.6569f, 13.0f, 13.0f, 11.6568f, 13.0f, 10.0f)
                verticalLineTo(5.0f)
                curveTo(13.0f, 3.3431f, 11.6569f, 2.0f, 10.0f, 2.0f)
                curveTo(8.3432f, 2.0f, 7.0f, 3.3431f, 7.0f, 5.0f)
                verticalLineTo(10.0f)
                curveTo(7.0f, 11.6568f, 8.3432f, 13.0f, 10.0f, 13.0f)
                close()
                moveTo(10.0f, 12.0f)
                curveTo(8.8954f, 12.0f, 8.0f, 11.1046f, 8.0f, 10.0f)
                verticalLineTo(5.0f)
                curveTo(8.0f, 3.8954f, 8.8954f, 3.0f, 10.0f, 3.0f)
                curveTo(11.1046f, 3.0f, 12.0f, 3.8954f, 12.0f, 5.0f)
                verticalLineTo(10.0f)
                curveTo(12.0f, 11.1046f, 11.1046f, 12.0f, 10.0f, 12.0f)
                close()
                moveTo(5.0f, 9.5f)
                curveTo(5.2761f, 9.5f, 5.5f, 9.7238f, 5.5f, 10.0f)
                curveTo(5.5f, 12.4853f, 7.5147f, 14.5f, 10.0f, 14.5f)
                curveTo(12.4853f, 14.5f, 14.5f, 12.4853f, 14.5f, 10.0f)
                curveTo(14.5f, 9.7238f, 14.7239f, 9.5f, 15.0f, 9.5f)
                curveTo(15.2761f, 9.5f, 15.5f, 9.7238f, 15.5f, 10.0f)
                curveTo(15.5f, 12.869f, 13.3033f, 15.2249f, 10.5f, 15.4776f)
                verticalLineTo(17.5f)
                curveTo(10.5f, 17.7761f, 10.2761f, 18.0f, 10.0f, 18.0f)
                curveTo(9.7239f, 18.0f, 9.5f, 17.7761f, 9.5f, 17.5f)
                verticalLineTo(15.4776f)
                curveTo(6.6968f, 15.2249f, 4.5f, 12.869f, 4.5f, 10.0f)
                curveTo(4.5f, 9.7238f, 4.7239f, 9.5f, 5.0f, 9.5f)
                close()
            }
        }
        .build()
        return _microphone!!
    }

private var _microphone: ImageVector? = null
