package com.example.running_app.viewModels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.livedata.observeAsState
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

    val TAG = "ROOM"

    companion object{
        var weightForCalories_: MutableLiveData<Int> = MutableLiveData(null)
    }

    val weightForCalories: LiveData<Int> = weightForCalories_

    // connect to the room database
    private val roomDB = RoomDB.get(application)
    private var coroutineScope = CoroutineScope(Dispatchers.Main)


    private var _nickname: MutableLiveData<String> = MutableLiveData(getUser().value?.name)
    var nickname: LiveData<String> = _nickname
    var height: Int = getUser().value?.height ?: 170

    var weight =  60
    var result = false

    fun getUser(): LiveData<User> = roomDB.userDao().getUser()
    fun insert(user: User) {
        Log.d("ROOM insert", "$user")
        viewModelScope.launch { roomDB.userDao().insert(user) }
    }
    fun update(user: User) {
        Log.d("ROOM update", "$user")
        viewModelScope.launch {
            roomDB.userDao().update(user)
            getUserWeight()
        }
    }

    fun checkNewUser(): Boolean {
        Log.d(TAG, "${roomDB.userDao().checkNewUser()}")
        result = roomDB.userDao().checkNewUser()
        Log.d(TAG, "result, $result")
        return result
    }

    fun getUserWeight(){
        Log.d(TAG, "get from db ${roomDB.userDao().getUserWeight()}")
        weight = roomDB.userDao().getUserWeight()
        Log.d(TAG, "get from db 2${weight}")
        weightForCalories_.value = weight
        Log.d(TAG, "after post, ${weightForCalories_.value}")
    }
}