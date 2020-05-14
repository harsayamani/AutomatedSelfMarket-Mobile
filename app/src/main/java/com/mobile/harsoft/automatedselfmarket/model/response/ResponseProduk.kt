package com.mobile.harsoft.automatedselfmarket.model.response

import com.mobile.harsoft.automatedselfmarket.model.KategoriProduk
import com.mobile.harsoft.automatedselfmarket.model.Produk

class ResponseProduk(
    val status_code: Int?,
    val message: String?,
    val data_produk: List<Produk?>?,
    val data_kategori: List<KategoriProduk?>?
)