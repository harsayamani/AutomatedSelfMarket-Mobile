package com.mobile.harsoft.automatedselfmarket.view

import com.mobile.harsoft.automatedselfmarket.model.Toko
import com.mobile.harsoft.automatedselfmarket.model.Transaksi

interface TransaksiView {
    fun showAlert(message: String?)
    fun showLoading()
    fun hideLoading()
    fun dataTransaksi(data: List<Transaksi?>?)
    fun dataToko(data: List<Toko?>?)
}