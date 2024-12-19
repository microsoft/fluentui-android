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
import com.microsoft.fluentui.icons.ListItemIcons

val ActionBarIcons.Chevron: ImageVector
    get() {
        if (_chevron != null) {
            return _chevron!!
        }
        _chevron = Builder(name = "Chevron", defaultWidth = 12.0.dp, defaultHeight = 12.0.dp,
                viewportWidth = 12.0f, viewportHeight = 12.0f).apply {
            path(fill = SolidColor(Color(0xFF808080)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(4.6465f, 2.1465f)
                curveTo(4.4512f, 2.3417f, 4.4512f, 2.6583f, 4.6465f, 2.8535f)
                lineTo(7.7929f, 6.0f)
                lineTo(4.6465f, 9.1465f)
                curveTo(4.4512f, 9.3417f, 4.4512f, 9.6583f, 4.6465f, 9.8535f)
                curveTo(4.8417f, 10.0488f, 5.1583f, 10.0488f, 5.3535f, 9.8535f)
                lineTo(8.8535f, 6.3535f)
                curveTo(9.0488f, 6.1583f, 9.0488f, 5.8417f, 8.8535f, 5.6465f)
                lineTo(5.3535f, 2.1465f)
                curveTo(5.1583f, 1.9512f, 4.8417f, 1.9512f, 4.6465f, 2.1465f)
                close()
            }
        }
        .build()
        return _chevron!!
    }

private var _chevron: ImageVector? = null
