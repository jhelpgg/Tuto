package fr.jhelp.data.entities

import android.content.ContentValues
import java.util.Objects

abstract class Entity internal constructor (internal var uid: Long)
{
    internal abstract val tableName: String
    internal abstract val columnsIdentifier: Array<String>
    internal abstract fun value(column: String): String
    internal abstract fun collect(contentValues: ContentValues)

    final override fun equals(other: Any?): Boolean =
        when
        {
            this === other                    -> true
            null == other || other !is Entity || this.tableName != other.tableName -> false
            else                              ->
                if (this.uid >= 0L && other.uid >= 0L)
                {
                    this.uid == other.uid
                }
                else
                {
                    this.columnsIdentifier.all { column -> this.value(column) == other.value(column) }
                }
        }

    final override fun hashCode(): Int =
        Objects.hash(this.tableName, *this.columnsIdentifier.map { column -> this.value(column) }.toTypedArray())

    final override fun toString(): String =
        this.tableName
}