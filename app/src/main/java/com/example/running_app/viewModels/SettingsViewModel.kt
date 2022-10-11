package com.example.running_app.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.running_app.data.db.RoomDB
import com.example.running_app.data.db.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application): AndroidViewModel(application){

    companion object{
        var weightForCalories_: MutableLiveData<Int> = MutableLiveData(60)
    }

    // connect to the room database
    private val roomDB = RoomDB.get(application)
    private var coroutineScope = CoroutineScope(Dispatchers.Main)


    private var _nickname: MutableLiveData<String> = MutableLiveData(getUser().value?.name)
    var nickname: LiveData<String> = _nickname
    var height: Int = getUser().value?.height ?: 170
    var weight: Int = getUser().value?.weight ?: 60

    var result = false

    fun getUser(): LiveData<User> = roomDB.userDao().getUser()
    fun insert(user: User) {
        Log.d("ROOM insert", "$user")
        viewModelScope.launch { roomDB.userDao().insert(user) }
    }
    fun update(user: User) {
        Log.d("ROOM update", "$user")
        viewModelScope.launch { roomDB.userDao().update(user) }
    }

    fun checkNewUser(): Boolean {
        Log.d("ROOM", "${roomDB.userDao().checkNewUser()}")
        result = roomDB.userDao().checkNewUser()
        Log.d("ROOM", "result, $result")
        return result
    }

    fun getWeightForRunning(){
        weightForCalories_.postValue(getUser().value?.weight)
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