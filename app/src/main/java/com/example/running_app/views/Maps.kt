package com.example.running_app.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun plotMap(focus: LatLng, coords: List<LatLng>) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(focus, 15f)
    }
    GoogleMap(
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            compassEnabled = true,
        ),
        modifier = Modifier
            .height(400.dp)
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Marker(
            state = MarkerState(focus),

        )
        Polyline(
            points = coords,
            color = MaterialTheme.colors.primary
        )
    }
}

@Composable
fun plotMapWithStartEnd(startCoord: LatLng, endCoord: LatLng, coords: List<LatLng>) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(startCoord, 15f)
    }
    GoogleMap(
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            compassEnabled = true,
        ),
        modifier = Modifier
            .height(500.dp)
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Marker(
            state = MarkerState(startCoord),
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN),
        )
        Polyline(
            points = coords,
            color = MaterialTheme.colors.primary
        )
        Marker(
            state = MarkerState(endCoord),
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED),
        )
    }
}