package com.microsoft.fluentui.icons.avataricons.presence.busy.large

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.icons.avataricons.presence.busy.LargeGroup

val LargeGroup.Dark: ImageVector
    get() {
        if (_dark != null) {
            return _dark!!
        }
        _dark = Builder(name = "Dark", defaultWidth = 20.0.dp, defaultHeight = 20.0.dp,
                viewportWidth = 20.0f, viewportHeight = 20.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(10.0f, 1.0f)
                lineTo(10.0f, 1.0f)
                arcTo(9.0f, 9.0f, 0.0f, false, true, 19.0f, 10.0f)
                lineTo(19.0f, 10.0f)
                arcTo(9.0f, 9.0f, 0.0f, false, true, 10.0f, 19.0f)
                lineTo(10.0f, 19.0f)
                arcTo(9.0f, 9.0f, 0.0f, false, true, 1.0f, 10.0f)
                lineTo(1.0f, 10.0f)
                arcTo(9.0f, 9.0f, 0.0f, false, true, 10.0f, 1.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 2.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(10.0f, 10.0f)
                moveToRelative(-9.0f, 0.0f)
                arcToRelative(9.0f, 9.0f, 0.0f, true, true, 18.0f, 0.0f)
                arcToRelative(9.0f, 9.0f, 0.0f, true, true, -18.0f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFFD74553)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(18.0f, 10.0f)
                curveTo(18.0f, 14.4183f, 14.4183f, 18.0f, 10.0f, 18.0f)
                curveTo(5.5817f, 18.0f, 2.0f, 14.4183f, 2.0f, 10.0f)
                curveTo(2.0f, 5.5817f, 5.5817f, 2.0f, 10.0f, 2.0f)
                curveTo(14.4183f, 2.0f, 18.0f, 5.5817f, 18.0f, 10.0f)
                close()
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 2.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(10.0f, 1.0f)
                lineTo(10.0f, 1.0f)
                arcTo(9.0f, 9.0f, 0.0f, false, true, 19.0f, 10.0f)
                lineTo(19.0f, 10.0f)
                arcTo(9.0f, 9.0f, 0.0f, false, true, 10.0f, 19.0f)
                lineTo(10.0f, 19.0f)
                arcTo(9.0f, 9.0f, 0.0f, false, true, 1.0f, 10.0f)
                lineTo(1.0f, 10.0f)
                arcTo(9.0f, 9.0f, 0.0f, false, true, 10.0f, 1.0f)
                close()
            }
        }
                .build()
        return _dark!!
    }

private var _dark: ImageVector? = null
