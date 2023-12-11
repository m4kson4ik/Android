package com.example.myapplication.Class

interface Command {
    fun run()
}

//class ListAuditionsCommand @Inject constructor(private val repository: ListAuditionMenuBuilder, navHostController: NavHostController) : Command {
//    @Composable
//    @RequiresApi(Build.VERSION_CODES.O)
//
//    override fun run() {
//
//    }
//}

//class CreateAuditionCommand constructor(private  val repository: AuditoriumWorker, private val AuditionModel : AuditionModel) : Class.Command
//{
//    override fun run() {
//        repository.addAuditorium()
//    }
//}

//class BindingListAudition()
//{
//
//}
//
//class EditAuditionCommand constructor(private val repository: AuditoriumWorker) : Class.Command
//{
//    override fun run()
//    {
//        repository.editingAuditorium()
//    }
//}
