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
import kotlin.math.PI
import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class StarPolygonShape(
    private val numOfPoints: Int,
    private val innerRadiusRatio: Float,
    private val outerCornerSize: CornerSize,
    private val innerCornerSize: CornerSize,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {

        val path = Path()

        if (numOfPoints == 0) {
            return Outline.Generic(path)
        }

        val centerX = size.width / 2
        val centerY = size.height / 2
        val outerRadius = min(centerX, centerY)
        val innerRadius = outerRadius * innerRadiusRatio

        val theta = PI / numOfPoints

        var currentCenterAngle = 0.0

        val vertices = mutableListOf<Point>()
        for (i in 0 until numOfPoints * 2) {
            val r = if (i % 2 == 0) outerRadius else innerRadius
            val x = centerX + (r * sin(currentCenterAngle)).toFloat()
            val y = centerY - (r * cos(currentCenterAngle)).toFloat()
            vertices.add(Point(x, y))
            currentCenterAngle += theta
        }

        val (cX, cY) = vertices[0]
        val (pX, pY) = vertices[(vertices.size - 1) % vertices.size]
        val (nX, nY) = vertices[1]
        val (nnX, nnY) = vertices[2]

        val anglePrev = PI / 2 - atan((pY - cY) / (cX - pX))

        val angleNext = PI / 2 - atan((nY - cY) / (nX - cX))

        val outerVertexHalfAngle = (anglePrev + angleNext) / 2
        val outerVertexHalfAngleDegree = outerVertexHalfAngle.toDegree()

        val anglePrevInner = PI - atan((nY - cY) / (nX - cX))

        val angleNextInner = atan((nnY - nY) / (nnX - nX))

        val innerVertexHalfAngle = (anglePrevInner + angleNextInner) / 2
        val innerVertexHalfAngleDegree = innerVertexHalfAngle.toDegree()

        val outerCornerRadius = minOf(
            outerCornerSize.toPx(size, density),
            outerRadius
        )

        val innerCornerRadius = minOf(
            innerCornerSize.toPx(size, density),
            innerRadius
        )

        currentCenterAngle = 0.0

        for (i in vertices.indices) {
            val (x, y) = vertices[i]

            val curPointHalfAngleDegree =
                if (i % 2 == 0) outerVertexHalfAngleDegree else innerVertexHalfAngleDegree

            val curCornerRadius =
                if (i % 2 == 0) outerCornerRadius else innerCornerRadius
            val curRadius = if (i % 2 == 0) outerRadius else innerRadius

            val circleSinDist = curCornerRadius / sin(curPointHalfAngleDegree.toRadian())

            val circleCenter = if (curCornerRadius == 0f) {
                Point(
                    x, y
                )
            } else if (i % 2 == 0) {
                Point(
                    centerX + (curRadius - circleSinDist).toFloat() * sin(currentCenterAngle).toFloat(),
                    centerY - (curRadius - circleSinDist).toFloat() * cos(currentCenterAngle).toFloat()
                )
            } else {
                Point(
                    centerX + (circleSinDist + curRadius).toFloat() * sin(currentCenterAngle).toFloat(),
                    centerY - (circleSinDist + curRadius).toFloat() * cos(currentCenterAngle).toFloat(),
                )
            }

            var centerCurAngle = atan2(
                (y - circleCenter.y.toDouble()),
                (x - circleCenter.x.toDouble())
            ).toDegree()

            centerCurAngle = if (centerCurAngle < 0f) {
                360f + centerCurAngle
            } else {
                centerCurAngle
            }

            var startAngleDegree = if (i % 2 == 0) {
                centerCurAngle - 90f + curPointHalfAngleDegree
            } else {
                centerCurAngle + 90f - curPointHalfAngleDegree
            }

            if (startAngleDegree < 0f) startAngleDegree += 360f

            val sweepAngle = if (i % 2 == 0) {
                180f - curPointHalfAngleDegree * 2
            } else {
                curPointHalfAngleDegree * 2 - 180f
            }

            path.arcTo(
                rect = Rect(
                    offset = Offset(
                        (circleCenter.x.toDouble() - curCornerRadius).toFloat(),
                        (circleCenter.y.toDouble() - curCornerRadius).toFloat()
                    ),
                    size = Size(curCornerRadius * 2, curCornerRadius * 2)
                ),
                startAngleDegrees = startAngleDegree.toFloat(),
                sweepAngleDegrees = sweepAngle.toFloat(),
                forceMoveTo = false
            )

            currentCenterAngle += theta
        }

        path.close()

        return Outline.Generic(path)
    }
}


fun StarPolygonShape(
    numOfPoints: Int, innerRadiusRatio: Float, cornerSize: CornerSize,
) =
    StarPolygonShape(numOfPoints, innerRadiusRatio, cornerSize, cornerSize)

fun StarPolygonShape(
    numOfPoints: Int, innerRadiusRatio: Float, size: Dp,
) =
    StarPolygonShape(numOfPoints, innerRadiusRatio, CornerSize(size))

fun StarPolygonShape(
    numOfPoints: Int,
    innerRadiusRatio: Float,
    outerCornerSize: Dp = 0.dp,
    innerCornerSize: Dp = 0.dp,
) = StarPolygonShape(
    numOfPoints = numOfPoints,
    innerRadiusRatio = innerRadiusRatio,
    outerCornerSize = CornerSize(outerCornerSize),
    innerCornerSize = CornerSize(innerCornerSize)
)
