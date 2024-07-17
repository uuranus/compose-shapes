package com.uuranus.variousshapes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
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
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
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
                    var innerRadiusRatio by remember { mutableFloatStateOf(0.4f) }
                    var numOfPoints by remember { mutableFloatStateOf(5f) }
                    var smoothing by remember { mutableFloatStateOf(0.0f) }
                    var outerCornerSize by remember { mutableFloatStateOf(0.0f) }
                    var innerCornerSize by remember { mutableFloatStateOf(0.0f) }
                    var startSkewed by remember { mutableFloatStateOf(0.2f) }
                    var endSkewed by remember { mutableFloatStateOf(0.2f) }

                    var top by remember { mutableStateOf(0f) }
                    var start by remember { mutableStateOf(0f) }
                    var end by remember { mutableStateOf(0f) }
                    var bottom by remember { mutableStateOf(0f) }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Center
                    ) {

                        Box(
                            modifier = Modifier.weight(0.4f)
                        ) {

                        }

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(0.5f)
                                .background(
                                    Color.Gray,
                                    shape = ParallelogramShape(
                                        skewed = 0.2f,
                                        cornerStyle = CornerStyle.CUT,
                                        cornerSize = CornerSize(24.dp)
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
                                "numOfPoints"
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Slider(
                                value = numOfPoints,
                                onValueChange = {
                                    numOfPoints = it
                                }, valueRange = 3f..20f
                            )
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
                                }, valueRange = 0f..1f
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "outerCornerRadius"
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Slider(
                                value = outerCornerSize,
                                onValueChange = {
                                    outerCornerSize = it
                                }, valueRange = 0f..30f,
                                steps = 30
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "innerCornerRadius"
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Slider(
                                value = innerCornerSize,
                                onValueChange = {
                                    innerCornerSize = it
                                }, valueRange = 0f..30f,
                                steps = 30
                            )
                        }

//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(vertical = 12.dp),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Text(
//                                "startCornerRadius"
//                            )
//                            Spacer(modifier = Modifier.width(4.dp))
//                            Slider(
//                                value = start,
//                                onValueChange = {
//                                    start = it
//                                }, valueRange = 0f..100f
//                            )
//                        }
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(vertical = 12.dp),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Text(
//                                "endCornerRadius"
//                            )
//                            Spacer(modifier = Modifier.width(4.dp))
//                            Slider(
//                                value = end,
//                                onValueChange = {
//                                    end = it
//                                }, valueRange = 0f..100f
//                            )
//                        }
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(vertical = 12.dp),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Text(
//                                "bottomCornerRadius"
//                            )
//                            Spacer(modifier = Modifier.width(4.dp))
//                            Slider(
//                                value = bottom,
//                                onValueChange = {
//                                    bottom = it
//                                }, valueRange = 0f..100f
//                            )
//                        }

                        Box(
                            modifier = Modifier.weight(0.4f)
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


        }
    }
}

