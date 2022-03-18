package com.rockstreamer.custommusicplayer.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.rockstreamer.custommusicplayer.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryInfo(): Parcelable {

    enum class Category(
        val id: Int,
        @StringRes val stringRes: Int,
        @DrawableRes val icon: Int
    ){
        Home(R.id.ac)
    }
}