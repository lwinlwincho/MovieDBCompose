package com.lwinlwincho.datastore

import androidx.datastore.core.Serializer
import com.lwinlwincho.data.NowShowingResponseList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object NowShowingMovieListSerializer : Serializer<NowShowingResponseList> {
    override val defaultValue: NowShowingResponseList
        get() = NowShowingResponseList()

    override suspend fun readFrom(input: InputStream): NowShowingResponseList {
        return try {
            Json.decodeFromString(
                deserializer = NowShowingResponseList.serializer(),
                string = input.readBytes().decodeToString()
            )
        }catch (e : SerializationException){
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: NowShowingResponseList, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(
                    serializer = NowShowingResponseList.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        }
    }
}