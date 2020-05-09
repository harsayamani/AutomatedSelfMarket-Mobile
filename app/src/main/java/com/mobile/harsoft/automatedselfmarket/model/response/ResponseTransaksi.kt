package com.mobile.harsoft.automatedselfmarket.model.response

import com.mobile.harsoft.automatedselfmarket.model.Toko
import com.mobile.harsoft.automatedselfmarket.model.Transaksi

class ResponseTransaksi(
    val status_code: Int?,
    val message: String?,
    val data_transaksi: List<Transaksi?>?,
    val data_toko: List<Toko?>?
)