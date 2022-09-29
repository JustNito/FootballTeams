package com.sportapp.footballteams.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sportapp.footballteams.R


@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    progress: Int = 0,
    maxValue: Int = 0,
    useInversion: Boolean = false,
    useProgress: Boolean
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            if (useProgress)
                CircularProgressIndicator(
                    modifier = Modifier.size(60.dp),
                    progress = convertProgressToFloat(progress, maxValue)
                )
            else
                CircularProgressIndicator(
                    modifier = Modifier.size(60.dp)
                )
            Image(
                modifier = Modifier.size(45.dp),
                painter = painterResource(id = R.drawable.ball),
                contentDescription = "ball"
            )
        }
        Text(
            modifier = Modifier.padding(4.dp),
            color = if(useInversion) Color.White else Color.Black,
            text =
            if(useProgress)
                "Loading ${(convertProgressToFloat(progress,maxValue) * 100).toInt()}%"
            else "Loading",
        )
    }
}

@Composable
fun ErrorMessage(status: Status, tryAgain: () -> Unit){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = "Error"
        )
        Button(
            modifier = Modifier.padding(all = 8.dp),
            onClick = tryAgain
        ) {
            Text("Try again")
        }
    }
}
private fun convertProgressToFloat(amount: Int, maxValue: Int) =
    amount.toFloat() / maxValue.toFloat()

@Preview(showBackground = true)
@Composable
fun PreviewLoadingScreen() {
    LoadingScreen(useProgress = false)
}