package com.example.csapp

class Global {

    companion object {
        val maps = hashMapOf(
            "mirage" to false,
            "inferno" to false,
            "dust2" to false,
            "overpass" to false,
            "nuke" to false,
            "vertigo" to false,
            "ancient" to false
        )
        var selectedSmoke = Int.MIN_VALUE
        var selectedPos = Int.MIN_VALUE

        var land = ""
        var pos = ""
    }
}