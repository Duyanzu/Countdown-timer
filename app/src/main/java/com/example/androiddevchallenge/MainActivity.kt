/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.ButtonRow
import com.example.androiddevchallenge.ui.TimerCircleComponent
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {
    val viewModel: CountDownViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setUpStatusBar(android.graphics.Color.parseColor("#00000000"), 1)
        super.onCreate(savedInstanceState)
        var totalTime = 10000L
        setContent {
            MyTheme {
                val configuration = LocalConfiguration.current
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    TimerCircleComponent(
                        modifier = Modifier.layoutId("progCircle"),
                        screenWidthDp = configuration.screenWidthDp,
                        screenHeightDp = configuration.screenHeightDp,
                        time = viewModel.lastSeconds.toString(),
                        state = viewModel.countDownState,
                        elapsedTime = viewModel.elapsedTimeInMillis,
                        totalTime = totalTime
                    )
                    ButtonRow(
                        modifier = Modifier
                            .layoutId("buttonRow")
                            .padding(0.dp, 20.dp),
                        buttonWidth = (0.8 * configuration.screenWidthDp).dp,
                        sendCommand = {
                            viewModel.startTimer(totalTime)
                        }
                    )
                }
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    Surface(color = MaterialTheme.colors.background) {

    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}


