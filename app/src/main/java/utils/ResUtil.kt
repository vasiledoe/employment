package utils

import android.content.Context
import androidx.core.content.ContextCompat

class ResUtil(val context: Context) {

    fun getStringRes(strId: Int): String = context.resources.getString(strId)

    fun getColorRes(colorId: Int): Int = ContextCompat.getColor(context, colorId)

    fun getDimens(dimensId: Int): Float = context.resources.getDimension(dimensId)
}