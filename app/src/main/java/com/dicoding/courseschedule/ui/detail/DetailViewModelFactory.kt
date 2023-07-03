package com.dicoding.courseschedule.ui.detail

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.data.DataRepository
import com.dicoding.courseschedule.ui.home.HomeViewModel

class DetailViewModelFactory(private val repository: DataRepository?, private val id: Int) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return try {
            modelClass.getConstructor(DataRepository::class.java, Int::class.java)
                .newInstance(repository, id)
        } catch (e: InstantiationException) {
            (modelClass.isAssignableFrom(DetailViewModel::class.java)).let {
                repository?.let { it1 ->
                    DetailViewModel(
                        it1,
                        id
                    )
                } as T
            }
        }
    }

    companion object {
        fun createFactory(activity: Activity, id: Int): DetailViewModelFactory {
            val context = activity.applicationContext
                ?: throw IllegalStateException("Not yet attached to Application")

            return DetailViewModelFactory(DataRepository.getInstance(context), id)
        }
    }
}