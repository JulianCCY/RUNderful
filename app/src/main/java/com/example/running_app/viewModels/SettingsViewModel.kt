package com.example.running_app.viewModels

import android.app.Application
import android.util.MutableLong
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

    private var _nickname: MutableLiveData<String> = MutableLiveData(getUser().value?.name)
    var nickname: LiveData<String> = _nickname
    var height: Int = getUser().value?.height ?: 170
    var weight: Int = getUser().value?.weight ?: 60

    fun getUser(): LiveData<User> = roomDB.userDao().getUser()
    fun insert(user: User) {
        println("VM insert $user")
        viewModelScope.launch { roomDB.userDao().insert(user) }
    }
    fun update(user: User) {
        println("VM update $user")
        viewModelScope.launch { roomDB.userDao().update(user) }
    }

//    val name: LiveData<String> = getUserInfo().value.get(0).name
//
//    fun getUserInfo(): LiveData<List<User>> = roomDB.userDao().getAll()
//
//    fun insert(uid: Long, name: String, height: Int, weight: Int) {
//        coroutineScope.launch {
//            roomDB.userDao().insert(User(uid, name, height, weight))
//        }
//        coroutineScope.cancel()
//        coroutineScope = CoroutineScope(Dispatchers.Main)
//    }
}