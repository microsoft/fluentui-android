package com.microsoft.fluentui.icons.avataricons.presence.away.small

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.icons.avataricons.presence.away.SmallGroup

val SmallGroup.Dark: ImageVector
    get() {
        if (_dark != null) {
            return _dark!!
        }
        _dark = Builder(name = "Dark", defaultWidth = 14.0.dp, defaultHeight = 14.0.dp,
                viewportWidth = 14.0f, viewportHeight = 14.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 2.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(7.0f, 7.0f)
                moveToRelative(-6.0f, 0.0f)
                arcToRelative(6.0f, 6.0f, 0.0f, true, true, 12.0f, 0.0f)
                arcToRelative(6.0f, 6.0f, 0.0f, true, true, -12.0f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFFF8D22A)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(7.0f, 12.0f)
                curveTo(9.7614f, 12.0f, 12.0f, 9.7614f, 12.0f, 7.0f)
                curveTo(12.0f, 4.2386f, 9.7614f, 2.0f, 7.0f, 2.0f)
                curveTo(4.2386f, 2.0f, 2.0f, 4.2386f, 2.0f, 7.0f)
                curveTo(2.0f, 9.7614f, 4.2386f, 12.0f, 7.0f, 12.0f)
                close()
                moveTo(7.0f, 5.0041f)
                verticalLineTo(6.7931f)
                lineTo(8.3535f, 8.1467f)
                curveTo(8.5488f, 8.3419f, 8.5488f, 8.6585f, 8.3535f, 8.8538f)
                curveTo(8.1583f, 9.049f, 7.8417f, 9.049f, 7.6465f, 8.8538f)
                lineTo(6.1465f, 7.3538f)
                curveTo(6.0527f, 7.26f, 6.0f, 7.1328f, 6.0f, 7.0002f)
                verticalLineTo(5.0041f)
                curveTo(6.0f, 4.728f, 6.2239f, 4.5041f, 6.5f, 4.5041f)
                curveTo(6.7761f, 4.5041f, 7.0f, 4.728f, 7.0f, 5.0041f)
                close()
            }
        }
                .build()
        return _dark!!
    }

private var _dark: ImageVector? = null
