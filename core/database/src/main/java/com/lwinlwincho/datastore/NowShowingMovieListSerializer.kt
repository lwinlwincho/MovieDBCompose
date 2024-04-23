package com.lwinlwincho.datastore

import androidx.datastore.core.Serializer
import com.lwinlwincho.data.NowShowingMovieList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object NowShowingMovieListSerializer : Serializer<NowShowingMovieList> {
    override val defaultValue: NowShowingMovieList
        get() = NowShowingMovieList()

    override suspend fun readFrom(input: InputStream): NowShowingMovieList {
        return try {
            Json.decodeFromString(
                deserializer = NowShowingMovieList.serializer(),
                string = input.readBytes().decodeToString()
            )
        }catch (e : SerializationException){
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: NowShowingMovieList, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(
                    serializer = NowShowingMovieList.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        }
    }
}