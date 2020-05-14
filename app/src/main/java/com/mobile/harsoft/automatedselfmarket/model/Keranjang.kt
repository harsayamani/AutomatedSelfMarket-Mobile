package com.mobile.harsoft.automatedselfmarket.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Keranjang(
    val id_keranjang: Int?,
    val id_produk: String?,
    val nama_produk: String?,
    val kategori_produk: String?,
    val kuantitas: Int?,
    val harga_unit: Int?,
    val harga_update: Int?,
    val id_pelanggan: String?,
    val status: Int?
) : Parcelable