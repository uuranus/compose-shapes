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
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

class RoundedStarPolygonShape(
    private val numOfPoints: Int = 5,
    private val innerRadiusRatio: Float = 0.5f,
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
        val innerCircleAngle = 2.0 * PI / (numOfPoints * 2)

        val totalPoints = numOfPoints * 2

        val innerPointAngleDegree = (totalPoints - 2) * 180f / totalPoints
        val outerPointAngleDegree =
            innerPointAngleDegree - (360f - innerPointAngleDegree * 2)

        var currentCenterAngle = 0.0

        val vertices = mutableListOf<Pair<Float, Float>>()
        for (i in 0 until numOfPoints * 2) {
            val r = if (i % 2 == 0) outerRadius else innerRadius
            val x = centerX + (r * sin(currentCenterAngle)).toFloat()
            val y = centerY - (r * cos(currentCenterAngle)).toFloat()
            vertices.add(Pair(x, y))
            currentCenterAngle += innerCircleAngle
        }

        val dx = vertices[1].first - vertices[0].first
        val dy = vertices[1].second - vertices[0].second
        val polygonDist = sqrt(dx * dx + dy * dy)

        val outerCornerRadius = minOf(
            outerCornerSize.toPx(size, density),
            polygonDist * tan((outerPointAngleDegree / 2f).toRadian())
        )

        val innerCornerRadius = minOf(
            innerCornerSize.toPx(size, density),
            polygonDist * tan((innerPointAngleDegree / 2f).toRadian())
        )

        for (i in vertices.indices) {
            val (x, y) = vertices[i]
            val (prevX, prevY) = vertices[(i + vertices.size - 1) % vertices.size]
            val (nextX, nextY) = vertices[(i + 1) % vertices.size]

            val curPointAngleDegree =
                if (i % 2 == 0) outerPointAngleDegree else innerPointAngleDegree

            val curCornerRadius =
                if (i % 2 == 0) outerCornerRadius else innerCornerRadius

            val circleTangentDist = curCornerRadius / tan((curPointAngleDegree / 2f).toRadian())

            val tangentRatio = circleTangentDist / polygonDist

            val anchor1 = Pair(
                prevX + (x - prevX) * (1 - tangentRatio),
                prevY + (y - prevY) * (1 - tangentRatio)
            )

            val anchor2 = Pair(
                x + (nextX - x) * (tangentRatio),
                y + (nextY - y) * (tangentRatio)
            )

            var middleX =
                ((nextX - x) / (nextY - y)) * anchor2.first + anchor2.second - ((x - prevX) / (y - prevY)) * anchor1.first - anchor1.second
            middleX /= ((nextX - x) / (nextY - y) - (x - prevX) / (y - prevY))

            val middleY =
                -((x - prevX) / (y - prevY)) * middleX + ((x - prevX) / (y - prevY)) * anchor1.first + anchor1.second

            val circleCenter = Pair(
                middleX,
                middleY
            )

            val startAngleDegree = atan2(
                anchor1.second.toDouble() - circleCenter.second.toDouble(),
                anchor1.first.toDouble() - circleCenter.first.toDouble(),
            ).toFloat().toDegree()

            val endAngleDegree = atan2(
                anchor2.second.toDouble() - circleCenter.second.toDouble(),
                anchor2.first.toDouble() - circleCenter.first.toDouble()
            ).toFloat().toDegree()

            var sweepAngle = if (endAngleDegree < startAngleDegree) {
                startAngleDegree - endAngleDegree
            } else {
                endAngleDegree - startAngleDegree
            }

            if (sweepAngle > 180f) {
                sweepAngle = 360f - sweepAngle
            }

            val arcRadius = sqrt(
                (circleCenter.first - anchor1.first) * (circleCenter.first - anchor1.first)
                        + (circleCenter.second - anchor1.second) * (circleCenter.second - anchor1.second)
            )

            path.arcTo(
                rect = Rect(
                    offset = Offset(
                        circleCenter.first - arcRadius,
                        circleCenter.second - arcRadius
                    ),
                    size = Size(arcRadius * 2, arcRadius * 2)
                ),
                startAngleDegrees = startAngleDegree,
                sweepAngleDegrees = if (i % 2 == 0) sweepAngle else -sweepAngle,
                forceMoveTo = false
            )
        }

        path.close()

        return Outline.Generic(path)
    }
}


fun RoundedStarPolygonShape(
    numOfPoints: Int, innerRadiusRatio: Float, cornerSize: CornerSize,
) =
    RoundedStarPolygonShape(numOfPoints, innerRadiusRatio, cornerSize, cornerSize)

fun RoundedStarPolygonShape(
    numOfPoints: Int, innerRadiusRatio: Float, size: Dp,
) =
    RoundedStarPolygonShape(numOfPoints, innerRadiusRatio, CornerSize(size))

fun RoundedStarPolygonShape(
    numOfPoints: Int,
    innerRadius: Float,
    outCornerSize: Dp = 0.dp,
    inCornerSize: Dp = 0.dp,
) = RoundedStarPolygonShape(
    numOfPoints = numOfPoints,
    innerRadiusRatio = innerRadius,
    outerCornerSize = CornerSize(outCornerSize),
    innerCornerSize = CornerSize(inCornerSize)
)

private fun Float.toRadian(): Float {
    return (this * PI / 180).toFloat()
}

private fun Float.toDegree(): Float {
    return (this * 180 / PI).toFloat()
}
