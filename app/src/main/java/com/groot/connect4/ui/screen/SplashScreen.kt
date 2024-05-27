package com.groot.connect4.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.groot.connect4.R
import com.groot.connect4.navigation.Route
import com.groot.connect4.ui.theme.Connect4Theme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {

    LaunchedEffect(key1 = Unit) {
        delay(1000)
        navController.navigate(Route.gameScreen)
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_web),
            contentDescription = "logo"
        )

        Text(text = stringResource(id = R.string.app_name))
    }


}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    Connect4Theme {
        SplashScreen(navController = rememberNavController())
    }
}