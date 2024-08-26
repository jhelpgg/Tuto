package fr.jhelp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.jhelp.data.dao.AddressDao
import fr.jhelp.data.dao.PersonDao
import fr.jhelp.data.entities.Address
import fr.jhelp.data.entities.Converters
import fr.jhelp.data.entities.Person

@Database(entities = [Address::class, Person::class], version = 1)
 @TypeConverters(Converters::class)
internal abstract class TutoDatabase : RoomDatabase() {
    abstract fun addressDao() : AddressDao
    abstract fun personDao() : PersonDao
}