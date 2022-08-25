package com.microsoft.fluentui.icons.avataricons.icon.anonymous

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.icons.avataricons.icon.AnonymousGroup

val AnonymousGroup.Medium: ImageVector
    get() {
        if (_medium != null) {
            return _medium!!
        }
        _medium = Builder(name = "Medium", defaultWidth = 20.0.dp, defaultHeight = 20.0.dp,
                viewportWidth = 20.0f, viewportHeight = 20.0f).apply {
            path(fill = SolidColor(Color(0xFF616161)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(10.0f, 2.0f)
                curveTo(7.7909f, 2.0f, 6.0f, 3.7909f, 6.0f, 6.0f)
                curveTo(6.0f, 8.2091f, 7.7909f, 10.0f, 10.0f, 10.0f)
                curveTo(12.2091f, 10.0f, 14.0f, 8.2091f, 14.0f, 6.0f)
                curveTo(14.0f, 3.7909f, 12.2091f, 2.0f, 10.0f, 2.0f)
                close()
                moveTo(7.0f, 6.0f)
                curveTo(7.0f, 4.3432f, 8.3432f, 3.0f, 10.0f, 3.0f)
                curveTo(11.6569f, 3.0f, 13.0f, 4.3432f, 13.0f, 6.0f)
                curveTo(13.0f, 7.6568f, 11.6569f, 9.0f, 10.0f, 9.0f)
                curveTo(8.3432f, 9.0f, 7.0f, 7.6568f, 7.0f, 6.0f)
                close()
                moveTo(5.0087f, 11.0f)
                curveTo(3.9032f, 11.0f, 3.0f, 11.8869f, 3.0f, 13.0f)
                curveTo(3.0f, 14.6912f, 3.8328f, 15.9663f, 5.135f, 16.7966f)
                curveTo(6.417f, 17.614f, 8.1453f, 18.0f, 10.0f, 18.0f)
                curveTo(11.8547f, 18.0f, 13.583f, 17.614f, 14.865f, 16.7966f)
                curveTo(16.1672f, 15.9663f, 17.0f, 14.6912f, 17.0f, 13.0f)
                curveTo(17.0f, 11.8956f, 16.1045f, 11.0f, 15.0f, 11.0f)
                lineTo(5.0087f, 11.0f)
                close()
                moveTo(4.0f, 13.0f)
                curveTo(4.0f, 12.4467f, 4.4479f, 12.0f, 5.0087f, 12.0f)
                lineTo(15.0f, 12.0f)
                curveTo(15.5522f, 12.0f, 16.0f, 12.4478f, 16.0f, 13.0f)
                curveTo(16.0f, 14.3088f, 15.3777f, 15.2837f, 14.3274f, 15.9534f)
                curveTo(13.2568f, 16.636f, 11.7351f, 17.0f, 10.0f, 17.0f)
                curveTo(8.2649f, 17.0f, 6.7432f, 16.636f, 5.6726f, 15.9534f)
                curveTo(4.6223f, 15.2837f, 4.0f, 14.3088f, 4.0f, 13.0f)
                close()
            }
        }
                .build()
        return _medium!!
    }

private var _medium: ImageVector? = null
