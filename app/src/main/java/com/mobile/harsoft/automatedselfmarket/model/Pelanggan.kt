package com.mobile.harsoft.automatedselfmarket.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pelanggan(
    val id_pelanggan: String?,
    val nama_pelanggan: String?,
    val alamat: String?
) : Parcelable