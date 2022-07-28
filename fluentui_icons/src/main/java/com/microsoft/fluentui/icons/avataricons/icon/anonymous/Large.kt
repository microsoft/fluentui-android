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

val AnonymousGroup.Large: ImageVector
    get() {
        if (_large != null) {
            return _large!!
        }
        _large = Builder(name = "Large", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF616161)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(17.7542f, 13.9999f)
                curveTo(18.9962f, 13.9999f, 20.003f, 15.0068f, 20.003f, 16.2488f)
                verticalLineTo(16.8242f)
                curveTo(20.003f, 17.7185f, 19.6835f, 18.5833f, 19.1019f, 19.2627f)
                curveTo(17.5326f, 21.0962f, 15.1454f, 22.0011f, 12.0f, 22.0011f)
                curveTo(8.8541f, 22.0011f, 6.4681f, 21.0959f, 4.9018f, 19.2617f)
                curveTo(4.3221f, 18.5828f, 4.0035f, 17.7193f, 4.0035f, 16.8265f)
                verticalLineTo(16.2488f)
                curveTo(4.0035f, 15.0068f, 5.0104f, 13.9999f, 6.2524f, 13.9999f)
                horizontalLineTo(17.7542f)
                close()
                moveTo(17.7542f, 15.4999f)
                horizontalLineTo(6.2524f)
                curveTo(5.8388f, 15.4999f, 5.5035f, 15.8352f, 5.5035f, 16.2488f)
                verticalLineTo(16.8265f)
                curveTo(5.5035f, 17.3622f, 5.6947f, 17.8802f, 6.0425f, 18.2876f)
                curveTo(7.2958f, 19.7553f, 9.2617f, 20.5011f, 12.0f, 20.5011f)
                curveTo(14.7383f, 20.5011f, 16.7059f, 19.7553f, 17.9624f, 18.2873f)
                curveTo(18.3113f, 17.8797f, 18.503f, 17.3608f, 18.503f, 16.8242f)
                verticalLineTo(16.2488f)
                curveTo(18.503f, 15.8352f, 18.1678f, 15.4999f, 17.7542f, 15.4999f)
                close()
                moveTo(12.0f, 2.0046f)
                curveTo(14.7614f, 2.0046f, 17.0f, 4.2432f, 17.0f, 7.0046f)
                curveTo(17.0f, 9.7661f, 14.7614f, 12.0046f, 12.0f, 12.0046f)
                curveTo(9.2386f, 12.0046f, 7.0f, 9.7661f, 7.0f, 7.0046f)
                curveTo(7.0f, 4.2432f, 9.2386f, 2.0046f, 12.0f, 2.0046f)
                close()
                moveTo(12.0f, 3.5046f)
                curveTo(10.067f, 3.5046f, 8.5f, 5.0716f, 8.5f, 7.0046f)
                curveTo(8.5f, 8.9376f, 10.067f, 10.5046f, 12.0f, 10.5046f)
                curveTo(13.933f, 10.5046f, 15.5f, 8.9376f, 15.5f, 7.0046f)
                curveTo(15.5f, 5.0716f, 13.933f, 3.5046f, 12.0f, 3.5046f)
                close()
            }
        }
                .build()
        return _large!!
    }

private var _large: ImageVector? = null
