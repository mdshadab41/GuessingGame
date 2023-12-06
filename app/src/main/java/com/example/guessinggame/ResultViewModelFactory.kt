package com.example.guessinggame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ResultViewModelFactory(private val finalResult: String):
    ViewModelProvider.Factory{

    //override this method , which the viewmodel provider
    //uses to create view model objects
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java))
        return ResultViewModel(finalResult) as T
        throw IllegalArgumentException("Unknown Viewmodel")
    }
}