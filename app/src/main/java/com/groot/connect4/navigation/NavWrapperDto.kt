package com.groot.connect4.navigation

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.Parceler

@Parcelize
class NavArgWrapperDto2(val navArgVo: String?, val from: String? = null) : Parcelable


@Parcelize
class NavArgWrapperDto(val navArgVo: Any?, val from: String? = null) : Parcelable {
    companion object : Parceler<NavArgWrapperDto> {
        override fun create(parcel: Parcel): NavArgWrapperDto {
            return NavArgWrapperDto(
                navArgVo = parcel.readValue(Any::class.java.classLoader),
                from = parcel.readString()
            )
        }

        override fun NavArgWrapperDto.write(parcel: Parcel, flags: Int) {
            parcel.writeValue(navArgVo)
            parcel.writeString(from)
        }
    }
}