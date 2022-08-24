package com.apis.recipefood.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConverters {

    @TypeConverter
    fun fromAnytoString(attributes: Any?):String{
        if (attributes == null)
            return ""
        return attributes as String
    }

    fun fromStringtoAny(attributes: String?):Any{
        if (attributes == null)
            return ""
        return attributes

    }

}