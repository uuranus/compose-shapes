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
import kotlin.math.atan
import kotlin.math.sin
import kotlin.math.sqrt

class RhombusShape(
    private val cornerStyle: CornerStyle,
    private val top: CornerSize,
    private val start: CornerSize,
    private val end: CornerSize,
    private val bottom: CornerSize,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {

        val path = when (cornerStyle) {
            CornerStyle.ROUNDED -> {
                drawRoundedRhombusShape(
                    size = size,
                    top = top.toPx(size, density),
                    start = start.toPx(size, density),
                    end = end.toPx(size, density),
                    bottom = bottom.toPx(size, density)
                )
            }

            CornerStyle.INNER_ROUNDED -> {
                drawInnerRoundedRhombusShape(
                    size = size,
                    top = top.toPx(size, density),
                    start = start.toPx(size, density),
                    end = end.toPx(size, density),
                    bottom = bottom.toPx(size, density)
                )
            }

            CornerStyle.CUT -> {
                drawCutRhombusShape(
                    size = size,
                    top = top.toPx(size, density),
                    start = start.toPx(size, density),
                    end = end.toPx(size, density),
                    bottom = bottom.toPx(size, density)
                )
            }
        }

        return Outline.Generic(path)
    }
}

private fun drawRoundedRhombusShape(
    size: Size,
    top: Float,
    start: Float,
    end: Float,
    bottom: Float,
): Path {
    val path = Path()
    val centerX = size.width / 2
    val centerY = size.height / 2

    val startEndHalfAngleDegree = atan(
        centerY / centerX
    ).toDouble().toDegree()

    val topDownHalfAngleDegree = 90.0 - startEndHalfAngleDegree

    val topCircleCenter = Point(
        centerX, top / sin((topDownHalfAngleDegree).toRadian()).toFloat()
    )

    val startCircleCenter = Point(
        start / sin((startEndHalfAngleDegree).toRadian()).toFloat(),
        centerY
    )

    val endCircleCenter = Point(
        size.width - end / sin((startEndHalfAngleDegree).toRadian()).toFloat(),
        centerY
    )

    val bottomCircleCenter = Point(
        centerX,
        size.height - bottom / sin((topDownHalfAngleDegree).toRadian()).toFloat()
    )

    path.apply {
        arcTo(
            rect = Rect(
                offset = Offset(
                    topCircleCenter.x - top,
                    topCircleCenter.y - top
                ),
                size = Size(
                    top * 2, top * 2,
                )
            ),
            startAngleDegrees = (topDownHalfAngleDegree - 180f).toFloat(),
            sweepAngleDegrees = 180f - 2 * topDownHalfAngleDegree.toFloat(),
            forceMoveTo = false

        )

        arcTo(
            rect = Rect(
                offset = Offset(
                    endCircleCenter.x - end,
                    endCircleCenter.y - end
                ),
                size = Size(
                    end * 2, end * 2,
                )
            ),
            startAngleDegrees = (startEndHalfAngleDegree - 90f).toFloat(),
            sweepAngleDegrees = 180f - 2 * startEndHalfAngleDegree.toFloat(),
            forceMoveTo = false
        )

        arcTo(
            rect = Rect(
                offset = Offset(
                    bottomCircleCenter.x - bottom,
                    bottomCircleCenter.y - bottom
                ),
                size = Size(
                    bottom * 2, bottom * 2,
                )
            ),
            startAngleDegrees = (topDownHalfAngleDegree).toFloat(),
            sweepAngleDegrees = 180f - 2 * topDownHalfAngleDegree.toFloat(),
            forceMoveTo = false

        )

        arcTo(
            rect = Rect(
                offset = Offset(
                    startCircleCenter.x - start,
                    startCircleCenter.y - start
                ),
                size = Size(
                    start * 2, start * 2,
                )
            ),
            startAngleDegrees = (90f + startEndHalfAngleDegree).toFloat(),
            sweepAngleDegrees = 180f - 2 * startEndHalfAngleDegree.toFloat(),
            forceMoveTo = false
        )

        close()
    }

    return path
}

private fun drawInnerRoundedRhombusShape(
    size: Size,
    top: Float,
    start: Float,
    end: Float,
    bottom: Float,
): Path {
    val path = Path()
    val width = size.width
    val height = size.height
    val centerX = width / 2
    val centerY = height / 2

    val startEndHalfAngleDegree = atan(
        centerY / centerX
    ).toDouble().toDegree()

    val topDownHalfAngleDegree = 90.0 - startEndHalfAngleDegree

    path.apply {
        arcTo(
            rect = Rect(
                offset = Offset(
                    centerX - top,
                    0f - top
                ),
                size = Size(
                    top * 2, top * 2,
                )
            ),
            startAngleDegrees = (90f + topDownHalfAngleDegree).toFloat(),
            sweepAngleDegrees = (-topDownHalfAngleDegree * 2).toFloat(),
            forceMoveTo = false

        )

        arcTo(
            rect = Rect(
                offset = Offset(
                    width - end,
                    centerY - end
                ),
                size = Size(
                    end * 2, end * 2,
                )
            ),
            startAngleDegrees = (180f + startEndHalfAngleDegree).toFloat(),
            sweepAngleDegrees = (-2 * startEndHalfAngleDegree).toFloat(),
            forceMoveTo = false
        )

        arcTo(
            rect = Rect(
                offset = Offset(
                    centerX - bottom,
                    height - bottom
                ),
                size = Size(
                    bottom * 2, bottom * 2,
                )
            ),
            startAngleDegrees = (270f + topDownHalfAngleDegree).toFloat(),
            sweepAngleDegrees = -2 * topDownHalfAngleDegree.toFloat(),
            forceMoveTo = false
        )

        arcTo(
            rect = Rect(
                offset = Offset(
                    -start,
                    centerY - start
                ),
                size = Size(
                    start * 2, start * 2,
                )
            ),
            startAngleDegrees = (startEndHalfAngleDegree).toFloat(),
            sweepAngleDegrees = -2 * startEndHalfAngleDegree.toFloat(),
            forceMoveTo = false
        )

        close()
    }

    return path
}

private fun drawCutRhombusShape(
    size: Size,
    top: Float,
    start: Float,
    end: Float,
    bottom: Float,
): Path {
    val path = Path()
    val width = size.width
    val height = size.height
    val centerX = width / 2
    val centerY = height / 2

    val dist = sqrt(centerX * centerX + centerY * centerY)

    path.apply {
        moveTo(
            centerX - top * centerX / dist,
            0f + top * centerY / dist
        )

        lineTo(
            centerX + top * centerX / dist,
            0f + top * centerY / dist
        )

        lineTo(
            width - end * centerX / dist,
            centerY - top * centerY / dist
        )

        lineTo(
            width - end * centerX / dist,
            centerY + end * centerY / dist
        )

        lineTo(
            centerX + bottom * centerX / dist,
            height - bottom * centerY / dist
        )

        lineTo(
            centerX - bottom * centerX / dist,
            height - bottom * centerY / dist
        )

        lineTo(
            start * centerX / dist,
            centerY + start * centerY / dist
        )

        lineTo(
            start * centerX / dist,
            centerY - start * centerY / dist
        )

        close()
    }

    return path
}

fun RhombusShape(
    cornerStyle: CornerStyle = CornerStyle.ROUNDED,
    cornerSize: CornerSize = CornerSize(0.dp),
) =
    RhombusShape(cornerStyle, cornerSize, cornerSize, cornerSize, cornerSize)

fun RhombusShape(cornerStyle: CornerStyle = CornerStyle.ROUNDED, size: Dp = 0.dp) =
    RhombusShape(cornerStyle, CornerSize(size))

fun RhombusShape(
    cornerStyle: CornerStyle = CornerStyle.ROUNDED,
    top: Dp = 0.dp,
    start: Dp = 0.dp,
    end: Dp = 0.dp,
    bottom: Dp = 0.dp,
) = RhombusShape(
    cornerStyle = cornerStyle,
    top = CornerSize(top),
    start = CornerSize(start),
    end = CornerSize(end),
    bottom = CornerSize(bottom)
)

