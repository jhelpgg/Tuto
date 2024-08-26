package fr.jhelp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import fr.jhelp.data.COLUMN_ROAD
import fr.jhelp.data.TABLE_ADDRESS
import fr.jhelp.data.entities.Address

@Dao
internal interface AddressDao {
    @Query("SELECT * FROM $TABLE_ADDRESS")
    fun addresses(): List<Address>

    @Query("SELECT * from $TABLE_ADDRESS WHERE $COLUMN_ROAD LIKE :road")
    fun findByRoad(road: String): List<Address>

    @Query("SELECT * FROM $TABLE_ADDRESS WHERE uid=:uid")
    fun address(uid:Long) : Address?

    @Insert
    fun addAddress(vararg addresses: Address)
}