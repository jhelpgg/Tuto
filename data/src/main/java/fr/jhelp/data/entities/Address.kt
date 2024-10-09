package fr.jhelp.data.entities

import android.content.ContentValues
import fr.jhelp.data.database.DataType
import fr.jhelp.data.database.TutoDatabase

class Address internal constructor(uid: Long, val road: String) : Entity(uid)
{
    companion object
    {
        internal const val TABLE_ADDRESS = "Address"
        internal const val COLUMN_ROAD = "road"
    }

    constructor(road: String) : this(-1L, road)

    override val tableName = Address.TABLE_ADDRESS
    override val columnsIdentifier = arrayOf(COLUMN_ROAD)

    override fun value(column: String): String
    {
        return when (column)
        {
            COLUMN_ROAD -> this.road
            else        -> ""
        }
    }

    override fun collect(contentValues: ContentValues)
    {
        contentValues.put(COLUMN_ROAD, this.road)
    }
}
