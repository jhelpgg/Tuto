package fr.jhelp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.jhelp.data.COLUMN_ROAD

@Entity
data class Address(
    @PrimaryKey val uid: Long,
    @ColumnInfo(name = COLUMN_ROAD) val road: String)