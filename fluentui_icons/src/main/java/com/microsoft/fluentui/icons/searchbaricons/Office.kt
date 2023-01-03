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

val SearchBarIcons.Office: ImageVector
    get() {
        if (_office != null) {
            return _office!!
        }
        _office = Builder(
            name = "Office", defaultWidth = 20.0.dp, defaultHeight = 20.0.dp,
            viewportWidth = 20.0f, viewportHeight = 20.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF242424)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(16.66f, 2.2999f)
                lineTo(10.72f, 0.57f)
                curveTo(10.5614f, 0.5209f, 10.396f, 0.4972f, 10.23f, 0.5f)
                curveTo(9.917f, 0.4975f, 9.6093f, 0.5805f, 9.34f, 0.74f)
                lineTo(2.93f, 4.46f)
                curveTo(2.6467f, 4.6224f, 2.4114f, 4.8569f, 2.248f, 5.1397f)
                curveTo(2.0847f, 5.4225f, 1.9991f, 5.7434f, 2.0f, 6.07f)
                verticalLineTo(14.07f)
                curveTo(2.0f, 14.3087f, 2.0948f, 14.5376f, 2.2636f, 14.7064f)
                curveTo(2.4324f, 14.8752f, 2.6613f, 14.9699f, 2.9f, 14.9699f)
                curveTo(3.0547f, 14.9693f, 3.2064f, 14.9279f, 3.34f, 14.8499f)
                lineTo(5.34f, 13.75f)
                curveTo(5.5516f, 13.6313f, 5.7279f, 13.4587f, 5.8509f, 13.2496f)
                curveTo(5.9738f, 13.0405f, 6.0391f, 12.8025f, 6.04f, 12.56f)
                verticalLineTo(7.06f)
                curveTo(6.0416f, 6.7777f, 6.1296f, 6.5027f, 6.2923f, 6.272f)
                curveTo(6.455f, 6.0413f, 6.6846f, 5.8661f, 6.95f, 5.77f)
                lineTo(11.04f, 4.32f)
                verticalLineTo(15.0f)
                horizontalLineTo(6.49f)
                curveTo(6.2181f, 14.9992f, 5.9537f, 15.0886f, 5.7379f, 15.2541f)
                curveTo(5.5222f, 15.4195f, 5.3674f, 15.6518f, 5.2977f, 15.9146f)
                curveTo(5.228f, 16.1774f, 5.2474f, 16.4559f, 5.3527f, 16.7066f)
                curveTo(5.4581f, 16.9572f, 5.6435f, 17.1659f, 5.88f, 17.3f)
                lineTo(9.38f, 19.3f)
                curveTo(9.649f, 19.4503f, 9.9519f, 19.5295f, 10.26f, 19.53f)
                curveTo(10.426f, 19.5327f, 10.5914f, 19.5091f, 10.75f, 19.46f)
                lineTo(16.75f, 17.74f)
                curveTo(17.1364f, 17.6258f, 17.4756f, 17.3898f, 17.717f, 17.0673f)
                curveTo(17.9585f, 16.7448f, 18.0893f, 16.3529f, 18.09f, 15.95f)
                verticalLineTo(4.08f)
                curveTo(18.0851f, 3.6661f, 17.9416f, 3.2657f, 17.6824f, 2.943f)
                curveTo(17.4231f, 2.6203f, 17.0631f, 2.3939f, 16.66f, 2.2999f)
                close()
                moveTo(17.0f, 15.9199f)
                curveTo(16.9977f, 16.1061f, 16.9365f, 16.2867f, 16.8251f, 16.4359f)
                curveTo(16.7137f, 16.585f, 16.5579f, 16.695f, 16.38f, 16.75f)
                lineTo(12.0f, 18.0f)
                curveTo(12.0f, 17.9f, 12.0f, 17.8f, 12.0f, 17.7f)
                verticalLineTo(2.27f)
                curveTo(12.0053f, 2.1734f, 12.0053f, 2.0765f, 12.0f, 1.9799f)
                lineTo(16.4f, 3.26f)
                curveTo(16.5787f, 3.3102f, 16.7361f, 3.4176f, 16.848f, 3.5656f)
                curveTo(16.96f, 3.7137f, 17.0204f, 3.8944f, 17.02f, 4.08f)
                lineTo(17.0f, 15.9199f)
                close()
            }
        }
            .build()
        return _office!!
    }

private var _office: ImageVector? = null
