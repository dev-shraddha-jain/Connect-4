package com.groot.connect4.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.groot.connect4.R
import com.groot.connect4.di.Opponent.OPPONENT_AI
import com.groot.connect4.di.Opponent.OPPONENT_PLAYER
import com.groot.connect4.di.Opponent.OPPONENT_SELF
import com.groot.connect4.navigation.Route
import com.groot.connect4.ui.theme.Connect4Theme

@Composable
fun GameConfigScreen(navController: NavHostController) {


    val selectedOpponent = remember { mutableStateOf(OPPONENT_PLAYER) }
    val selectedFirstPlayer = remember { mutableStateOf(OPPONENT_SELF) }

    val selectedColor = remember { mutableStateOf(R.color.red) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .weight(0.8f)
                .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier.size(70.dp),
                    painter = painterResource(id = R.drawable.ic_launcher_web),
                    contentDescription = "logo"
                )

                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.headlineLarge
                )
            }

            Spacer(modifier = Modifier.height(70.dp))

            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = stringResource(id = R.string.play_with),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(25.dp))

            // choose opponent
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                OpponentSelectorBox(OPPONENT_AI, selectedOpponent)

                OpponentSelectorBox(OPPONENT_PLAYER, selectedOpponent)
            }

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = stringResource(id = R.string.choose_your_color),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(25.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                ColorSelectorCircle(R.color.red, selectedColor)

                Spacer(modifier = Modifier.width(25.dp))

                ColorSelectorCircle(R.color.yellow, selectedColor)
            }

            Spacer(modifier = Modifier.height(25.dp))


            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = stringResource(id = R.string.first_turn),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(25.dp))

            //first turn
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                OpponentSelectorBox(OPPONENT_SELF, selectedFirstPlayer)

                if (selectedOpponent.value == OPPONENT_PLAYER) {
                    OpponentSelectorBox(OPPONENT_PLAYER, selectedFirstPlayer)
                } else {
                    OpponentSelectorBox(OPPONENT_AI, selectedFirstPlayer)
                }

            }


        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp),
            shape = RoundedCornerShape(0.dp),
            onClick = {
                navController.navigate(Route.gameScreen)
            }) {
            Text(
                text = stringResource(id = R.string.play),
                style = MaterialTheme.typography.titleLarge,

                )

        }
    }

}

fun getPlayerResId(player: String): Int {
    return when (player) {
        OPPONENT_AI -> R.string.opponent_ai
        OPPONENT_PLAYER -> R.string.opponent_player
        OPPONENT_SELF -> R.string.you
        else -> R.string.opponent_player
    }

}

@Composable
fun ColorSelectorCircle(colorId: Int, selectedColor: MutableState<Int>) {
    val border = if (selectedColor.value == colorId) {
        R.color.colorAccent
    } else {
        colorId
    }

    PlayerCircle(border, colorId) {
        selectedColor.value = colorId
    }
}

@Composable
fun PlayerCircle(border: Int, colorId: Int, function: () -> Unit) {
    Text(
        modifier = Modifier
            .size(50.dp)
            .border(2.dp, colorResource(id = border), CircleShape)
            .background(colorResource(id = colorId), CircleShape)
            .clickable {
                function()
            },
        text = "",
        style = MaterialTheme.typography.bodyLarge

    )
}

@Composable

private fun OpponentSelectorBox(player: String, selectedOpponent: MutableState<String>) {
    val border = if (selectedOpponent.value == player) {
        R.color.colorAccent
    } else {
        R.color.white
    }


    Row(
        modifier = Modifier
            .width(150.dp)
            .border(1.dp, colorResource(id = R.color.colorPrimaryDark))
            .background(colorResource(id = border))
            .clickable {
                selectedOpponent.value = player
            },
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            text = stringResource(id = getPlayerResId(player)),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center

        )
    }
}


@Preview(showBackground = true)
@Composable
fun GameConfigScreenPreview() {
    Connect4Theme {
        GameConfigScreen(navController = rememberNavController())
    }
}