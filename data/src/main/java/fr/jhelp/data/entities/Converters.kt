package fr.jhelp.data.entities

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import fr.jhelp.data.database.database

@ProvidedTypeConverter
internal class Converters {
    @TypeConverter
    fun addressToId(address: Address?): Long = address?.uid ?: -1L

    @TypeConverter
    fun getAddress(id: Long): Address? = database.addressDao().address(id)
}