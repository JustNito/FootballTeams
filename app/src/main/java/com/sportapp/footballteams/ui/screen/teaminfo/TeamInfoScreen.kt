package com.sportapp.footballteams.ui.screen.teaminfo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.sportapp.footballteams.R
import com.sportapp.footballteams.ui.model.Player
import com.sportapp.footballteams.ui.model.Team
import com.sportapp.footballteams.ui.utils.ErrorMessage
import com.sportapp.footballteams.ui.utils.LoadingScreen
import com.sportapp.footballteams.ui.utils.Status

@Composable
fun TeamInfoScreen(
    teamInfoViewModel: TeamInfoViewModel
) {
    when(teamInfoViewModel.status) {
        Status.OK -> TeamInfo(
            team = teamInfoViewModel.team,
            players = teamInfoViewModel.players
        )
        Status.LOADING -> LoadingScreen(
            modifier = Modifier.fillMaxSize(),
            useProgress = false
        )
        else -> ErrorMessage(
            status = teamInfoViewModel.status,
            tryAgain = teamInfoViewModel::tryAgain
        )
    }
}

@Composable
fun TeamInfo(
    team: Team,
    players: List<Player>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
    ) {
        Header(team = team)
        if(players.isNotEmpty()) {
            PlayersList(players = players)
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No Information",
                    style = MaterialTheme.typography.h3.copy(color = Color.Gray)
                )
            }
        }
    }
}

@Composable
fun Header(team: Team) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box() {
            AsyncImage(
                modifier = Modifier.size(150.dp),
                model = team.logo,
                contentDescription = "Team Logo"
            )
        }
        Text(
            text = team.name,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = team.country ?: "",
                style = MaterialTheme.typography.h5
            )
            AsyncImage(
                modifier = Modifier.size(30.dp),
                model = ImageRequest
                    .Builder(context)
                    .decoderFactory(factory = SvgDecoder.Factory())
                    .data(team.flag)
                    .build(),
                contentDescription = "Flag"
            )
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayersList(
    players: List<Player>
) {
    val weights = listOf(
        .15f,
        .30f,
        .15f,
        .20f,
        .20f,
    )
    Surface(
        modifier = Modifier.padding(4.dp),
        elevation = 4.dp
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            stickyHeader {
                Surface(
                    elevation = 4.dp
                ) {
                    Row() {
                        Spacer(
                            modifier = Modifier.weight(weights[0])
                        )
                        Text(
                            modifier = Modifier.weight(weights[1]),
                            text = "Name",
                            style = MaterialTheme.typography.subtitle2
                        )
                        Text(
                            modifier = Modifier.weight(weights[2]),
                            text = "Age",
                            style = MaterialTheme.typography.subtitle2
                        )
                        Text(
                            modifier = Modifier.weight(weights[3]),
                            text = "Height",
                            style = MaterialTheme.typography.subtitle2
                        )
                        Text(
                            modifier = Modifier.weight(weights[4]),
                            text = "Weight",
                            style = MaterialTheme.typography.subtitle2
                        )
                    }
                }
            }
            items(items = players) { player ->
                PlayerInfo(player = player, weights = weights)
                Divider()
            }
        }
    }
}

@Composable
fun PlayerInfo(
    player: Player,
    weights: List<Float>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(weights[0]),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.FillBounds,
                model = player.photo,
                contentDescription = "photo"
            )
        }
        Text(
            modifier = Modifier.weight(weights[1]),
            text = player.name,
            style = MaterialTheme.typography.subtitle2
        )
        Text(
            modifier = Modifier.weight(weights[2]),
            text = if(player.age != null) player.age.toString() else "-",
            style = MaterialTheme.typography.subtitle2
        )
        Text(
            modifier = Modifier.weight(weights[3]),
            text = player.height ?: "-",
            style = MaterialTheme.typography.subtitle2
        )
        Text(
            modifier = Modifier.weight(weights[4]),
            text = player.weight ?: "-",
            style = MaterialTheme.typography.subtitle2
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTeamInfo() {
    TeamInfo(
        team = Team(
            id = 0,
            name = "Macnhester United",
            code = "MAN",
            country = "England",
            flag = R.drawable.ic_gb.toString(),
            logo = R.drawable._50px_manchester_united_fc_crest_svg.toString()
        ),
        players = listOf(
            Player(
                name = "Nikita Manzharov",
                age = 22,
                height = "185",
                weight = "85",
                photo = null
            ),
            Player(
                name = "Nikita Manzharov",
                age = 22,
                height = "185",
                weight = "85",
                photo = null
            )

        )
    )
}