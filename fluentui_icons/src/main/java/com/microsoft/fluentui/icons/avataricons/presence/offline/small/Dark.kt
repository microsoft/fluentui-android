package com.microsoft.fluentui.icons.avataricons.presence.offline.small

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.icons.avataricons.presence.offline.SmallGroup

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
            path(fill = SolidColor(Color(0xFF979593)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(8.8535f, 5.1465f)
                curveTo(9.0488f, 5.3417f, 9.0488f, 5.6583f, 8.8535f, 5.8535f)
                lineTo(7.7071f, 7.0f)
                lineTo(8.8535f, 8.1465f)
                curveTo(9.0488f, 8.3417f, 9.0488f, 8.6583f, 8.8535f, 8.8535f)
                curveTo(8.6583f, 9.0488f, 8.3417f, 9.0488f, 8.1465f, 8.8535f)
                lineTo(7.0f, 7.7071f)
                lineTo(5.8535f, 8.8535f)
                curveTo(5.6583f, 9.0488f, 5.3417f, 9.0488f, 5.1465f, 8.8535f)
                curveTo(4.9512f, 8.6583f, 4.9512f, 8.3417f, 5.1465f, 8.1465f)
                lineTo(6.2929f, 7.0f)
                lineTo(5.1465f, 5.8535f)
                curveTo(4.9512f, 5.6583f, 4.9512f, 5.3417f, 5.1465f, 5.1465f)
                curveTo(5.3417f, 4.9512f, 5.6583f, 4.9512f, 5.8535f, 5.1465f)
                lineTo(7.0f, 6.2929f)
                lineTo(8.1465f, 5.1465f)
                curveTo(8.3417f, 4.9512f, 8.6583f, 4.9512f, 8.8535f, 5.1465f)
                close()
                moveTo(2.0f, 7.0f)
                curveTo(2.0f, 4.2386f, 4.2386f, 2.0f, 7.0f, 2.0f)
                curveTo(9.7615f, 2.0f, 12.0001f, 4.2386f, 12.0001f, 7.0f)
                curveTo(12.0001f, 9.7615f, 9.7615f, 12.0001f, 7.0f, 12.0001f)
                curveTo(4.2386f, 12.0001f, 2.0f, 9.7615f, 2.0f, 7.0f)
                close()
                moveTo(7.0f, 3.0f)
                curveTo(4.7909f, 3.0f, 3.0f, 4.7909f, 3.0f, 7.0f)
                curveTo(3.0f, 9.2092f, 4.7909f, 11.0001f, 7.0f, 11.0001f)
                curveTo(9.2092f, 11.0001f, 11.0001f, 9.2092f, 11.0001f, 7.0f)
                curveTo(11.0001f, 4.7909f, 9.2092f, 3.0f, 7.0f, 3.0f)
                close()
            }
        }
                .build()
        return _dark!!
    }

private var _dark: ImageVector? = null
