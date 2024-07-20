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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uuranus.variousshapes.ui.theme.VariousShapesTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VariousShapesTheme {

                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    var corner by remember { mutableStateOf(0f) }

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
                                .weight(0.3f)
                                .background(
                                    Color.Gray,
                                    shape = TrapezoidShape(
                                        skewed = 0.3f,
                                        cornerStyle = CornerStyle.ROUNDED,
                                        cornerSize = CornerSize(0.dp),
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
                                "cornerRadius"
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Slider(
                                value = corner,
                                onValueChange = {
                                    corner = it
                                }, valueRange = 0f..30f,
                                steps = 30
                            )
                        }

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

        Box(
            modifier = Modifier
                .width(400.dp)
                .height(400.dp)
                .background(
                    Color.Gray,
                    shape = RingShape(
                        ringWidth = 3.dp
                    )
                )
        )
    }
}

