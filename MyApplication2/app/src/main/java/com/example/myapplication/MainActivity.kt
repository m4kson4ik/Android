package com.example.myapplication
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ContentProvider.AuditionProvider
import com.example.myapplication.Room.AuditionDB
import com.example.myapplication.View.ListAudition
import com.example.myapplication.View.UserProfileAudition
import com.example.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint


@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel : AuditionsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        val cursor = contentResolver.query(Uri.parse(AuditionProvider.uriPrefix), null, null, null, null)
        var audition = mutableListOf<AuditionDB>()

        if (cursor!= null)
        {
            if (cursor.moveToFirst())
            {
                    val startDate = cursor.getString(0)
                    val endDate = cursor.getString(1)
                    val numberAudition = cursor.getInt(2)
                    val name = cursor.getString(3)
                    val uri = cursor.getString(4)
                    val numberImage = cursor.getInt(5)
                    audition.add(
                        AuditionDB(
                            startDate,
                            endDate,
                            numberAudition,
                            name,
                            uri,
                            numberImage
                        )
                    )
            }
            cursor.close()
        }

        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val userAudition = UserProfileAudition()
                    val listAudition = ListAudition()
                    NavHost(navController = navController, "list")
                    {
                        listAuditionsNav(navController, viewModel, lifecycleScope, listAudition, audition)
                        auditionsPage(navController, viewModel, lifecycleScope, userAudition)
                    }
                }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.auditionsPage(
    navController: NavHostController,
    viewModel: AuditionsViewModel,
    lifecycleScope: LifecycleCoroutineScope,
    userProfileAudition: UserProfileAudition,
) {

    composable(
        "user/{uid}",
        arguments = listOf(navArgument("uid") { type = NavType.IntType })
    )
    { arguments ->
        val id = arguments.arguments?.getInt("uid")
        if (id != null)
        {
            userProfileAudition.AuditionInfo(
                id,
                viewModel = viewModel,
                navControllerBack = navController,
                lifecycleScope = lifecycleScope,
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.listAuditionsNav(
    navController: NavHostController,
    viewModel: AuditionsViewModel,
    lifecycleScope: LifecycleCoroutineScope,
    listAudition: ListAudition,
    mutableList: MutableList<AuditionDB>
) {
    composable("list")
    {
        listAudition.ListAudition(navController = navController, viewModel = viewModel, lifecycleScope,mutableList)
    }

    composable("create")
    {
        listAudition.create(viewModel, lifecycleScope, navController)
    }
}

