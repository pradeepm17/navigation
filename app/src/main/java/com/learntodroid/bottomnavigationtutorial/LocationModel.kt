package com.learntodroid.bottomnavigationtutorial


sealed class LocationModel {
    class LocationData(
        val id: Int,
        val name: String,
        val type: String
    ) : LocationModel() {
        constructor(location: Location) : this(



            location.id,
            location.name,
            location.type
        )
    }
    class LocationSeparator(val tag: String) : LocationModel()
}