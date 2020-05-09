package com.mobile.harsoft.automatedselfmarket.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Toko(
    val id_toko: String?,
    val nama_toko: String?,
    val alamat: String?,
    val nama_pemilik: String?
) : Parcelable