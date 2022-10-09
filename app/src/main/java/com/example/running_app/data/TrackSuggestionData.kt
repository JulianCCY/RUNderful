package com.example.running_app.data

import com.google.android.gms.maps.model.LatLng

data class TrackSuggestionStructure(
    val title: String,
    val coords: List<LatLng>,
)

object TrackSuggestionData {
    var suggestions = arrayListOf(
        TrackSuggestionStructure(
            "Location 1",
            listOf(
                LatLng(44.811058, 20.4617586),
                LatLng(44.811058, 20.4627586),
                LatLng(44.810058, 20.4627586),
                LatLng(44.809058, 20.4627586),
                LatLng(44.809058, 20.4617586)
            ),
        ),
        TrackSuggestionStructure(
            "Location 2",
            listOf(
                LatLng(44.811058, 20.4617586),
                LatLng(44.811058, 20.4627586),
                LatLng(44.810058, 20.4627586),
                LatLng(44.809058, 20.4627586),
                LatLng(44.809058, 20.4617586)
            ),
        ),
        TrackSuggestionStructure(
            "Location 3",
            listOf(
                LatLng(44.811058, 20.4617586),
                LatLng(44.811058, 20.4627586),
                LatLng(44.810058, 20.4627586),
                LatLng(44.809058, 20.4627586),
                LatLng(44.809058, 20.4617586)
            ),
        ),
        TrackSuggestionStructure(
            "Location 4",
            listOf(
                LatLng(44.811058, 20.4617586),
                LatLng(44.811058, 20.4627586),
                LatLng(44.810058, 20.4627586),
                LatLng(44.809058, 20.4627586),
                LatLng(44.809058, 20.4617586)
            ),
        ),
        TrackSuggestionStructure(
            "Location 5",
            listOf(
                LatLng(44.811058, 20.4617586),
                LatLng(44.811058, 20.4627586),
                LatLng(44.810058, 20.4627586),
                LatLng(44.809058, 20.4627586),
                LatLng(44.809058, 20.4617586)
            ),
        ),
    )
}