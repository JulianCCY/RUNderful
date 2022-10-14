package com.example.running_app.data.running.heartrate

import android.annotation.SuppressLint
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BLEViewModel(application: Application) : AndroidViewModel(application) {

    val TAG = "BLE viewModel"

    companion object{
        const val SCAN_PERIOD: Long = 5000
        val mBPM_: MutableLiveData<Int> = MutableLiveData(0)
        val hBPM_: MutableLiveData<Int> = MutableLiveData(0)
        val lBPM_: MutableLiveData<Int> = MutableLiveData(0)
        val avgBPM_: MutableLiveData<MutableList<Int>> = MutableLiveData(mutableListOf())
        val isConnected_: MutableLiveData<Int> = MutableLiveData(0)
    }

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    // Bluetooth Setup
    private var mBluetoothAdapter: BluetoothAdapter? = null
    val bluetoothManager = getApplication<Application>().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    val scanResults = MutableLiveData<List<ScanResult>>(null)
    val fScanning = MutableLiveData<Boolean>(false)
    private val mResults = java.util.HashMap<String, ScanResult>()

    // Connect to the call back function
    val gattClientCallback = GATTClientCallBack(BLEViewModel)

    // Connection condition
    // 0 -> disconnected
    // 1 -> connected
    // 2 -> loading
    // 3 -> device not found
    val isConnected: LiveData<Int> = isConnected_

    private fun checkBLEPermission(): Boolean {
        if (mBluetoothAdapter == null || !mBluetoothAdapter!!.isEnabled) {
            Log.d(TAG, "No Bluetooth LE capability")
            return false
        }
        Log.d(TAG, "Have Bluetooth LE capability")
        return true
    }

    // call from button in Composable
    fun scanDevices() {
        mBluetoothAdapter = bluetoothManager.adapter
        if (checkBLEPermission()){
            viewModelScope.launch(Dispatchers.IO) {
                isConnected_.postValue(2)
                val scanner = mBluetoothAdapter!!.bluetoothLeScanner
                fScanning.postValue(true)
                val settings = ScanSettings.Builder()
                    .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                    .setReportDelay(0)
                    .build()
                scanner.startScan(null, settings, leScanCallback)
                delay(SCAN_PERIOD)
                scanner.stopScan(leScanCallback)
                scanResults.postValue(mResults.values.toList())
                fScanning.postValue(false)
                delay(1000)


                // if the device is not found
                if (scanResults.value?.isEmpty() == true) {
                    Log.e(TAG, "Polar Sensor not found")
                    isConnected_.postValue(3)
                    return@launch
                }

                // connect to the device if it is detected
                val tar = scanResults.value?.get(0)?.device
                tar?.connectGatt(context, false, gattClientCallback)
                isConnected_.postValue(1)
            }
        }
    }

    // To scan the polar device
    private val leScanCallback: ScanCallback = object : ScanCallback() {
        @SuppressLint("MissingPermission")
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            val device = result.device
            val deviceName = device.name
            val deviceAddress = device.address

            if (deviceName == "Polar Sense A9BBE023") {
                mResults[deviceAddress] = result
                Log.d("DBG", "Device address: $deviceAddress $deviceName (${result.isConnectable})")
            }
        }

        // if scanning fails
        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            Log.i(TAG, "onScanFailed: failed")
        }
    }
}