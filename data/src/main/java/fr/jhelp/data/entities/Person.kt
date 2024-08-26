package fr.jhelp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.jhelp.data.COLUMN_ADDRESS
import fr.jhelp.data.COLUMN_NAME
import fr.jhelp.data.COLUMN_ROAD

@Entity
data class Person(
    @PrimaryKey val uid: Long,
    @ColumnInfo(name = COLUMN_NAME) val name: String,
    @ColumnInfo(name = COLUMN_ADDRESS, typeAffinity = ColumnInfo.INTEGER) val address: Address)
