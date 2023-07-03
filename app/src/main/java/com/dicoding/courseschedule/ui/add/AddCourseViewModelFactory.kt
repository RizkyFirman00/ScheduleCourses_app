package com.dicoding.courseschedule.ui.add

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.data.DataRepository

class AddCourseViewModelFactory(private val repository: DataRepository?) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return try {
            modelClass.getConstructor(DataRepository::class.java).newInstance(repository)
        } catch (e: InstantiationException) {
            (modelClass.isAssignableFrom(AddCourseViewModel::class.java)).let {
                repository?.let { it1 ->
                    AddCourseViewModel(
                        it1
                    )
                } as T
            }
        }
    }

    companion object {
        fun createFactory(activity: Activity): AddCourseViewModelFactory {
            val context = activity.applicationContext
                ?: throw IllegalStateException("Not yet attached to Application")
            return AddCourseViewModelFactory(DataRepository.getInstance(context))
        }
    }
}