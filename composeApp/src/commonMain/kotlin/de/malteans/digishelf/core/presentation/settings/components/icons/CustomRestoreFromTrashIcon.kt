package de.malteans.digishelf.core.presentation.settings.components.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val CustomRestoreFromTrashIcon: ImageVector
    get() {
        if (_CustomRestoreFromTrashIcon != null) {
            return _CustomRestoreFromTrashIcon!!
        }
        _CustomRestoreFromTrashIcon = ImageVector.Builder(
            name = "Restore_from_trash",
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
                moveTo(440f, 640f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(-166f)
                lineToRelative(64f, 62f)
                lineToRelative(56f, -56f)
                lineToRelative(-160f, -160f)
                lineToRelative(-160f, 160f)
                lineToRelative(56f, 56f)
                lineToRelative(64f, -62f)
                close()
                moveTo(280f, 840f)
                quadToRelative(-33f, 0f, -56.5f, -23.5f)
                reflectiveQuadTo(200f, 760f)
                verticalLineToRelative(-520f)
                horizontalLineToRelative(-40f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(200f)
                verticalLineToRelative(-40f)
                horizontalLineToRelative(240f)
                verticalLineToRelative(40f)
                horizontalLineToRelative(200f)
                verticalLineToRelative(80f)
                horizontalLineToRelative(-40f)
                verticalLineToRelative(520f)
                quadToRelative(0f, 33f, -23.5f, 56.5f)
                reflectiveQuadTo(680f, 840f)
                close()
                moveToRelative(400f, -600f)
                horizontalLineTo(280f)
                verticalLineToRelative(520f)
                horizontalLineToRelative(400f)
                close()
                moveToRelative(-400f, 0f)
                verticalLineToRelative(520f)
                close()
            }
        }.build()
        return _CustomRestoreFromTrashIcon!!
    }

private var _CustomRestoreFromTrashIcon: ImageVector? = null
