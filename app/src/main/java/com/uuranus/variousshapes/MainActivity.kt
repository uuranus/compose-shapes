package com.uuranus.variousshapes

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uuranus.variousshapes.ui.theme.VariousShapesTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VariousShapesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    var innerRadiusRatio by remember { mutableFloatStateOf(0.5f) }
                    var numOfPoints by remember { mutableFloatStateOf(5f) }
                    var smoothing by remember { mutableFloatStateOf(0.0f) }
                    var outerCornerSize by remember { mutableFloatStateOf(0.0f) }
                    var innerCornerSize by remember { mutableFloatStateOf(0.0f) }
                    var skewed by remember { mutableFloatStateOf(0.2f) }

                    var topStart by remember { mutableStateOf(0) }
                    var topEnd by remember { mutableStateOf(0) }
                    var bottomStart by remember { mutableStateOf(0) }
                    var bottomEnd by remember { mutableStateOf(0) }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Center
                    ) {

                        Box(
                            modifier = Modifier.weight(0.3f)
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.5f)
                                .background(
                                    Color.Gray,
                                    shape = RoundedStarPolygonShape(
                                        numOfPoints = numOfPoints.toInt(),
                                        innerRadius = innerRadiusRatio,
                                        outCornerSize = outerCornerSize.toInt().dp,
                                        inCornerSize = innerCornerSize.toInt().dp,
                                        smoothing = smoothing
                                    )
                                )
                        ) {
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "innerRadiusRatio"
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Slider(
                                value = innerRadiusRatio,
                                onValueChange = {
                                    innerRadiusRatio = it
                                }
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "numOfPoints"
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Slider(
                                value = numOfPoints,
                                onValueChange = {
                                    numOfPoints = it
                                },
                                valueRange = 1f..100f
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "outerCornerSize"
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Slider(
                                value = outerCornerSize,
                                onValueChange = {
                                    outerCornerSize = it
                                }, valueRange = 0f..100f
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "innerCornerSize"
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Slider(
                                value = innerCornerSize,
                                onValueChange = {
                                    innerCornerSize = it
                                },valueRange = 0f..100f
                            )
                        }

                        Box(
                            modifier = Modifier.weight(0.3f)
                        )

                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VariousShapesTheme {
        var innerRadiusRatio by remember { mutableFloatStateOf(2.5f) }

        Column {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .background(Color.Gray, shape = StarPolygonShape(numOfPoints = 5))
            )
        }
    }
}