package com.example.myapplication.View

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.example.myapplication.AuditionsViewModel
import com.example.myapplication.Class.CreateDateSimple
import com.example.myapplication.R
import com.example.myapplication.Room.AuditionDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ListAudition
{
    @Composable
    fun BottomBarListAudition(navController: NavController) {
        NavigationBar {
            NavigationBarItem(selected = true,
                onClick = { navController.navigate("list") },
                label = { Text(text = stringResource(R.string.List)) },
                icon = { Icon(Icons.Filled.List, "") })
            NavigationBarItem(
                selected = true,
                onClick = { navController.navigate("create") },
                label = { Text(text = stringResource(R.string.Create)) },
                icon = { Icon(Icons.Filled.Add, "") })
        }
    }


    @SuppressLint("NotConstructor", "CoroutineCreationDuringComposition")
    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun ListAudition(
        navController: NavController,
        viewModel: AuditionsViewModel,
        scope: CoroutineScope,
        mutableList: MutableList<AuditionDB>
    ) {
        //val listAudition by viewModel.listAuditionMutableStateFlow.collectAsState(initial = emptyList())
        val listAudition by viewModel.listAuditionMutableStateFlow.collectAsState(initial = emptyList())

        Scaffold (
            bottomBar = { BottomBarListAudition(navController) }
        ) {
                pag -> Column (Modifier.padding(pag)){
            Search(viewModel)
            DropMenuSorted(viewModel = viewModel)
            LazyColumn(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .fillMaxSize())
            {
                items(listAudition,key = {it.uid})
                {
                    Row(modifier = Modifier
                        .clickable {
                            navController.navigate("user/${it.uid}")
                        }
                        .fillMaxSize()) {
                            val dismissState = rememberDismissState()
                            if (dismissState.isDismissed(DismissDirection.EndToStart))
                            {
                                scope.launch {
                                    viewModel.deleted(it)
                                }
                                //DeletedAuditionDialog(viewModel, it, navController, scope)
                            }
                            SwipeToDismiss(state = dismissState, background = {
                            }, dismissContent = {
                                Card(
                                    Modifier.fillMaxSize().padding(5.dp)
                                ) {
                                    Text(
                                        stringResource(R.string.teacher) + "- ${it.name}",
                                        Modifier.padding(start = 10.dp, end = 10.dp)
                                    )
                                    Text(
                                        stringResource(R.string.numberAudition) + "- ${it.numberAudition}",
                                        Modifier.padding(start = 10.dp, end = 10.dp)
                                    )

                                    Text(
                                        stringResource(R.string.dateAndTimeStart) + "\n${it.startDate}",
                                        Modifier.padding(start = 10.dp, end = 10.dp)
                                    )
                                    Text(
                                        stringResource(R.string.dateAndTimeEnd) + "\n${it.endDate}",
                                        Modifier.padding(start = 10.dp, end = 10.dp)
                                    )
                                }
                            })
                        }

                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun create(
        viewModel: AuditionsViewModel,
        lifecycleScope: LifecycleCoroutineScope,
        navController: NavController
    ) {
        Scaffold (
            bottomBar = { BottomBarListAudition (navController = navController) }
        )
        { paddingValues ->  Column(Modifier.padding(paddingValues)) {

        }
            var name by rememberSaveable { mutableStateOf("") }
            var numberAudition by rememberSaveable { mutableStateOf("") }
            var startDate by rememberSaveable { mutableStateOf("") }
            var endDate by rememberSaveable { mutableStateOf("") }

            val workerDate = CreateDateSimple()
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.FieldName)) })

                TextField(
                    value = numberAudition,
                    onValueChange = { numberAudition = it },
                    label = { Text(stringResource(R.string.FieldNumberAudition)) })

                TextField(value = startDate,
                    onValueChange = { startDate = it },
                    label = { Text(stringResource(R.string.FieldDateAndTimeStart)) })

                TextField(value = endDate,
                    onValueChange = { endDate = it },
                    label = { Text(stringResource(R.string.FieldDateAndTimeEnd)) })

                Button(
                    onClick = {
                        val date1 = workerDate.checkDate(startDate)
                        val date2 = workerDate.checkDate(endDate)
                        if (numberAudition.isNotEmpty() && name.isNotEmpty() && date1 != null && date2 != null) {
                            val aud = AuditionDB(
                                startDate,
                                endDate,
                                numberAudition.toInt(),
                                name
                            )
                            lifecycleScope.launch {
                                viewModel.create(aud)
                            }
                            navController.navigate("list")
                        }
                        else
                        {
                            Toast.makeText(
                                navController.context,
                                "Error",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    },
                    content = { Text(text = stringResource(R.string.Create)) })
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Search(viewModel: AuditionsViewModel)
    {
        var search by rememberSaveable { (mutableStateOf("")) }
        Row {
            TextField(value = search, onValueChange = {search = it}, label = { Text(text = stringResource(R.string.search)) })
            IconButton(onClick = { viewModel.search(search) },  content = { Icon(Icons.Filled.Search, "") })
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun DropMenuSorted(viewModel : AuditionsViewModel)
    {
        var expanded by remember { mutableStateOf(false) }
        var sortedElement by rememberSaveable { mutableIntStateOf(0) }

        Box {
            Row {
                Text(stringResource(R.string.sorted))
                IconButton(onClick = { expanded = true }) {
                    if (!expanded) {
                        Icon(Icons.Filled.KeyboardArrowDown, "")
                    } else Icon(Icons.Filled.KeyboardArrowUp, "")

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false })
                    {
                        DropdownMenuItem(text = { Text(stringResource(R.string.SortedName)) }, onClick = {
                            expanded = false
                            sortedElement = 1
                        })
                        DropdownMenuItem(text = { Text(stringResource(R.string.SortedNumberAudition)) }, onClick = {
                            expanded = false
                            sortedElement = 2
                        })
                        DropdownMenuItem(text = { Text(stringResource(R.string.SortedStart)) }, onClick = {
                            expanded = false
                            sortedElement = 3
                        })
                        DropdownMenuItem(text = { Text(stringResource(R.string.SortedEnd)) }, onClick = {
                            expanded = false
                            sortedElement = 4
                        })
                    }
                }
            }

            when(sortedElement)
            {
                1 -> viewModel.sortedAudition { it.name }
                2 -> viewModel.sortedAudition { it.numberAudition }
                3 -> viewModel.sortedAudition { it.startDate }
                4 -> viewModel.sortedAudition { it.endDate }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun DeletedAuditionDialog(viewModel : AuditionsViewModel, deletedAudition: AuditionDB, navController : NavController, scope : CoroutineScope)
    {
        var openDialog by rememberSaveable {
            mutableStateOf(true)
        }
        if (openDialog) {
            AlertDialog(
                onDismissRequest = {
                    openDialog = false
                },
                title = { Text(text = stringResource(R.string.Confirmationoftheaction)) },
                text = { Text(stringResource(R.string.DeletedElementMessage)) },
                confirmButton =
                {
                    Row {
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                scope.launch {
                                    viewModel.deleted(deletedAudition)
                                }
                                openDialog = false
                            }
                        ) {
                            Text(stringResource(R.string.Delete))
                        }

                        Button(onClick = {openDialog = false}, modifier = Modifier.padding(start = 20.dp)) {
                            Text(text = stringResource(R.string.Cancel))
                        }
                    }
                }
            )
        }
    }

}