package com.uuranus.variousshapes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
                    color = MaterialTheme.colorScheme.background
                ) {
                    var innerRadiusRatio by remember { mutableFloatStateOf(0.5f) }
                    var numOfPoints by remember { mutableFloatStateOf(5f) }
                    var skewed by remember { mutableFloatStateOf(0.2f) }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Center
                    ) {

                        Box(
                            modifier = Modifier.weight(0.3f)
                        )

                        RoundedParallelogramShape(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.4f),
                            skewed = skewed,
                            cornerRadius = 32.dp
                        )


                        Slider(
                            modifier = Modifier.padding(vertical = 36.dp),
                            value = skewed,
                            onValueChange = {
                                skewed = it
                            }
                        )

//                        Slider(
//                            modifier = Modifier.padding(vertical = 36.dp),
//                            value = numOfPoints,
//                            onValueChange = {
//                                numOfPoints = it
//                            }, valueRange = 3f..20f
//                        )

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

            RoundedParallelogramShape(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                skewed = 0.2f,
                cornerRadius = 32.dp
            )
        }
    }
}