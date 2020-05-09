package com.mobile.harsoft.automatedselfmarket.model.response

import com.mobile.harsoft.automatedselfmarket.model.Toko

data class ResponseToko(
    val status_code: Int?,
    val message: String?,
    val data_toko: List<Toko?>?
)