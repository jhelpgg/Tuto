package fr.jhelp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import fr.jhelp.data.COLUMN_ADDRESS
import fr.jhelp.data.COLUMN_NAME
import fr.jhelp.data.TABLE_PERSON
import fr.jhelp.data.entities.Address
import fr.jhelp.data.entities.Person

@Dao
internal interface PersonDao {
    @Query("SELECT * FROM $TABLE_PERSON")
    fun persons(): List<Person>

    @Query("SELECT * from $TABLE_PERSON WHERE $COLUMN_NAME LIKE :name")
    fun findByName(name: String): List<Person>

    @Query("SELECT * from $TABLE_PERSON WHERE $COLUMN_ADDRESS=:address")
    fun findByAddress(address: Address): List<Person>

    @Insert
    fun addPerson(vararg persons: Person)
}