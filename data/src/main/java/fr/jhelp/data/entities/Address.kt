package fr.jhelp.data.entities

data class Address internal constructor(internal val uid: Long, val road: String) {
    companion object {
        internal const val TABLE_ADDRESS = "Address"
        internal const val COLUMN_ROAD = "road"

    }

    constructor(road: String) : this(-1L, road)
}
