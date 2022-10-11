package com.example.running_app.data.running.heartrate

import android.annotation.SuppressLint
import android.bluetooth.*
import android.util.Log
import com.example.running_app.data.running.heartrate.BLEViewModel
import java.lang.Integer.max
import java.lang.Integer.min
import java.util.*
import javax.inject.Inject

open class GATTClientCallBack @Inject constructor(
    private val model: BLEViewModel.Companion
): BluetoothGattCallback() {

    val TAG = "julian bluetooth"

    private fun convertFromInteger(i: Int): UUID {
        val MSB = 0x0000000000001000L
        val LSB = -0x7fffff7fa064cb05L
        val value = (i and -0x1).toLong()
        return UUID(MSB or (value shl 32), LSB)
    }
    val HEART_RATE_SERVICE_UUID  = convertFromInteger(0x180D)
    val HEART_RATE_MEASUREMENT_CHAR_UUID  = convertFromInteger(0x2A37)
    val CLIENT_CHARACTERISTIC_CONFIG_UUID = convertFromInteger(0x2902)

    @SuppressLint("MissingPermission")
    override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
        super.onConnectionStateChange(gatt, status, newState)
        if (status == BluetoothGatt.GATT_FAILURE) {
            Log.d(TAG, "GATT connection failure")
            return
        } else if (status == BluetoothGatt.GATT_SUCCESS) {
            Log.d(TAG, "GATT connection success")
            gatt.discoverServices()
            onServicesDiscovered(gatt, status)
            return
        }
        if (newState == BluetoothGatt.STATE_CONNECTED) {
            Log.d(TAG, "Connected GATT service")
            gatt.discoverServices()
            model.isConnected_.postValue(true)
        } else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
            Log.d(TAG, "Disconnected GATT service")
            model.isConnected_.postValue(false)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
        super.onServicesDiscovered(gatt, status)
        if (status != BluetoothGatt.GATT_SUCCESS) {
            return
        }
        Log.d(TAG, "onServicesDiscovered()")
        for (gattService in gatt.services) {

            if (gattService.uuid == HEART_RATE_SERVICE_UUID) {
                Log.d(TAG, "BINGO!!!")

                for (gattCharacteristic in gattService.characteristics) {
                    Log.d(TAG, "Characteristic ${gattCharacteristic.uuid}")
                }

                val characteristic = gatt.getService(HEART_RATE_SERVICE_UUID)
                    .getCharacteristic(HEART_RATE_MEASUREMENT_CHAR_UUID)
                gatt.setCharacteristicNotification(characteristic, true)

                if (gatt.setCharacteristicNotification(characteristic, true)){

                    val descriptor = characteristic.getDescriptor(CLIENT_CHARACTERISTIC_CONFIG_UUID)
                    descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                    gatt.writeDescriptor(descriptor)
                }

            }

        }
    }

    override fun onDescriptorWrite(gatt: BluetoothGatt, descriptor: BluetoothGattDescriptor, status: Int) {
        Log.d(TAG, "onDescriptorWrite status is $status and descriptor is ${descriptor.value}  ${descriptor.permissions}")
    }

    override fun onCharacteristicChanged(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic) {
        Log.d("DBG", "Characteristic data received")
        // get the heart rate from the sensor device
        val bpm = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 1)
        Log.d("My heart beat rate", "BPM: $bpm")

        // update the current heart rate
        model.mBPM_.postValue(bpm)

        // add in the list of average heart rate
        model.avgBPM_.value?.add(bpm)

        // when lowest heart rate is not initialised then let it be the current heart rate
        if (model.lBPM_.value == null) model.lBPM_.postValue(bpm)
        // update the new lowest heart rate if current one is lower
        else model.lBPM_.postValue(min(bpm, model.lBPM_.value!!))

        // when highest heart rate is not initialised then let it be the current heart rate
        if (model.hBPM_.value == null) model.hBPM_.postValue(bpm)
        // update the new highest heart rate if current one is higher
        else model.hBPM_.postValue(max(bpm, model.hBPM_.value!!))


    }

}