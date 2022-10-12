package com.example.running_app.viewModels

import androidx.lifecycle.ViewModel
import com.example.running_app.views.RunRecordForUI
import com.google.android.gms.maps.model.LatLng

class StatDetailViewModel: ViewModel() {

    private val historyList = listOf(
        RunRecordForUI(1, "05-10-2022", "10:00", "10:48", "00:15:31", 13, "Foggy", 9876, 4, 8.0,175.0, 3, 0.52, listOf(
            LatLng(60.178152, 24.989714),
            LatLng(60.178347, 24.991572),
            LatLng(60.178559, 24.992468),
            LatLng(60.178808, 24.993152),
            LatLng(60.178970, 24.994139),
            LatLng(60.179276, 24.996648),
            LatLng(60.179540, 24.997403),
            LatLng(60.180113, 24.998110),
            LatLng(60.180294, 24.999510),
            LatLng(60.180373, 25.000382),
        )),
        RunRecordForUI(2, "05-10-2022", "10:00", "10:48", "00:15:31", 13, "Foggy", 9876, 4, 8.0,175.0, 3, 0.52, listOf(
            LatLng(60.178152, 24.989714),
            LatLng(60.178347, 24.991572),
            LatLng(60.178559, 24.992468),
            LatLng(60.178808, 24.993152),
            LatLng(60.178970, 24.994139),
            LatLng(60.179276, 24.996648),
            LatLng(60.179540, 24.997403),
            LatLng(60.180113, 24.998110),
            LatLng(60.180294, 24.999510),
            LatLng(60.180373, 25.000382),
        )),
    )

    fun getDataById(id: Long): RunRecordForUI {
        return historyList.first { it.id == id }
    }
}