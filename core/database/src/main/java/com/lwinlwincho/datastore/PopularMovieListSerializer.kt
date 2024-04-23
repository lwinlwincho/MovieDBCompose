package com.lwinlwincho.datastore

import androidx.datastore.core.Serializer
import com.lwinlwincho.data.PopularResponseList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object PopularMovieListSerializer : Serializer<PopularResponseList> {
    override val defaultValue: PopularResponseList
        get() = PopularResponseList()

    override suspend fun readFrom(input: InputStream): PopularResponseList {
        return try {
            Json.decodeFromString(
                deserializer = PopularResponseList.serializer(),
                string = input.readBytes().decodeToString()
            )
        }catch (e : SerializationException){
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: PopularResponseList, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(
                    serializer = PopularResponseList.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        }
    }
}