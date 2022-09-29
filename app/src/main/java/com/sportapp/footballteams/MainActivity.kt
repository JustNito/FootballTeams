package com.sportapp.footballteams

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sportapp.footballteams.ui.screen.teaminfo.TeamInfoScreen
import com.sportapp.footballteams.ui.screen.teaminfo.TeamInfoViewModel
import com.sportapp.footballteams.ui.screen.teaminfo.TeamInfoViewModelFactory
import com.sportapp.footballteams.ui.screen.teaminfo.TeamInfoViewModelFactory_Factory
import com.sportapp.footballteams.ui.screen.teamlist.TeamListScreen
import com.sportapp.footballteams.ui.screen.teamlist.TeamListViewModel
import com.sportapp.footballteams.ui.screen.teamlist.TeamListViewModelFactory
import com.sportapp.footballteams.ui.theme.FootballTeamsTheme
import com.sportapp.footballteams.ui.utils.Screen
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var teamListViewModelFactory: TeamListViewModelFactory
    private val teamListViewModel: TeamListViewModel by viewModels {
        teamListViewModelFactory
    }
    
    @Inject
    lateinit var teamInfoViewModelFactory: TeamInfoViewModelFactory
    private val teamInfoViewModel: TeamInfoViewModel by viewModels {  
        teamInfoViewModelFactory
    }
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApp).appComponent.inject(this)
        //teamListViewModel.prepareTeams()
        super.onCreate(savedInstanceState)
        setContent {
            FootballTeamsTheme {
                FootballApp(
                    teamListViewModel = teamListViewModel,
                    teamInfoViewModel = teamInfoViewModel
                )
            }
        }
    }
}

@Composable
fun FootballApp(
    teamListViewModel: TeamListViewModel,
    teamInfoViewModel: TeamInfoViewModel
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.TeamList.name
    ) {
        composable(Screen.TeamList.name) {
            TeamListScreen(teamListViewModel = teamListViewModel) {
                teamInfoViewModel.initInfo(it)
                navController.navigate(Screen.TeamInfo.name)
            }
        }
        composable(Screen.TeamInfo.name) {
            TeamInfoScreen(teamInfoViewModel = teamInfoViewModel)
        }
    }
}
