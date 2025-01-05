package com.groot.connect4.ui.screen

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.groot.connect4.R
import com.groot.connect4.navigation.Route
import com.groot.connect4.ui.theme.Connect4Theme
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navController: NavHostController) {
    val context = LocalContext.current

    val appUpdateManager = remember { AppUpdateManagerFactory.create(context) }
    val appUpdateInfoTask = appUpdateManager.appUpdateInfo
    val appUpdateOptions = AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE)
        .setAllowAssetPackDeletion(true)
        .build()

    val isUpdateAvailable = remember { mutableStateOf(false) }
    val isAppUpdateInProgress = remember { mutableStateOf(false) }
    val appUpdateInfoSt = remember { mutableStateOf<AppUpdateInfo?>(null) }

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        if (result.resultCode != Activity.RESULT_OK) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("Update flow failed! Result code: ${result.resultCode}")
            }
        } else {
            navController.navigate(Route.gameConfigScreen)
        }
    }

    LaunchedEffect(key1 = Unit) {
        appUpdateInfoTask
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                    appUpdateInfoSt.value = appUpdateInfo
                    isUpdateAvailable.value = true
                    if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                        appUpdateManager.startUpdateFlowForResult(appUpdateInfo, launcher, appUpdateOptions)
                    }
                } else {
                    navController.navigate(Route.gameConfigScreen)
                }
            }
            .addOnFailureListener {
                navController.navigate(Route.gameConfigScreen)
            }
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

        if (isAppUpdateInProgress.value) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                text = "App Update In Progress...",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

        }

        if (isUpdateAvailable.value) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CustomButton("Update App", R.color.colorAccent, R.color.white) {
                    isUpdateAvailable.value = false
                    isAppUpdateInProgress.value = true

                    val appUpdateInfo = appUpdateInfoSt.value ?: return@CustomButton
                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo, launcher, appUpdateOptions)
                }

                CustomButton("Not Now", R.color.gray, R.color.gray) {
                    navController.navigate(Route.gameConfigScreen)
                }
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                text = "We have added new features to \nupgrade your experiences \nupdate app to feel the all new experience",
                textAlign = TextAlign.Center
            )

        }
    }


}

@Composable
private fun CustomButton(label: String, borderColor: Int, backgroundColor: Int, function: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .width(150.dp)
            .border(1.dp, colorResource(id = borderColor))
            .background(colorResource(id = backgroundColor))
            .clickable {
                function()
            },
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    Connect4Theme {
        SplashScreen(navController = rememberNavController())
    }
}