
package com.example.drinkersjournal.util

import androidx.datastore.core.Serializer
import com.example.drinkersjournal.ListOfDrinks
import java.io.InputStream
import java.io.OutputStream

object FavDrinkSerializer : Serializer<ListOfDrinks> {
    override val defaultValue: ListOfDrinks = ListOfDrinks.getDefaultInstance()


    override suspend fun readFrom(input: InputStream): ListOfDrinks {

        return ListOfDrinks.parseFrom(input)

    }


    override suspend fun writeTo(
        t: ListOfDrinks,
        output: OutputStream)  { t.writeTo(output) }
}



