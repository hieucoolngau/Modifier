package com.example.madteamb.ui.theme.ext

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.madteamb.model.Timer.TimerModel
import com.example.madteamb.model.Timer.TimerViewModel
import com.example.madteamb.ui.theme.GreenBackGround
import MuneerCircularProgressBar

@Composable
fun TimerHomeScreen(viewModel: TimerViewModel)
{
    val timer by viewModel.viewState.observeAsState(TimerModel())

    Column(
        modifier = Modifier
            .background(GreenBackGround),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

//        TimerHeader()
//        Spacer(modifier = Modifier.height(250.dp))

//        MuneerCircularProgressBar(onProgressChanged = {}, isStartButtonClicked = true)
        TimerTopSection(time = timer.timeDuration.format(), remainingTime = timer.remaingTime)
//        Spacer(modifier =Modifier.height(25.dp))
        TimerButton(viewModel)

    }

}
@Composable
fun TimerTopSection(time:String,remainingTime:Long)
{

        Text(
            text = time,
            fontSize = 60.sp,
            color = Color.White
        )

}



@Composable
fun TimerButton(timerState: TimerViewModel)
{
    val toggle by timerState.viewState.observeAsState()
    Box(
        modifier = Modifier
            .fillMaxWidth()

        )
    {
        ButtonLayout(timerState)
    }
}
@Composable
fun ButtonLayout(timerState: TimerViewModel) {
    var isResetButtonVisible by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth()
        ,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isResetButtonVisible) {
            ResetButton(
                timerState = timerState,
                onClick = {
                    // Perform any actions you need before showing the Start button
                    timerState.resetTimer()
                    // Update the state to show the Start button
                    isResetButtonVisible = false
                }
            )
        } else {
            StartButton(
                onClick = {
                    // Perform any actions you need before showing the Reset button
                    timerState.buttonselection()
                    // Update the state to show the Reset button
                    isResetButtonVisible = true
                }
            )
        }
    }
}

@Composable
fun StartButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(10.dp)
            .size(80.dp, 30.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(GreenBackGround)
    ) {
        Text(
            text = "Start",
            color = Color.White,
            modifier = Modifier.align(Alignment.Center),
            fontSize = 13.sp,

        )
    }
}

@Composable
fun ResetButton(timerState: TimerViewModel, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                onClick()
            }
            .padding(10.dp)
            .size(80.dp, 25.dp)
            .clip(RoundedCornerShape(7.dp))
            .background(GreenBackGround)
            .border(2.dp, GreenBackGround, shape = RoundedCornerShape(5.dp))
            .fillMaxWidth()
    ) {
        Text(
            text = "Cancel", color = Color.White, modifier = Modifier
                .align(Alignment.Center),
            fontSize = 13.sp
        )
    }
}
@Preview
@Composable
fun PreviewButton()
{
    ResetButton(timerState = TimerViewModel(1000)) {

    }
}

@Preview
@Composable
fun Previewtimer()
{
    Timer()
}
@Composable
fun Timer()
{
    var timer:TimerViewModel

    Column {

        var angle by remember {
            mutableStateOf(0.0)
        }
        angle = MuneerCircularProgressBar(onProgressChanged = {}, isStartButtonClicked = true)
        TimerHomeScreen(viewModel = TimerViewModel((angle/3).toLong()*60))
    }
}