package com.example.myapplication.View

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.AuditionsViewModel
import com.example.myapplication.Class.CreateDateSimple
import com.example.myapplication.R
import com.example.myapplication.Room.AuditionDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File

class UserProfileAudition {
    @Composable
    fun BottomBarUserProfile(navController: NavHostController, navControllerBack: NavHostController)
    {
        NavigationBar {
            NavigationBarItem(
                selected = true,
                onClick = { navController.navigate("profile") },
                label = { Text(text = stringResource(R.string.profile)) },
                icon = { Icon(Icons.Filled.AccountCircle, "") })
            NavigationBarItem(
                selected = true,
                onClick = { navController.navigate("edit") },
                label = { Text(text = stringResource(R.string.Edition)) },
                icon = { Icon(Icons.Filled.Edit, "") })
            NavigationBarItem(
                selected = true,
                onClick = { navControllerBack.navigate("list") },
                label = {Text(stringResource(R.string.Exit))},
                icon = { Icon(Icons.Filled.ExitToApp, "") })
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun AuditionInfo(
        id: Int?,
        viewModel: AuditionsViewModel,
        navControllerBack: NavHostController,
        lifecycleScope: CoroutineScope,
    ) {
        val navController = rememberNavController()
        val auditions by viewModel.listAuditionMutableStateFlow.collectAsState(initial = emptyList())
        val audition = auditions.firstOrNull { it.uid == id }
        if (audition != null) {
            val userProfileAudition = UserProfileAudition()
            NavHost(navController, startDestination = "profile")
            {
                composable("profile")
                {
                    userProfileAudition.UserProfileAudition(
                        navController,
                        navControllerBack,
                        audition
                    )
                }

                composable("edit")
                {
                    userProfileAudition.EditingUserProfile(
                        navController,
                        navControllerBack,
                        audition,
                        viewModel,
                        lifecycleScope,
                    )
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("NotConstructor")
    @Composable
    fun UserProfileAudition(navController: NavHostController, navControllerBack: NavHostController, audition: AuditionDB)
    {
        Scaffold (
            bottomBar = {
                BottomBarUserProfile(navController, navControllerBack)
            }
        ) { pad ->
            Column(Modifier.padding(pad)) {

            }
            Column()
            {
                Text(stringResource(R.string.teacher)+"- ${audition.name} ")
                Text(stringResource(R.string.numberAudition)+"${audition.numberAudition}")
                Text(stringResource(R.string.dateAndTimeStart)+"- ${audition.startDate}")
                Text(stringResource(R.string.dateAndTimeEnd)+"- ${audition.endDate}")

                if (audition.uri != null)
                {
                    Image(
                        painter = rememberAsyncImagePainter(audition.uri),
                        contentDescription = ""
                    )
                }
                else
                {
                    Text("Фотографии нет")
                }
            }
        }
    }

    fun message(navController: NavController, text :String)
    {
        Toast.makeText(
            navController.context,
            text,
            Toast.LENGTH_LONG
        ).show()
    }
    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun EditingUserProfile(navController: NavHostController, navControllerBack: NavHostController, audition: AuditionDB, viewModel: AuditionsViewModel, scope: CoroutineScope)
    {
        var imageLoaded by remember {
            mutableStateOf(false)
        }

        val getLocalTime = CreateDateSimple()
        Scaffold (
            bottomBar = {
                BottomBarUserProfile(navController, navControllerBack)
            }
        ) { pad -> Column(Modifier.padding(pad)) {
            var name by rememberSaveable { mutableStateOf(audition.name) }
            var numberAudition by rememberSaveable { mutableStateOf(audition.numberAudition.toString()) }
            var startDate by rememberSaveable { mutableStateOf(audition.startDate)}
            var endDate by rememberSaveable {
                    mutableStateOf(
                        audition.endDate
                    )
                }

            var photo by remember {
                mutableStateOf("")
            }

            TextField(value = numberAudition,
                onValueChange = { numberAudition = it },
                label = { Text(stringResource(R.string.FieldNumberAudition)) })
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(R.string.FieldName)) }
            )

            TextField(
                value = startDate,
                onValueChange = { startDate = it },
                label = { Text(stringResource(R.string.FieldDateAndTimeStart)) }
            )

            TextField(
                value = endDate,
                onValueChange = { endDate = it },
                label = { Text(stringResource(R.string.FieldDateAndTimeEnd)) }
            )

            var uri : Uri? = null
            var numberImage by remember {
                mutableIntStateOf(audition.numberImage!!)
            }
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.TakePicture(),
                onResult = {
                    photo = uri.toString()
                    imageLoaded = it
                    scope.launch {
                        viewModel.update(audition , name, numberAudition.toInt(), startDate, endDate, photo, numberImage++)
                    }
            } )

            val launcherPermission = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult ={
                    if (!it) {
                        Toast.makeText(navController.context, "Права не ваданы", Toast.LENGTH_LONG)
                    }
                } )



            Button(onClick =
            {
                val res = navController.context.checkSelfPermission("android.permission.CAMERA")
                if (res == PackageManager.PERMISSION_GRANTED)
                {
                    val fileDel = File(filesPath, "imageUser${audition.uid}-${numberImage}.jpg")
                    fileDel.delete()
                    numberImage++
                    val file = File(filesPath, "imageUser${audition.uid}-${numberImage}.jpg")
                    uri = FileProvider.getUriForFile(navController.context, "audition.provider", file)
                    launcher.launch(uri)
                }
                else
                {
                    launcherPermission.launch("android.permission.CAMERA")
                }
            }) {
                Text(text = "Сделать фото")
            }

            Button(onClick = {
                val date1 = getLocalTime.checkDate(startDate)
                val date2 = getLocalTime.checkDate(endDate)
                if (name.isNotEmpty() && numberAudition.isNotEmpty() && numberAudition.toInt() > 0 && date1 != null && date2 != null) {

                scope.launch {
                    viewModel.update(audition , name, numberAudition.toInt(), startDate, endDate, null, numberImage++)
                }
                    navController.navigate("profile")
                    message(navController, "Аудитория отредактирована!")
                }
                else
                {
                    message(navController, "Error")
                }
            }) {
                Text(stringResource(R.string.Edition))
            }

            if (imageLoaded) {
                val photo = rememberAsyncImagePainter(model = photo)
                Image(photo, "")
            }
        }
        }
    }

    companion object
    {
        @SuppressLint("SdCardPath")
        const val filesPath = "/data/user/0/com.example.myapplication/files/"
    }
}