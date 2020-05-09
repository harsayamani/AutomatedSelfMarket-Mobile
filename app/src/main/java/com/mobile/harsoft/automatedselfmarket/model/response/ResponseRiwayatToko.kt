package com.mobile.harsoft.automatedselfmarket.model.response

import com.mobile.harsoft.automatedselfmarket.model.RiwayatToko
import com.mobile.harsoft.automatedselfmarket.model.Toko

data class ResponseRiwayatToko(
    val status_code: Int?,
    val message: String?,
    val data_riwayat: List<RiwayatToko?>?,
    val data_toko: List<Toko?>?
)