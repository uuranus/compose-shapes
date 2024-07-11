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
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sign
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

class RoundedStarPolygonShape(
    private val numOfPoints: Int = 5,
    private val innerRadiusRatio: Float = 0.5f,
    private val outerCornerSize: CornerSize,
    private val innerCornerSize: CornerSize,
    private val smoothing: Float,
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

        val regularPolygonAngleDegree = (totalPoints - 2) * 180 / totalPoints
        val innerPointAngleDegree = 360f - regularPolygonAngleDegree
        val outerPointAngleDegree =
            regularPolygonAngleDegree - (360f - regularPolygonAngleDegree * 2)

        var currentAngle = 0.0

        val vertices = mutableListOf<Pair<Float, Float>>()
        for (i in 0 until numOfPoints * 2) {
            val r = if (i % 2 == 0) outerRadius else innerRadius
            val x = centerX + (r * sin(currentAngle)).toFloat()
            val y = centerY - (r * cos(currentAngle)).toFloat()
            vertices.add(Pair(x, y))
            currentAngle += innerCircleAngle

        }

        val dx = vertices[1].first - vertices[0].first
        val dy = vertices[1].second - vertices[0].second
        val polygonDist = sqrt(dx * dx + dy * dy)


        for (i in vertices.indices) {
            val (x, y) = vertices[i]
            val (prevX, prevY) = vertices[(i + vertices.size - 1) % vertices.size]
            val (nextX, nextY) = vertices[(i + 1) % vertices.size]

            var curAngleDegree = if (i % 2 == 0) outerPointAngleDegree else innerPointAngleDegree
            if (curAngleDegree > 180f) curAngleDegree = 360 - curAngleDegree

            val curCornerRadius =
                if (i % 2 == 0) outerCornerSize.toPx(size, density) else innerCornerSize.toPx(
                    size,
                    density
                )

            val curRadius = if (i % 2 == 0) outerRadius else innerRadius

            val circleTangentDist = curCornerRadius / tan((curAngleDegree / 2).toRadian())
            val circleSinDist = curCornerRadius / sin((curAngleDegree / 2).toRadian())

            val tangentRatio = circleTangentDist / polygonDist
            val curDx = x - centerX
            val curDy = y - centerY

            val circleMiddleDist =
                if (i % 2 == 0) outerRadius - circleSinDist + curCornerRadius
                else innerRadius + circleSinDist - curCornerRadius

            val circleMiddleCenter = Pair(
                centerX + (curDx * circleMiddleDist) / curRadius,
                centerY + (curDy * circleMiddleDist) / curRadius,
            )

            val anchor1 = Pair(
                prevX + (x - prevX) * (1 - tangentRatio),
                prevY + (y - prevY) * (1 - tangentRatio)
            )

            val anchor2 = Pair(
                x + (nextX - x) * tangentRatio,
                y + (nextY - y) * tangentRatio
            )

            if (i == 0) {
                path.moveTo(anchor1.first, anchor1.second)
            } else {
                path.lineTo(anchor1.first, anchor1.second)
            }

            path.quadraticBezierTo(
                x1 = circleMiddleCenter.first,
                y1 = circleMiddleCenter.second,
                x2 = anchor2.first,
                y2 = anchor2.second
            )

        }

        path.close()

        return Outline.Generic(path)
    }
}


fun RoundedStarPolygonShape(
    numOfPoints: Int, innerRadiusRatio: Float, cornerSize: CornerSize,
    smoothing: Float,
) =
    RoundedStarPolygonShape(numOfPoints, innerRadiusRatio, cornerSize, cornerSize, smoothing)

fun RoundedStarPolygonShape(
    numOfPoints: Int, innerRadiusRatio: Float, size: Dp,
    smoothing: Float,
) =
    RoundedStarPolygonShape(numOfPoints, innerRadiusRatio, CornerSize(size), smoothing)

fun RoundedStarPolygonShape(
    numOfPoints: Int,
    innerRadius: Float,
    outCornerSize: Dp = 0.dp,
    inCornerSize: Dp = 0.dp,
    smoothing: Float,
) = RoundedStarPolygonShape(
    numOfPoints = numOfPoints,
    innerRadiusRatio = innerRadius,
    outerCornerSize = CornerSize(outCornerSize),
    innerCornerSize = CornerSize(inCornerSize),
    smoothing = smoothing,
)

private fun Float.toRadian(): Float {
    return (this * PI / 180).toFloat()
}

private fun getCenterPoint(
    centerDist: Float,
    x1: Float,
    y1: Float,
    x2: Float,
    y2: Float,
): Pair<Float, Float> {

    val dx = x2 - x1
    val dy = y2 - y1
    val dist = sqrt(dx * dx + dy * dy)

    val centerRatio = centerDist / dist

    val centerX = x1 + dist * centerRatio
    val centerY = y1 + dist * centerRatio

    return Pair(centerX, centerY)
}

fun getRatioPoint(
    p1: Pair<Float, Float>,
    p2: Pair<Float, Float>,
    ratio: Float,
): Pair<Float, Float> {
    val x = (p1.first + p2.first) / 2
    val y = (p1.second + p2.second) / 2
    val controlX = x + (p2.first - p1.first) * ratio
    val controlY = y + (p2.second - p1.second) * ratio
    return Pair(controlX, controlY)
}