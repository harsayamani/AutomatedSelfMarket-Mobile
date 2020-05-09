package com.mobile.harsoft.automatedselfmarket.view

import com.mobile.harsoft.automatedselfmarket.model.Pelanggan

interface PelangganView {
    fun showAlert(message: String?)
    fun showLoading()
    fun hideLoading()
    fun pelangganData(data: List<Pelanggan?>)
}