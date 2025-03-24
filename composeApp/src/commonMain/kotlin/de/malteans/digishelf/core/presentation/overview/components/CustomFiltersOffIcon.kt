package de.malteans.digishelf.core.presentation.overview.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val CustomFiltersOffIcon: ImageVector
    get() {
        if (_CustomFiltersOffIcon != null) {
            return _CustomFiltersOffIcon!!
        }
        _CustomFiltersOffIcon = ImageVector.Builder(
            name = "Filter_list_off",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(791f, 905f)
                lineTo(55f, 169f)
                lineToRelative(57f, -57f)
                lineToRelative(736f, 736f)
                close()
                moveTo(633f, 520f)
                lineToRelative(-80f, -80f)
                horizontalLineToRelative(167f)
                verticalLineToRelative(80f)
                close()
                moveTo(433f, 320f)
                lineToRelative(-80f, -80f)
                horizontalLineToRelative(487f)
                verticalLineToRelative(80f)
                close()
                moveToRelative(-33f, 400f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(160f)
                verticalLineToRelative(80f)
                close()
                moveTo(240f, 520f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(166f)
                verticalLineToRelative(80f)
                close()
                moveTo(120f, 320f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(86f)
                verticalLineToRelative(80f)
                close()
            }
        }.build()
        return _CustomFiltersOffIcon!!
    }

private var _CustomFiltersOffIcon: ImageVector? = null
