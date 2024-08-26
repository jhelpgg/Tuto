package fr.jhelp.data.entities


data class Person internal constructor(internal val uid: Long,
                                       val name: String,
                                       val address: Address) {
    companion object {
        internal const val TABLE_PERSON = "Person"
        internal const val COLUMN_NAME = "name"
        internal const val COLUMN_ADDRESS = "address"
    }

    constructor(name: String, address: Address) : this(-1L, name, address)
}