package com.mobile.harsoft.automatedselfmarket.model.response

import com.mobile.harsoft.automatedselfmarket.model.Pelanggan

data class ResponsePelanggan(
    val status_code: Int?,
    val message: String?,
    val data_pelanggan: List<Pelanggan?>?
)