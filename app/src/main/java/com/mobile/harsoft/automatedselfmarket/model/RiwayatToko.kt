package com.mobile.harsoft.automatedselfmarket.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RiwayatToko(
    val id_riwayat_pelanggan: Int?,
    val id_toko: String?,
    val id_pelanggan: String?,
    val created_at: String?,
    val update_at: String?
) : Parcelable