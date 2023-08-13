package com.anurag.sneakerapp.db

import androidx.room.TypeConverter
import com.anurag.sneakerapp.data.model.Media
import com.google.gson.Gson

class MediaConverter {
    private val gson = Gson()

    @TypeConverter
    fun mediaToString(media: Media): String {
        return gson.toJson(media)
    }

    @TypeConverter
    fun stringToMedia(mediaString: String): Media {
        return gson.fromJson(mediaString, Media::class.java)
    }
}
