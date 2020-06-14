package com.mobile.harsoft.automatedselfmarket.view

interface ProsesView {
    fun showAlert(message: String?)
    fun showAlertDialog(message: String?)
    fun showLoading()
    fun hideLoading()
}