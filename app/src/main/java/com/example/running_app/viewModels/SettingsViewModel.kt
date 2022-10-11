package com.example.running_app.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.running_app.data.db.RoomDB
import com.example.running_app.data.db.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application): AndroidViewModel(application){

    // connect to the room database
    private val roomDB = RoomDB.get(application)
    private var coroutineScope = CoroutineScope(Dispatchers.Main)

    var nickname: String = "Username"
    var height: Int = 170
    var weight: Int = 60



//    val name: LiveData<String> = getUserInfo().value.get(0).name

    fun getUserInfo(): LiveData<List<User>> = roomDB.userDao().getAll()

    fun insert(uid: Long, name: String, height: Int, weight: Int) {
        coroutineScope.launch {
            roomDB.userDao().insert(User(uid, name, height, weight))
        }
        coroutineScope.cancel()
        coroutineScope = CoroutineScope(Dispatchers.Main)
    }
}