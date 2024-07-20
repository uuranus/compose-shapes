package com.uuranus.variousshapes

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class RectangleShape(
    private val cornerStyle: CornerStyle,
    private val topStart: CornerSize,
    private val topEnd: CornerSize,
    private val bottomEnd: CornerSize,
    private val bottomStart: CornerSize,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {

        val path = when (cornerStyle) {
            CornerStyle.ROUNDED -> {
                drawRoundedRectangleShape(
                    size = size,
                    topStart = topStart.toPx(size, density),
                    topEnd = topEnd.toPx(size, density),
                    bottomStart = bottomStart.toPx(size, density),
                    bottomEnd = bottomEnd.toPx(size, density),
                )
            }

            CornerStyle.INNER_ROUNDED -> {
                drawInnerRoundedRectangleShape(
                    size = size,
                    topStart = topStart.toPx(size, density),
                    topEnd = topEnd.toPx(size, density),
                    bottomStart = bottomStart.toPx(size, density),
                    bottomEnd = bottomEnd.toPx(size, density),
                )
            }

            CornerStyle.CUT -> {
                drawCutRectangleShape(
                    size = size,
                    topStart = topStart.toPx(size, density),
                    topEnd = topEnd.toPx(size, density),
                    bottomStart = bottomStart.toPx(size, density),
                    bottomEnd = bottomEnd.toPx(size, density),
                )
            }

        }

        return Outline.Generic(path)
    }
}

private fun drawRoundedRectangleShape(
    size: Size,
    topStart: Float,
    topEnd: Float,
    bottomEnd: Float,
    bottomStart: Float,
): Path {
    val path = Path()
    val width = size.width
    val height = size.height

    path.apply {

        arcTo(
            rect = Rect(
                offset = Offset(
                    0f,
                    0f
                ),
                size = Size(topStart * 2, topStart * 2)
            ),
            startAngleDegrees = 180f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        arcTo(
            rect = Rect(
                offset = Offset(
                    width - topEnd * 2,
                    0f
                ),
                size = Size(topEnd * 2, topEnd * 2)
            ),
            startAngleDegrees = 270f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        arcTo(
            rect = Rect(
                offset = Offset(
                    width - bottomEnd * 2,
                    height - bottomEnd * 2
                ),
                size = Size(
                    bottomEnd * 2,
                    bottomEnd * 2
                )
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        arcTo(
            rect = Rect(
                offset = Offset(
                    0f,
                    height - bottomStart * 2
                ),
                size = Size(
                    bottomStart * 2,
                    bottomStart * 2
                )
            ),
            startAngleDegrees = 90f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        close()
    }
    return path
}

private fun drawInnerRoundedRectangleShape(
    size: Size,
    topStart: Float,
    topEnd: Float,
    bottomEnd: Float,
    bottomStart: Float,
): Path {
    val path = Path()
    val width = size.width
    val height = size.height

    path.apply {

        arcTo(
            rect = Rect(
                offset = Offset(
                    -topStart,
                    -topStart
                ),
                size = Size(topStart * 2, topStart * 2)
            ),
            startAngleDegrees = 90f,
            sweepAngleDegrees = -90f,
            forceMoveTo = false
        )

        arcTo(
            rect = Rect(
                offset = Offset(
                    width - topEnd,
                    -topEnd
                ),
                size = Size(topEnd * 2, topEnd * 2)
            ),
            startAngleDegrees = 180f,
            sweepAngleDegrees = -90f,
            forceMoveTo = false
        )

        arcTo(
            rect = Rect(
                offset = Offset(
                    width - bottomEnd,
                    height - bottomEnd
                ),
                size = Size(
                    bottomEnd * 2,
                    bottomEnd * 2
                )
            ),
            startAngleDegrees = 270f,
            sweepAngleDegrees = -90f,
            forceMoveTo = false
        )

        arcTo(
            rect = Rect(
                offset = Offset(
                    -bottomStart,
                    height - bottomStart
                ),
                size = Size(
                    bottomStart * 2,
                    bottomStart * 2
                )
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = -90f,
            forceMoveTo = false
        )

        close()
    }
    return path
}

private fun drawCutRectangleShape(
    size: Size,
    topStart: Float,
    topEnd: Float,
    bottomEnd: Float,
    bottomStart: Float,
): Path {
    val path = Path()
    val width = size.width
    val height = size.height

    path.apply {
        moveTo(topStart, 0f)

        lineTo(width - topEnd, 0f)

        lineTo(width, topEnd)

        lineTo(width, height - bottomEnd)

        lineTo(width - bottomEnd, height)

        lineTo(bottomStart, height)

        lineTo(0f, height - bottomStart)

        lineTo(0f, topStart)

        lineTo(topStart, 0f)

        close()
    }
    return path
}

fun RectangleShape(
    cornerStyle: CornerStyle = CornerStyle.ROUNDED,
    cornerSize: CornerSize = CornerSize(0.dp),
) =
    RectangleShape(
        cornerStyle = cornerStyle,
        cornerSize,
        cornerSize,
        cornerSize,
        cornerSize
    )

fun RectangleShape(cornerStyle: CornerStyle = CornerStyle.ROUNDED, size: Dp = 0.dp) =
    RectangleShape(cornerStyle, CornerSize(size))

fun RectangleShape(
    cornerStyle: CornerStyle = CornerStyle.ROUNDED,
    topStart: Dp = 0.dp,
    topEnd: Dp = 0.dp,
    bottomEnd: Dp = 0.dp,
    bottomStart: Dp = 0.dp,
) = RectangleShape(
    cornerStyle = cornerStyle,
    topStart = CornerSize(topStart),
    topEnd = CornerSize(topEnd),
    bottomEnd = CornerSize(bottomEnd),
    bottomStart = CornerSize(bottomStart)
)
