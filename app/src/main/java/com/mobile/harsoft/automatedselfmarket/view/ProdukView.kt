package com.mobile.harsoft.automatedselfmarket.view

import com.mobile.harsoft.automatedselfmarket.model.KategoriProduk
import com.mobile.harsoft.automatedselfmarket.model.Produk

interface ProdukView {
    fun showAlert(message: String?)
    fun showLoading()
    fun hideLoading()
    fun dataProduk(data: List<Produk?>?)
    fun dataKategoriProduk(data: List<KategoriProduk?>?)
}