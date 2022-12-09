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

public val SearchBarIcons.Search: ImageVector
    get() {
        if (_search != null) {
            return _search!!
        }
        _search = Builder(name = "Search", defaultWidth = 20.0.dp, defaultHeight = 20.0.dp,
                viewportWidth = 20.0f, viewportHeight = 20.0f).apply {
            path(fill = SolidColor(Color(0xFF808080)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(8.5f, 3.0f)
                curveTo(11.5376f, 3.0f, 14.0f, 5.4624f, 14.0f, 8.5f)
                curveTo(14.0f, 9.8388f, 13.5217f, 11.0659f, 12.7266f, 12.0196f)
                lineTo(16.8536f, 16.1464f)
                curveTo(17.0488f, 16.3417f, 17.0488f, 16.6583f, 16.8536f, 16.8536f)
                curveTo(16.68f, 17.0271f, 16.4106f, 17.0464f, 16.2157f, 16.9114f)
                lineTo(16.1464f, 16.8536f)
                lineTo(12.0196f, 12.7266f)
                curveTo(11.0659f, 13.5217f, 9.8388f, 14.0f, 8.5f, 14.0f)
                curveTo(5.4624f, 14.0f, 3.0f, 11.5376f, 3.0f, 8.5f)
                curveTo(3.0f, 5.4624f, 5.4624f, 3.0f, 8.5f, 3.0f)
                close()
                moveTo(8.5f, 4.0f)
                curveTo(6.0147f, 4.0f, 4.0f, 6.0147f, 4.0f, 8.5f)
                curveTo(4.0f, 10.9853f, 6.0147f, 13.0f, 8.5f, 13.0f)
                curveTo(10.9853f, 13.0f, 13.0f, 10.9853f, 13.0f, 8.5f)
                curveTo(13.0f, 6.0147f, 10.9853f, 4.0f, 8.5f, 4.0f)
                close()
            }
        }
        .build()
        return _search!!
    }

private var _search: ImageVector? = null
