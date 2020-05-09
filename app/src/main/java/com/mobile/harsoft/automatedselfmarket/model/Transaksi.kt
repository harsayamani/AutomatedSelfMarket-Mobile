package com.mobile.harsoft.automatedselfmarket.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Transaksi(
    val id_transaksi: Int?,
    val total_tagihan: Int?,
    val diterima: Int?,
    val kembalian: Int?,
    val id_toko: String?,
    val status: Int?,
    val created_at: String?,
    val update_at: String?
) : Parcelable