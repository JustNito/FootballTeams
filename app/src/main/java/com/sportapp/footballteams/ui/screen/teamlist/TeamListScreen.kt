package com.sportapp.footballteams.ui.screen.teamlist

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.sportapp.footballteams.R
import com.sportapp.footballteams.ui.model.Team
import com.sportapp.footballteams.ui.theme.FootballTeamsTheme
import com.sportapp.footballteams.ui.utils.ErrorMessage
import com.sportapp.footballteams.ui.utils.LoadingScreen
import com.sportapp.footballteams.ui.utils.Status


@Composable
fun TeamListScreen(
    teamListViewModel: TeamListViewModel,
    toTeamInfoScreen: (Team) -> Unit
) {
    if(teamListViewModel.status == Status.LOADING || teamListViewModel.status == Status.OK) {
        TeamList(
            teams = teamListViewModel.teams,
            status = teamListViewModel.status,
            searchLine = teamListViewModel.searchLine,
            onSearchBarChange = teamListViewModel::onSearchBarChange,
            toTeamInfoScreen = toTeamInfoScreen
        )
    } else {
        ErrorMessage(
            status = teamListViewModel.status,
            tryAgain = teamListViewModel::tryAgain
        )
    }


}


@Composable
fun TeamList(
    teams: List<Team>,
    status: Status,
    searchLine: String,
    onSearchBarChange: (String) -> Unit,
    toTeamInfoScreen: (Team) -> Unit
) {
    val context = LocalContext.current
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { openCustomTab(context) },
                text = { Text("Privacy Policy") }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            SearchBar(
                searchText = searchLine,
                onSearchBarChange = onSearchBarChange
            )
            if(status != Status.LOADING) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(items = teams) {
                        TeamCard(
                            team = it,
                            toTeamInfoScreen = toTeamInfoScreen
                        )
                    }
                }
            } else {
                LoadingScreen(
                    modifier = Modifier.fillMaxSize(),
                    useProgress = false
                )
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    searchText: String,
    onSearchBarChange: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        value = searchText,
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = { keyboardController?.hide() }
        ),
        trailingIcon = {
            IconButton(onClick = { keyboardController?.hide() }) {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "searchButton"
                )
            }
        },
        onValueChange = {
            onSearchBarChange(it)
        }
    )
}

@Composable
fun TeamCard(
    team: Team,
    toTeamInfoScreen: (Team) -> Unit
) {
    val context = LocalContext.current
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable {
                toTeamInfoScreen(team)
            },
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .padding(4.dp)
                    .size(80.dp),
                shape = RoundedCornerShape(100)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        contentScale = ContentScale.FillBounds,
                        model = ImageRequest
                            .Builder(context)
                            .decoderFactory(factory = SvgDecoder.Factory())
                            .data(team.flag)
                            .build(),
                        contentDescription = "background flag",
                        alpha = 0.8f
                    )
                    if (team.logo != null) {
                        Surface(
                            modifier = Modifier.padding(8.dp),
                            shape = RoundedCornerShape(100),
                            color = MaterialTheme.colors.surface.copy(alpha = 0.8f)
                        ) {
                            AsyncImage(
                                modifier = Modifier.padding(4.dp),
                                model = team.logo,
                                contentDescription = "team logo",
                            )
                        }
                    }
                }
            }
            Column(modifier = Modifier
                .padding(4.dp)
                .weight(1f)) {
                Text(
                    text = team.name,
                    style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
                )
                Text(text = team.country ?: "")
            }
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.BottomEnd
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = team.code ?: ""
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTeamCard() {
    FootballTeamsTheme {
        TeamCard(
            Team(
                id = 0,
                name = "Manchester United",
                code = "MUN",
                country = "England",
                logo = "Logo"
            ),
            toTeamInfoScreen = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTeamList() {
    FootballTeamsTheme {
        TeamList(
            teams = listOf(
                Team(
                    id = 0,
                    name = "Manchester United",
                    code = "MUN",
                    country = "England",
                    flag = null,
                    logo = null
                )
            ),
            searchLine = "",
            onSearchBarChange = {},
            toTeamInfoScreen = {},
            status = Status.OK
        )
    }
}

fun openCustomTab(context: Context) {
    val url = context.getString(R.string.custom_tab_url)
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(context, Uri.parse(url))
}