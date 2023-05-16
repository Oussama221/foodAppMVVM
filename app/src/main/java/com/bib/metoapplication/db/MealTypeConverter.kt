package com.bib.metoapplication.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConverter {
    @TypeConverter
    fun fromAnyToString(attributes: Any?) : String{
        if (attributes == null)
            return ""
        return attributes.toString()
    }

    @TypeConverter
    fun fromStringToAny(attributes: String?) : Any {
        if (attributes == null)
            return ""
        return attributes.toString()
    }

}