package com.example.eywa_android.Home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eywa_android.ClassObject.User

class HomeSharedViewModel : ViewModel() {

    private var _category = MutableLiveData("Action")
    private var _difficulty = MutableLiveData(0)
    val category : LiveData<String> = _category
    val difficulty : LiveData<Int> = _difficulty

    var displayUser : User? = null

    fun changeCategory(newCategory : String){
        _category.value = newCategory
    }

    fun changeDifficulty(newDifficulty : Int){
        _difficulty.value = newDifficulty
    }

    fun setUserToDisplay(newUser : User){
        displayUser = newUser
    }


}