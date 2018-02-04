package se.hactar.movieregister.helper

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader

import java.io.IOException
import java.io.Reader

import okhttp3.ResponseBody
import retrofit2.Converter


class GsonPResponseBodyConverter<T> internal constructor(private val gson: Gson, private val adapter: TypeAdapter<T>) : Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {
        val reader = value.charStream()
        var item = reader.read()
        while (item != '('.toInt() && item != -1) {
            item = reader.read()
        }
        val jsonReader = gson.newJsonReader(reader)
        try {
            return adapter.read(jsonReader)
        } finally {
            reader.close()
        }
    }
}