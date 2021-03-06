package com.mobile.harsoft.automatedselfmarket.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Keranjang(
    val id_keranjang: Int?,
    val id_produk: String?,
    val kuantitas: Int?,
    val harga_update: Int?,
    val id_pelanggan: String?,
    val status: Int?
) : Parcelable