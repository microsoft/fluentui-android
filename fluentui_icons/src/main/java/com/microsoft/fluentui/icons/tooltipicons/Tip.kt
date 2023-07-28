package com.microsoft.fluentui.icons.tooltipicons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.icons.ToolTipIcons

public val ToolTipIcons.Tip: ImageVector
    get() {
        if (_tip != null) {
            return _tip!!
        }
        _tip = Builder(name = "Tip", defaultWidth = 14.0.dp, defaultHeight = 7.0.dp, viewportWidth =
                14.0f, viewportHeight = 7.0f).apply {
            path(fill = SolidColor(Color(0xFF242424)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(7.7071f, 0.5899f)
                curveTo(7.3166f, 0.1994f, 6.6834f, 0.1994f, 6.2929f, 0.5899f)
                lineTo(0.0f, 6.8828f)
                horizontalLineTo(14.0f)
                lineTo(7.7071f, 0.5899f)
                close()
            }
        }
        .build()
        return _tip!!
    }

private var _tip: ImageVector? = null
