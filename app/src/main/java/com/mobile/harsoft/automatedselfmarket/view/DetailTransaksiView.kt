package com.mobile.harsoft.automatedselfmarket.view

import com.mobile.harsoft.automatedselfmarket.model.DetailTransaksi
import com.mobile.harsoft.automatedselfmarket.model.Keranjang
import com.mobile.harsoft.automatedselfmarket.model.Produk

interface DetailTransaksiView {
    fun showAlert(message: String?)
    fun showLoading()
    fun hideLoading()
    fun dataDetail(data: List<DetailTransaksi?>?)
    fun dataKeranjang(data: List<Keranjang?>?)
    fun dataProduk(data: List<Produk?>?)
}