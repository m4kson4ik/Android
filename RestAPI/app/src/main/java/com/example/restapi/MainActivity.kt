package com.example.restapi

import android.app.Application
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.restapi.DateBase.CatDB
import com.example.restapi.Interface.IRepositoryCats
import com.example.restapi.MainActivity.Companion.RESULTBASIC
import com.example.restapi.MainActivity.Companion.RESULTINTERNET
import com.example.restapi.Repository.CatRepositoryAPI
import com.example.restapi.Repository.Status
import com.example.restapi.ViewModel.CatViewModel
import com.example.restapi.ui.theme.RestAPITheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@HiltAndroidApp
class App : Application()
{

}



@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    companion object
    {
        const val TYPE = "TYPE"
        const val COUNT = "COUNT"
        const val BUTTON = "BUTTON"
        const val RESULTINTERNET = "RESULTINTERNET"
        const val RESULTBASIC = "RESULTBASIC"
    }

    private val viewModel : CatViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        val repository = CatRepositoryAPI()
        super.onCreate(savedInstanceState)
        setContent {
            RestAPITheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Cats(repository, lifecycleScope, viewModel)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Cats(
    repository: IRepositoryCats,
    lifecycleScope: LifecycleCoroutineScope,
    viewModel: CatViewModel
)
{
    Column {
        var type by rememberSaveable { mutableStateOf("") }
        var amount by rememberSaveable { mutableStateOf("") }
        var data by rememberSaveable {
            mutableStateOf(listOf<CatDB>())
        }

        val flowCat = viewModel.flowCat
        val statusInternet by viewModel.statusInternet.collectAsState()


        TextField(type,{type=it},label={Text(stringResource(id = R.string.type),
            Modifier.testTag(MainActivity.TYPE))})

        TextField(amount, {amount = it}, label = {Text(stringResource(id = R.string.count),
            Modifier.testTag(MainActivity.COUNT))})

        Text(
            when (statusInternet)
            {
                Status.Error -> "Ошибка"
                Status.Waiting -> "Ожидание"
                Status.OK -> "Загрузка завершена"
                Status.None -> "Отсутствует"
            }
        )

        Button(onClick = {
            val amountInt = amount.toIntOrNull() ?: 1
            val flow = repository.getFacts(type, amountInt)
            lifecycleScope.launch {
                flow.collect {
                    viewModel.statusInternet.emit(it.first)
                    val list = it.second
                    if (list != null)
                    {
                        data = list
                        viewModel.deleted()
                    }
                    if (it.first == Status.OK)
                    {
                        list?.forEach {viewModel.insert(it) }
                    }
                }
            }
        }) {
            Text(stringResource(id = R.string.reciveButton))
        }
        if (statusInternet == Status.Error || statusInternet == Status.None)
        {
            LazyColumn(Modifier.testTag(RESULTBASIC))
            {

                items(flowCat)
                {
                    Row()
                    {
                        Text(it.text)
                        Text(it.type)
                        Text(it.updatedAt)
                    }
                }
            }
        }
        else
        {
            LazyColumn(Modifier.testTag(RESULTINTERNET))
            {
                items(data)
                {
                    Row()
                    {
                        Text(it.text)
                        Text(it.type)
                        Text(it.updatedAt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                    }
                }
            }
        }
    }
}