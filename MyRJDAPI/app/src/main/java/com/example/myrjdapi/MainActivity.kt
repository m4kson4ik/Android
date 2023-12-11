package com.example.myrjdapi

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myrjdapi.Model.AllStation.Country
import com.example.myrjdapi.Model.AllStation.Station
import com.example.myrjdapi.Model.Status
import com.example.myrjdapi.Repository.SegmentResponse
import com.example.myrjdapi.Repository.TrainRepositoryAPI
import com.example.myrjdapi.Room.LikedThread
import com.example.myrjdapi.Room.StationDB
import com.example.myrjdapi.ServiceWorker.ServiceDownloadListStation
import com.example.myrjdapi.ViewModel.TrainViewModel
import com.example.myrjdapi.ui.theme.MyRJDAPITheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@HiltAndroidApp
class App : Application()
{

}


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("CoroutineCreationDuringComposition")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyRJDAPITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    val importance = NotificationManager.IMPORTANCE_DEFAULT
//                    val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "", importance)
//                    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//                    notificationManager.createNotificationChannel(channel)

                    val viewModel : TrainViewModel by viewModels()
                    val repository = TrainRepositoryAPI()
                    val intent = Intent(this, ServiceDownloadListStation::class.java)

                    val navController = rememberNavController()
                    val scope = CoroutineScope(Dispatchers.Default)
                    NavHost(navController, "list")
                    {
                        listAuditionsNav(navController, scope, repository, viewModel)
                        startService(intent)
                    }
                    //GetTrain(lifecycleScope, repository)
                }
            }
        }
    }
}

//fun NavGraphBuilder.selectedTrainNav(
//    navController: NavHostController
//)
//{
//
//}


@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.listAuditionsNav(
    navController: NavHostController,
    lifecycleScope: CoroutineScope,
    repository: TrainRepositoryAPI,
    viewModel: TrainViewModel,
) {
    composable("list")
    {

        GetTrain(lifecycleScope = lifecycleScope, repository = repository, navController = navController, viewModel )
    }
    composable("liked")
    {
        LikedList(navController, viewModel, lifecycleScope)
    }
    composable("stationRydom")
    {
        //GetTrain(lifecycleScope = lifecycleScope, repository = repository, navController = navController )
    }
    composable("selectedTrain")
    {
        SelectedStation(repository, lifecycleScope, viewModel, navController)
    }
}

@Composable
fun BottomBarMenu(navController: NavController) {
    NavigationBar {
        NavigationBarItem(selected = true,
            onClick = { navController.navigate("list") },
            icon = { Icon(Icons.Filled.List, "") })
        NavigationBarItem(
            selected = true,
            onClick = { navController.navigate("liked") },
            icon = { Icon(Icons.Filled.FavoriteBorder, "") })
        NavigationBarItem(selected = true,
            onClick = { navController.navigate("stationRydom") },
            icon = { Icon(Icons.Filled.LocationOn, "") })
    }
}

fun findStationByTitle(country: Country?, title: String): List<Station> {
    if (country != null) {
        for (countryItem in country.countries) {
            Log.d("mgkit", countryItem.title)
            for (region in countryItem.regions) {
                Log.d("mgkit", region.title)
                for (settlement in region.settlements) {
                    Log.d("mgkit", "gorod" + settlement.title)
                    if (settlement.title == title) {
                        Log.d("mgkit","return" + settlement.stations.toString())
                        return settlement.stations.filter { it.transport_type == "train" }
                    }
                }
            }
        }
    }
    return emptyList()
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectedStation(
    repository: TrainRepositoryAPI,
    lifecycleScope: CoroutineScope,
    viewModel: TrainViewModel,
    navController: NavController
)
{
    var stationSelected by rememberSaveable {
        mutableStateOf("")
    }
    var listData by remember {
        mutableStateOf(listOf<StationDB>())
    }

    val status by viewModel.listSelectedStation.collectAsState()

    var statusError by rememberSaveable {
        mutableStateOf(Status.None)
    }

    val flowStart by viewModel.startStation.collectAsState()
    val flowEnd by viewModel.endStation.collectAsState()

    val flow by viewModel.listStationMutableStateFlow.collectAsState(initial = emptyList())
    Scaffold(topBar = { Text( when(status)
    {
        com.example.myrjdapi.ViewModel.SelectedStation.START -> "Отправление"
        com.example.myrjdapi.ViewModel.SelectedStation.END -> "Прибытие"
    })}) {
        pad -> Column(Modifier.padding(pad)) {


//        stationSelected = if (status == com.example.myrjdapi.ViewModel.SelectedStation.START) {
//            flowStart.title ?: ""
//        } else {
//            flowEnd.title
//        }

        TextField(value = stationSelected,
            onValueChange = {stationSelected = it},
            label = { Text(text = "Станция или город")})
        Button(onClick = {
        }) {
            Text("Поиск")
        }

        Text(text = when (statusError) {
            Status.Error -> "Ошибка"
            Status.Waiting -> "Ожидание"
            Status.OK -> "Загрузка завершена"
            Status.None -> "Отсутствует"
        })
        Button(onClick = {
            lifecycleScope.launch {
                viewModel.getStation()

//                val flow2 = repository.getStation()
//                lifecycleScope.launch {
//                    flow2.collect {
//                        //val list = findStationByTitle(it.second, "Нахабино")
////                        if (list != null) {
////                           // listData = list.filter { it.departure > LocalDateTime.now().toString() }
////                        }
//                    }
//                }
            }
        }) {
            Text("Получить")
        }
        LazyColumn()
        {
            listData = if (stationSelected.isNotEmpty()) {
                flow.filter { it.title?.contains(stationSelected, ignoreCase = true) ?: false }
            } else {
                emptyList()
            }
            items(listData)
            {
                Row(Modifier.clickable {
                    lifecycleScope.launch {
                        if (status == com.example.myrjdapi.ViewModel.SelectedStation.START) {
                            viewModel.startStation.emit(it)
                        } else {
                            viewModel.endStation.emit(it)
                        }
                    }
                    navController.navigate("list")
                })
                {
                    Text(it.title.toString())
                }
            }
        }
    }
    }
}

@Composable
fun Station(
    navController: NavHostController,
    repository: TrainRepositoryAPI,
    viewModel: TrainViewModel,
    lifecycleScope: CoroutineScope
)
{
    val flowStart by viewModel.startStation.collectAsState()
    val flowEnd by viewModel.endStation.collectAsState()
    Column {
        Text(flowStart.title ?: "Выберите станцию отправления", Modifier.clickable {
            lifecycleScope.launch {
                viewModel.listSelectedStation.emit(com.example.myrjdapi.ViewModel.SelectedStation.START)
            }
            navController.navigate("selectedTrain")

        })
        Text(flowEnd.title ?: "Выберите станцию прибытия", Modifier.clickable {
            lifecycleScope.launch {
                viewModel.listSelectedStation.emit(com.example.myrjdapi.ViewModel.SelectedStation.END)
            }
            navController.navigate("selectedTrain")
        })
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun LikedList(
    navController: NavHostController,
    viewModel: TrainViewModel,
    lifecycleScope: CoroutineScope
)
{
    Scaffold(bottomBar = { BottomBarMenu(navController = navController)}) {

        pad -> Column(Modifier.padding(pad)) {
        val items by viewModel.listLikedThreadMutableStateFlow.collectAsState(initial = emptyList())
        LazyColumn()
        {
            items(items)
            {
                Row(Modifier.clickable {
                    lifecycleScope.launch {
                        viewModel.startStation.emit(StationDB(it.direction, it.stationStart, it.codesStart.toString(), null, null, null))
                        viewModel.endStation.emit(StationDB(it.direction, it.stationEnd, it.codesEnd.toString(), null, null, null))
                    }
                })
                {
                    Text(it.stationStart.toString())
                    Text(it.stationEnd.toString())
                    Icon(Icons.Filled.Favorite, "", modifier = Modifier.clickable {
                        lifecycleScope.launch {
                            viewModel.deletedLikedThread(it)
                        }
                    })
                }
            }
        }
    }
    }

}

@SuppressLint("CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetTrain(
    lifecycleScope: CoroutineScope,
    repository: TrainRepositoryAPI,
    navController: NavHostController,
    viewModel: TrainViewModel
) {
    var data by rememberSaveable {
        mutableStateOf("")
    }

    var status by rememberSaveable {
        mutableStateOf(Status.None)
    }

    var listThread by rememberSaveable {
        mutableStateOf(listOf<SegmentResponse>())
    }

    val showingResult by viewModel.showResult.collectAsState()

    Scaffold(bottomBar = { BottomBarMenu(navController) }) { pag ->
        Column(Modifier.padding(pag)) {
            Station(navController, repository, viewModel, lifecycleScope)
             when (status) {
                Status.Error -> "Ошибка"
                Status.Waiting -> "Ожидание"
                Status.OK -> "Загрузка завершена"
                Status.None -> "Отсутствует"
            }
            Toast.makeText(navController.context, status.toString(), Toast.LENGTH_LONG).show()
            val flowStart by viewModel.startStation.collectAsState()
            val flowEnd by viewModel.endStation.collectAsState()

//            if (flowStart.second == ShowingResult.Start && flowEnd.second == ShowingResult.Start)
//            {
//                val from = flowStart.first.codes
//                val to  = flowEnd.first.codes
//                if (from != " " && to != " ") {
//                    val flow2 = repository.getFrom(from, to, LocalDateTime.now().toString())
//                    lifecycleScope.launch {
//                        viewModel.showResult.emit(ShowingResult.NoShow)
//                        flow2.collect {
//                            status = it.first
//                            val list = it.second
//                            if (list != null) {
//                                listThread = list.segments
//                            }
//                        }
//
//                    }
//                }
//            }
            Row()
            {
                Button(onClick = {
                    val from = flowStart.codes
                    val to = flowEnd.codes
                    val flow2 = repository.getFrom(from, to, LocalDateTime.now().toString())
                    lifecycleScope.launch {
                        flow2.collect {
                            status = it.first
                            val list = it.second
//
                            if (list != null) {
                                //val items = list.segments.filter { LocalDateTime.parse(it.departure < LocalDateTime.now() }
                                listThread = list.segments
                            }
                            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")
                            //val dateThis = LocalDateTime.now().format(format)
                            val item = LocalDateTime.parse(list?.segments?.get(0)?.arrival, formatter)
                                //val dateStart = LocalDateTime.parse(list?.segments?.get(0)?.departure ?: "")
                            Log.d("mgkit", "item +" + item)
                        }
                    }
                }) {
                    Text("Вывести")
                }
                Icon(Icons.Filled.Favorite, "",
                    Modifier
                        .padding(10.dp)
                        .clickable {
                            if (flowStart.title != null && flowEnd.title != null) {
                                lifecycleScope.launch {
                                    viewModel.insertLikedThread(
                                        LikedThread(
                                            flowStart.direction,
                                            flowStart.title,
                                            flowEnd.title,
                                            flowStart.codes,
                                            flowEnd.codes
                                        )
                                    )
                                }
                            }
                        })
            }
            Text(data)
            LazyColumn()
            {
                items(listThread)
                {
                    Card(
                        Modifier
                            .padding(10.dp)
                            .fillMaxSize()
                    ) {
                        Column()
                        {
                            Text("Отпр - " + it.departure)
                            Text("Приб - " + it.arrival)
                        }
                        Row()
                        {
                            Text(it.from.title + "-----" + it.to.title)
                        }
                        Text(it.thread.number)
                        Text(text = it.thread.title)
                    }
                }
            }
        }
    }
}
