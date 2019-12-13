package com.disorganizzazione.spesapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.disorganizzazione.spesapp.IngredientName
import org.jetbrains.annotations.NotNull
import java.util.*

/* Classe che rappresenta la tabella "INGREDIENTI" (mostrata nell'activity principale).
*  Class representing the "INGREDIENTS" table (shown in the main activity). */

@Entity
class IngredientEntity {
    // TODO: decidere se va bene come cp/decide if it should be the pk
    @PrimaryKey
    var name : IngredientName = ""

    // Data di scadenza: è un Long perché SQLite non ha manco le date...
    // Expiration date: it is a Long because SQLite does not even have dates...
    @ColumnInfo
    var useBefore : Date? = null

    @ColumnInfo
    var portions : Int? = null

    @ColumnInfo @NotNull
    var inStorage : Boolean = false
}