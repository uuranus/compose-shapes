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

class RhombusShape(
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
        val path = Path()
        val centerX = size.width / 2
        val centerY = size.height / 2

        val startEndAngleDegree = atan(
            centerY / centerX
        ).toDouble().toDegree()

        val topDownAngleDegree = 90.0 - startEndAngleDegree

        val topCircleRadius = top.toPx(size, density)
        val topCircleCenter = Point(
            centerX, topCircleRadius / sin((topDownAngleDegree).toRadian()).toFloat()
        )

        val startCircleRadius = start.toPx(size, density)
        val startCircleCenter = Point(
            startCircleRadius / sin((startEndAngleDegree).toRadian()).toFloat(),
            centerY
        )

        val endCircleRadius = end.toPx(size, density)
        val endCircleCenter = Point(
            size.width - endCircleRadius / sin((startEndAngleDegree).toRadian()).toFloat(),
            centerY
        )

        val bottomCircleRadius = bottom.toPx(size, density)
        val bottomCircleCenter = Point(
            centerX,
            size.height - bottomCircleRadius / sin((topDownAngleDegree).toRadian()).toFloat()
        )

        path.apply {
            arcTo(
                rect = Rect(
                    offset = Offset(
                        topCircleCenter.x - topCircleRadius,
                        topCircleCenter.y - topCircleRadius
                    ),
                    size = Size(
                        topCircleRadius * 2, topCircleRadius * 2,
                    )
                ),
                startAngleDegrees = (topDownAngleDegree - 180f).toFloat(),
                sweepAngleDegrees = 180f - 2 * topDownAngleDegree.toFloat(),
                forceMoveTo = false

            )

            arcTo(
                rect = Rect(
                    offset = Offset(
                        endCircleCenter.x - endCircleRadius,
                        endCircleCenter.y - endCircleRadius
                    ),
                    size = Size(
                        endCircleRadius * 2, endCircleRadius * 2,
                    )
                ),
                startAngleDegrees = (startEndAngleDegree - 90f).toFloat(),
                sweepAngleDegrees = 180f - 2 * startEndAngleDegree.toFloat(),
                forceMoveTo = false
            )

            arcTo(
                rect = Rect(
                    offset = Offset(
                        bottomCircleCenter.x - bottomCircleRadius,
                        bottomCircleCenter.y - bottomCircleRadius
                    ),
                    size = Size(
                        bottomCircleRadius * 2, bottomCircleRadius * 2,
                    )
                ),
                startAngleDegrees = (topDownAngleDegree).toFloat(),
                sweepAngleDegrees = 180f - 2 * topDownAngleDegree.toFloat(),
                forceMoveTo = false

            )

            arcTo(
                rect = Rect(
                    offset = Offset(
                        startCircleCenter.x - startCircleRadius,
                        startCircleCenter.y - startCircleRadius
                    ),
                    size = Size(
                        startCircleRadius * 2, startCircleRadius * 2,
                    )
                ),
                startAngleDegrees = (90f + startEndAngleDegree).toFloat(),
                sweepAngleDegrees = 180f - 2 * startEndAngleDegree.toFloat(),
                forceMoveTo = false
            )

            close()
        }

        return Outline.Generic(path)
    }
}


fun RhombusShape(cornerSize: CornerSize) =
    RhombusShape(cornerSize, cornerSize, cornerSize, cornerSize)

fun RhombusShape(size: Dp) =
    RhombusShape(CornerSize(size))

fun RhombusShape(
    top: Dp = 0.dp,
    start: Dp = 0.dp,
    end: Dp = 0.dp,
    bottom: Dp = 0.dp,
) = RhombusShape(
    top = CornerSize(top),
    start = CornerSize(start),
    end = CornerSize(end),
    bottom = CornerSize(bottom)
)

