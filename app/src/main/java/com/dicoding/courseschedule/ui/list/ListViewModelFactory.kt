package com.dicoding.courseschedule.ui.list

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.data.DataRepository
import com.dicoding.courseschedule.ui.home.HomeViewModel
import java.lang.reflect.InvocationTargetException

class ListViewModelFactory(private val repository: DataRepository?): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return try {
            modelClass.getConstructor(DataRepository::class.java).newInstance(repository)
        } catch (e: InstantiationException) {
            (modelClass.isAssignableFrom(ListViewModel::class.java)).let {
                repository?.let { it1 ->
                    ListViewModel(
                        it1
                    )
                } as T
            }
        }
    }

    companion object {
        fun createFactory(activity: Activity): ListViewModelFactory {
            val context = activity.applicationContext
                ?: throw IllegalStateException("Not yet attached to Application")

            return ListViewModelFactory(DataRepository.getInstance(context))
        }
    }
}