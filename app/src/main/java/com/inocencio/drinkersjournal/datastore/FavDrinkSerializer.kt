
package com.inocencio.drinkersjournal.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.inocencio.drinkersjournal.ListOfDrinks
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

