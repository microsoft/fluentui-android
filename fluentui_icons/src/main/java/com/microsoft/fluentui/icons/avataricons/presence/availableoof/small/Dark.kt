package com.microsoft.fluentui.icons.avataricons.presence.availableoof.small

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.icons.avataricons.presence.availableoof.SmallGroup

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
            path(fill = SolidColor(Color(0xFF92C353)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(6.9977f, 2.0f)
                curveTo(4.2375f, 2.0f, 2.0f, 4.2375f, 2.0f, 6.9977f)
                curveTo(2.0f, 9.7578f, 4.2375f, 11.9954f, 6.9977f, 11.9954f)
                curveTo(9.7578f, 11.9954f, 11.9954f, 9.7578f, 11.9954f, 6.9977f)
                curveTo(11.9954f, 4.2375f, 9.7578f, 2.0f, 6.9977f, 2.0f)
                close()
                moveTo(3.0f, 6.9977f)
                curveTo(3.0f, 4.7898f, 4.7898f, 3.0f, 6.9977f, 3.0f)
                curveTo(9.2056f, 3.0f, 10.9954f, 4.7898f, 10.9954f, 6.9977f)
                curveTo(10.9954f, 9.2056f, 9.2056f, 10.9954f, 6.9977f, 10.9954f)
                curveTo(4.7898f, 10.9954f, 3.0f, 9.2056f, 3.0f, 6.9977f)
                close()
                moveTo(9.1035f, 5.3965f)
                curveTo(9.2988f, 5.5917f, 9.2988f, 5.9083f, 9.1035f, 6.1035f)
                lineTo(6.8535f, 8.3535f)
                curveTo(6.6583f, 8.5488f, 6.3417f, 8.5488f, 6.1465f, 8.3535f)
                lineTo(5.1465f, 7.3535f)
                curveTo(4.9512f, 7.1583f, 4.9512f, 6.8417f, 5.1465f, 6.6465f)
                curveTo(5.3417f, 6.4512f, 5.6583f, 6.4512f, 5.8535f, 6.6465f)
                lineTo(6.5f, 7.2929f)
                lineTo(8.3965f, 5.3965f)
                curveTo(8.5917f, 5.2012f, 8.9083f, 5.2012f, 9.1035f, 5.3965f)
                close()
            }
        }
                .build()
        return _dark!!
    }

private var _dark: ImageVector? = null
