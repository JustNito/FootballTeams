package com.sportapp.footballteams

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.sportapp.footballteams.ui.theme.FootballTeamsTheme
import com.sportapp.footballteams.ui.utils.LoadingScreen
import com.sportapp.footballteams.ui.utils.Status

class PreloaderActivity : ComponentActivity() {

    private val preloaderViewModel: PreloaderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preloaderViewModel.wait()
        setContent {
            FootballTeamsTheme {
                val context = LocalContext.current
                if(preloaderViewModel.status == Status.LOADING) {
                    Box(
                        modifier = Modifier.fillMaxSize().background(Color.Black),

                    ) {
                        LoadingScreen(
                            modifier = Modifier.fillMaxSize(),
                            useInversion = true,
                            useProgress = false
                        )
                    }
                } else {
                    LaunchedEffect(key1 = preloaderViewModel.status) {
                        context.startActivity(Intent(context, MainActivity::class.java))
                    }
                }
            }
        }
    }
}
