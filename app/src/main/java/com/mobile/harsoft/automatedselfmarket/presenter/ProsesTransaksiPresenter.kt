package com.mobile.harsoft.automatedselfmarket.presenter

import com.mobile.harsoft.automatedselfmarket.api.ApiRepo
import com.mobile.harsoft.automatedselfmarket.model.response.Response
import com.mobile.harsoft.automatedselfmarket.view.ProsesView
import retrofit2.Call
import retrofit2.Callback

class ProsesTransaksiPresenter(
    private val view: ProsesView,
    private val apiRepo: ApiRepo
) {
    fun prosesTransaksi(idToko: String?, idPelanggan: String?, totalTagihan: Int?) {
        try {
            view.showLoading()
            apiRepo.api().transaksiProses(idToko!!, idPelanggan!!, totalTagihan!!)
                .enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>, t: Throwable) {
                        view.hideLoading()
                        view.showAlert("Gagal Transaksi")
                    }

                    override fun onResponse(
                        call: Call<Response>,
                        response: retrofit2.Response<Response>
                    ) {
                        val statusCode = response.body()?.status_code
                        val message = response.body()?.message

                        if (response.isSuccessful) {
                            if (statusCode == 0) {
                                view.hideLoading()
                                view.showAlertDialog(message)
                            } else{
                                view.hideLoading()
                                view.showAlert(message)
                            }
                        } else {
                            view.hideLoading()
                            view.showAlert(response.message())
                        }
                    }
                })

        } catch (e: Exception) {
            view.showAlert(e.message)
        }
    }

}