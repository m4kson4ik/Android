package com.example.myapplication.ContentProvider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.AbstractCursor
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.myapplication.Room.AuditionDB
import com.example.myapplication.Room.AuditionDateBase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class AuditionCursor(private val auditionList: List<AuditionDB>) : AbstractCursor() {
    override fun getCount(): Int = auditionList.size

    override fun getColumnNames(): Array<String> = arrayOf("startDate", "endDate", "numberAudition","name", "uri","numberImage", "id")

    override fun getString(column: Int): String? {
        Log.d("mgkit","string $column $position ${auditionList[position]} ${auditionList[position].uid}")
        return when (column)
        {
            0 ->  auditionList[position].startDate
            1 ->  auditionList[position].endDate
            3 ->  auditionList[position].name
            4 -> auditionList[position].uri?:""
            else -> ""
        }
    }

    override fun getShort(column: Int): Short {
        TODO("Not yet implemented")
    }

    override fun getType(column: Int): Int {
        return when (column) {
            2,5,6 -> FIELD_TYPE_INTEGER
            else -> FIELD_TYPE_STRING
        }
    }

    override fun getInt(column: Int): Int {
        Log.d("mgkit","$column $position ${auditionList[position]} ${auditionList[position].uid}")
        return when(column)
        {
            2 -> return auditionList[position].numberAudition
            5 -> return auditionList[position].numberImage ?: 0
            6 -> return auditionList[position].uid
            else -> 0
        }
    }

    override fun getLong(column: Int): Long {
        Log.d("mgkit","$column $position ${auditionList[position]} ${auditionList[position].uid}")
        return when(column)
        {
            2 -> auditionList[position].numberAudition
            5 -> auditionList[position].numberImage ?: 0
            6 -> auditionList[position].uid
            else -> 0
        }.toLong()
    }

    override fun getFloat(column: Int): Float {
        TODO("Not yet implemented")
    }

    override fun getDouble(column: Int): Double {
        TODO("Not yet implemented")
    }

    override fun isNull(column: Int): Boolean {
        TODO("Not yet implemented")
    }
}


class DbHolder @Inject constructor(val db: AuditionDateBase)

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ContentProviderEntryPoint {
    fun getDbHolder(): DbHolder
}

@RequiresApi(Build.VERSION_CODES.O)
class AuditionProvider : ContentProvider() {
    private lateinit var db : AuditionDateBase
    private val dao by lazy { db.auditionDAO()}
    @RequiresApi(Build.VERSION_CODES.O)



    override fun onCreate(): Boolean {
        this.db = EntryPointAccessors.fromApplication(context!!.applicationContext,ContentProviderEntryPoint::class.java)
            .getDbHolder().db
        Log.d("mgkit","db object in provider: ${this.db}")
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
         val auditionList = mutableListOf<AuditionDB>()
        return if ((projection == null) && (selection == null) && (selectionArgs == null) && (sortOrder == null)) {
            runBlocking {
                launch(Dispatchers.Default) {
                    val list = dao.getListAudition()
                    auditionList.addAll(list)
                }
            }
            return AuditionCursor(auditionList.toList())
        } else null
    }

    override fun getType(uri: Uri): String? {
        return "audition.object/audition.user"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (values!= null)
        {
            val startDate = values["startDate"] as String
            val endDate = values["endDate"] as String
            val numberAudition = values["numberAudition"] as Int
            val name = values["name"] as String
            val item = (AuditionDB(startDate,endDate, numberAudition, name))
            runBlocking {
                dao.insert(item)
            }
            return Uri.parse(uriPrefix+"/"+(item.uid).toString())
        } else return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val item =  selectionArgs!![0].toInt()
        if (item != null) {
            runBlocking {
                Log.d("mgkit","delete from provider ${dao}")

                dao.deletedById(item)
            }
            return 1
        }
        else
        {
            return 0
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        if (selection == "" && selectionArgs == null)
        {
            val lastSegment = uri.lastPathSegment
            if (lastSegment != null)
            {
                val id = values?.get("uid") as Int
                if (id != null)
                {
                    if (values!= null)
                    {
                        val startDate = values["startDate"] as String
                        val endDate = values["endDate"] as String
                        val numberAudition = values["numberAudition"] as Int
                        val name = values["name"] as String
                        val item = AuditionDB(startDate, endDate, numberAudition, name)
                        item.uid = id
                        runBlocking {
//                            dao.deletedById(id)
                            dao.update(item)
                        }
                    }
                }
            }
        }
        return 0
    }
    companion object
    {
        const val uriPrefix ="content://audition/user"
    }
}