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

val SearchBarIcons.Arrowback: ImageVector
    get() {
        if (_arrowback != null) {
            return _arrowback!!
        }
        _arrowback = Builder(
            name = "Arrowback", defaultWidth = 20.0.dp, defaultHeight = 20.0.dp,
            viewportWidth = 20.0f, viewportHeight = 20.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF242424)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(9.159f, 16.3666f)
                curveTo(9.3629f, 16.5528f, 9.6792f, 16.5384f, 9.8654f, 16.3345f)
                curveTo(10.0515f, 16.1305f, 10.0371f, 15.8143f, 9.8332f, 15.6281f)
                lineTo(3.6653f, 9.9974f)
                horizontalLineTo(17.4961f)
                curveTo(17.7722f, 9.9974f, 17.9961f, 9.7735f, 17.9961f, 9.4974f)
                curveTo(17.9961f, 9.2212f, 17.7722f, 8.9974f, 17.4961f, 8.9974f)
                horizontalLineTo(3.6682f)
                lineTo(9.8332f, 3.3693f)
                curveTo(10.0371f, 3.1831f, 10.0515f, 2.8668f, 9.8654f, 2.6629f)
                curveTo(9.6792f, 2.459f, 9.3629f, 2.4446f, 9.159f, 2.6307f)
                lineTo(2.2426f, 8.9448f)
                curveTo(2.1027f, 9.0725f, 2.0229f, 9.2401f, 2.0031f, 9.4132f)
                curveTo(1.9985f, 9.4406f, 1.9961f, 9.4687f, 1.9961f, 9.4974f)
                curveTo(1.9961f, 9.5242f, 1.9982f, 9.5506f, 2.0023f, 9.5763f)
                curveTo(2.0205f, 9.7522f, 2.1006f, 9.9229f, 2.2426f, 10.0526f)
                lineTo(9.159f, 16.3666f)
                close()
            }
        }
            .build()
        return _arrowback!!
    }

private var _arrowback: ImageVector? = null
