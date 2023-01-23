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

val SearchBarIcons.Dismisscircle: ImageVector
    get() {
        if (_dismisscircle != null) {
            return _dismisscircle!!
        }
        _dismisscircle = Builder(
            name = "Dismisscircle", defaultWidth = 20.0.dp, defaultHeight =
            20.0.dp, viewportWidth = 20.0f, viewportHeight = 20.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF616161)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(10.0f, 2.0f)
                curveTo(14.4183f, 2.0f, 18.0f, 5.5817f, 18.0f, 10.0f)
                curveTo(18.0f, 14.4183f, 14.4183f, 18.0f, 10.0f, 18.0f)
                curveTo(5.5817f, 18.0f, 2.0f, 14.4183f, 2.0f, 10.0f)
                curveTo(2.0f, 5.5817f, 5.5817f, 2.0f, 10.0f, 2.0f)
                close()
                moveTo(7.8094f, 7.1137f)
                curveTo(7.6146f, 6.9787f, 7.3451f, 6.998f, 7.1716f, 7.1716f)
                lineTo(7.1137f, 7.2408f)
                curveTo(6.9787f, 7.4357f, 6.998f, 7.7051f, 7.1716f, 7.8787f)
                lineTo(9.2929f, 10.0f)
                lineTo(7.1716f, 12.1213f)
                lineTo(7.1137f, 12.1906f)
                curveTo(6.9787f, 12.3854f, 6.998f, 12.6549f, 7.1716f, 12.8284f)
                lineTo(7.2408f, 12.8863f)
                curveTo(7.4357f, 13.0213f, 7.7051f, 13.002f, 7.8787f, 12.8284f)
                lineTo(10.0f, 10.7071f)
                lineTo(12.1213f, 12.8284f)
                lineTo(12.1906f, 12.8863f)
                curveTo(12.3854f, 13.0213f, 12.6549f, 13.002f, 12.8284f, 12.8284f)
                lineTo(12.8863f, 12.7592f)
                curveTo(13.0213f, 12.5643f, 13.002f, 12.2949f, 12.8284f, 12.1213f)
                lineTo(10.7071f, 10.0f)
                lineTo(12.8284f, 7.8787f)
                lineTo(12.8863f, 7.8094f)
                curveTo(13.0213f, 7.6146f, 13.002f, 7.3451f, 12.8284f, 7.1716f)
                lineTo(12.7592f, 7.1137f)
                curveTo(12.5643f, 6.9787f, 12.2949f, 6.998f, 12.1213f, 7.1716f)
                lineTo(10.0f, 9.2929f)
                lineTo(7.8787f, 7.1716f)
                lineTo(7.8094f, 7.1137f)
                close()
            }
        }
            .build()
        return _dismisscircle!!
    }

private var _dismisscircle: ImageVector? = null
