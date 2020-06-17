package com.mobile.harsoft.automatedselfmarket.model.response

import com.mobile.harsoft.automatedselfmarket.model.DetailTransaksi
import com.mobile.harsoft.automatedselfmarket.model.Keranjang
import com.mobile.harsoft.automatedselfmarket.model.Produk

data class ResponseDetailTransaksi(
    val status_code: Int?,
    val message: String?,
    val data_detail: List<DetailTransaksi?>?,
    val data_keranjang: List<Keranjang?>?,
    val data_produk: List<Produk?>?
)