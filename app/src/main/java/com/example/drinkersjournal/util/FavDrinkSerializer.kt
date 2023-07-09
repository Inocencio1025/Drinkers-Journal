
package com.example.drinkersjournal.util

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.example.drinkersjournal.ListOfDrinks
import com.example.drinkersjournal.ProtoDrink
import com.example.drinkersjournal.protoDrink
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object FavDrinkSerializer : Serializer<ListOfDrinks> {
    override val defaultValue: ListOfDrinks = ListOfDrinks.getDefaultInstance()

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun readFrom(input: InputStream): ListOfDrinks {
        try {
            return ListOfDrinks.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }catch (e: java.io.IOException) {
            e.printStackTrace()
            throw e
        }

    }
    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(
        t: ListOfDrinks,
        output: OutputStream) = t.writeTo(output)
}




