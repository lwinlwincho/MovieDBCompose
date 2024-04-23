package com.lwinlwincho.datastore

import androidx.datastore.core.Serializer
import com.lwinlwincho.data.PopularMovieList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object PopularMovieListSerializer : Serializer<PopularMovieList> {
    override val defaultValue: PopularMovieList
        get() = PopularMovieList()

    override suspend fun readFrom(input: InputStream): PopularMovieList {
        return try {
            Json.decodeFromString(
                deserializer = PopularMovieList.serializer(),
                string = input.readBytes().decodeToString()
            )
        }catch (e : SerializationException){
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: PopularMovieList, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(
                    serializer = PopularMovieList.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        }
    }
}