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

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CountDownViewModel : ViewModel() {

    var elapsedTimeInMillis by mutableStateOf(0L)
    var countDownState by mutableStateOf("准备")
    var lastSeconds by mutableStateOf(0)
    private var timer: CountDownTimer? = null

    fun startTimer(totalTime: Long) {
        countDownState = "开始计时"
        timer = object : CountDownTimer(totalTime, 5L) {
            override fun onTick(millisUntilFinished: Long) {
                elapsedTimeInMillis = millisUntilFinished
                lastSeconds = millisToSeconds(millisUntilFinished)
            }

            override fun onFinish() {
                countDownState = "结束"
            }
        }.start()
    }
}
