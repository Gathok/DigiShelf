package de.malteans.digishelf.core.presentation.add.scanner

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val CustomFlashlightOffIcon: ImageVector
    get() {
        if (_CustomFlashlightOffIcon != null) {
            return _CustomFlashlightOffIcon!!
        }
        _CustomFlashlightOffIcon = ImageVector.Builder(
            name = "Flashlight_off",
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
                moveTo(792f, 904f)
                lineTo(640f, 752f)
                verticalLineToRelative(128f)
                horizontalLineTo(320f)
                verticalLineToRelative(-448f)
                lineTo(56f, 168f)
                lineToRelative(56f, -56f)
                lineToRelative(736f, 736f)
                close()
                moveTo(400f, 800f)
                horizontalLineToRelative(160f)
                verticalLineToRelative(-128f)
                lineTo(400f, 512f)
                close()
                moveToRelative(240f, -274f)
                lineToRelative(-80f, -80f)
                verticalLineToRelative(-30f)
                lineToRelative(80f, -120f)
                verticalLineToRelative(-16f)
                horizontalLineTo(394f)
                lineToRelative(-80f, -80f)
                horizontalLineToRelative(326f)
                verticalLineToRelative(-40f)
                horizontalLineTo(274f)
                lineToRelative(-34f, -34f)
                verticalLineToRelative(-46f)
                horizontalLineToRelative(480f)
                verticalLineToRelative(240f)
                lineToRelative(-80f, 120f)
                close()
                moveTo(497f, 383f)
            }
        }.build()
        return _CustomFlashlightOffIcon!!
    }

private var _CustomFlashlightOffIcon: ImageVector? = null

val CustomFlashlightOnIcon: ImageVector
    get() {
        if (_CustomFlashlightOnIcon != null) {
            return _CustomFlashlightOnIcon!!
        }
        _CustomFlashlightOnIcon = ImageVector.Builder(
            name = "Flashlight_on",
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
                moveTo(320f, 880f)
                verticalLineToRelative(-440f)
                lineToRelative(-80f, -120f)
                verticalLineToRelative(-240f)
                horizontalLineToRelative(480f)
                verticalLineToRelative(240f)
                lineToRelative(-80f, 120f)
                verticalLineToRelative(440f)
                close()
                moveToRelative(160f, -260f)
                quadToRelative(-25f, 0f, -42.5f, -17.5f)
                reflectiveQuadTo(420f, 560f)
                reflectiveQuadToRelative(17.5f, -42.5f)
                reflectiveQuadTo(480f, 500f)
                reflectiveQuadToRelative(42.5f, 17.5f)
                reflectiveQuadTo(540f, 560f)
                reflectiveQuadToRelative(-17.5f, 42.5f)
                reflectiveQuadTo(480f, 620f)
                moveTo(320f, 200f)
                horizontalLineToRelative(320f)
                verticalLineToRelative(-40f)
                horizontalLineTo(320f)
                close()
                moveToRelative(320f, 80f)
                horizontalLineTo(320f)
                verticalLineToRelative(16f)
                lineToRelative(80f, 120f)
                verticalLineToRelative(384f)
                horizontalLineToRelative(160f)
                verticalLineToRelative(-384f)
                lineToRelative(80f, -120f)
                close()
                moveTo(480f, 480f)
            }
        }.build()
        return _CustomFlashlightOnIcon!!
    }

private var _CustomFlashlightOnIcon: ImageVector? = null
