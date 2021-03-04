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
package com.example.androiddevchallenge.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.example.androiddevchallenge.calculateRadiusOffset
import kotlin.math.min

@Composable
fun TimerCircleComponent(
    modifier: Modifier = Modifier,
    screenWidthDp: Int,
    screenHeightDp: Int,
    time: String,
    state: String,
    elapsedTime: Long,
    totalTime: Long
) {
    val maxRadius by remember { mutableStateOf(min(screenHeightDp, screenWidthDp)) }
    Box(
        modifier = modifier
            .size(maxRadius.dp)
            .padding(8.dp)
    ) {
        val constraints = portraitConstraints()
        ConstraintLayout(modifier = Modifier.fillMaxSize(), constraintSet = constraints) {
            Text(
                modifier = Modifier.layoutId("timerText"),
                text = time,
                style = MaterialTheme.typography.h2,
                color = Color.White
            )

            Text(
                modifier = Modifier.layoutId("workoutStateText"),
                text = state,
                style = MaterialTheme.typography.h5,
                color = Color.White
            )
        }

        if (screenWidthDp.dp < 600.dp) {
            TimerCircle(
                modifier = modifier,
                elapsedTime = elapsedTime,
                totalTime = totalTime
            )
        }
    }
}

private fun portraitConstraints(): ConstraintSet {
    return ConstraintSet {
        val timerText = createRefFor("timerText")
        val workoutStateText = createRefFor("workoutStateText")

        constrain(timerText) {
            centerHorizontallyTo(parent)
            centerVerticallyTo(parent)
        }
        constrain(workoutStateText) {
            centerHorizontallyTo(parent)
            bottom.linkTo(timerText.top, 8.dp)
        }
    }
}

@Composable
fun TimerCircle(
    modifier: Modifier = Modifier,
    elapsedTime: Long,
    totalTime: Long
) {
    androidx.compose.foundation.Canvas(
        modifier = modifier.fillMaxSize(),
        onDraw = {
            val height = size.height
            val width = size.width
            val dotDiameter = 12.dp
            val strokeSize = 30.dp
            val radiusOffset = calculateRadiusOffset(strokeSize.value, dotDiameter.value, 0f)

            val xCenter = width / 2f
            val yCenter = height / 2f
            val radius = min(xCenter, yCenter)
            val arcWidthHeight = ((radius - radiusOffset) * 2f)
            val arcSize = Size(arcWidthHeight, arcWidthHeight)

            val remainderColor = Color.White.copy(alpha = 0.25f)
            val completedColor = Color(0xFFFF8000)

            val whitePercent =
                min(1f, elapsedTime.toFloat() / totalTime.toFloat())
            val greenPercent = 1 - whitePercent

            drawArc(
                completedColor,
                270f,
                -greenPercent * 360f,
                false,
                topLeft = Offset(radiusOffset, radiusOffset),
                size = arcSize,
                style = Stroke(width = strokeSize.value, cap = StrokeCap.Round)
            )

            drawArc(
                remainderColor,
                270f,
                whitePercent * 360,
                false,
                topLeft = Offset(radiusOffset, radiusOffset),
                size = arcSize,
                style = Stroke(width = strokeSize.value)
            )
        }
    )
}
