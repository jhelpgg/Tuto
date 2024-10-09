package fr.jhelp.data.entities

import android.content.ContentValues
import fr.jhelp.data.database.DataType
import fr.jhelp.data.database.TutoDatabase

class Person internal constructor(uid: Long,
                                  val name: String,
                                  val address: Address) : Entity(uid)
{
    companion object
    {
        internal const val TABLE_PERSON = "Person"
        internal const val COLUMN_NAME = "name"
        internal const val COLUMN_ADDRESS = "address"
    }

    constructor(name: String, address: Address) : this(-1L, name, address)

    override val tableName = Person.TABLE_PERSON
    override val columnsIdentifier = arrayOf(COLUMN_NAME)

    override fun value(column: String): String
    {
        return when (column)
        {
            COLUMN_NAME -> this.name
            COLUMN_ADDRESS -> this.address.uid.toString()
            else -> ""
        }
    }

    override fun collect(contentValues: ContentValues)
    {
        contentValues.put(COLUMN_NAME, this.name)
        contentValues.put(COLUMN_ADDRESS, this.address.uid)
    }
}