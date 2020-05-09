package com.mobile.harsoft.automatedselfmarket.view

import com.mobile.harsoft.automatedselfmarket.model.RiwayatToko
import com.mobile.harsoft.automatedselfmarket.model.Toko

interface RiwayatTokoView {
    fun showAlert(message: String?)
    fun showLoading()
    fun hideLoading()
    fun dataRiwayatToko(data: List<RiwayatToko?>)
    fun dataToko(data: List<Toko?>)
}