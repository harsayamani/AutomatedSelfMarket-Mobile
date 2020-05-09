package com.mobile.harsoft.automatedselfmarket.presenter

import com.mobile.harsoft.automatedselfmarket.api.ApiRepo
import com.mobile.harsoft.automatedselfmarket.model.response.ResponseTransaksi
import com.mobile.harsoft.automatedselfmarket.view.TransaksiView
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransaksiPresenter(
    private val view: TransaksiView,
    private val apiRepo: ApiRepo
) {
    fun getTransaksi(idPelanggan: String?) {
        try {
            doAsync {
                view.showLoading()
                apiRepo.api().getTransaksi(idPelanggan!!)
                    .enqueue(object : Callback<ResponseTransaksi> {
                        override fun onFailure(call: Call<ResponseTransaksi>, t: Throwable) {
                            view.showAlert(t.message)
                        }

                        override fun onResponse(
                            call: Call<ResponseTransaksi>,
                            response: Response<ResponseTransaksi>
                        ) {
                            val transaksi = response.body()?.data_transaksi
                            val toko = response.body()?.data_toko
                            val message = response.body()?.message
                            val statusCode = response.body()?.status_code

                            if (response.isSuccessful) {
                                if (statusCode == 0) {
                                    view.dataToko(toko)
                                    view.dataTransaksi(transaksi)
                                    view.hideLoading()
                                } else if (statusCode == 1) {
                                    view.hideLoading()
                                    view.showAlert(message)
                                }
                            } else {
                                view.hideLoading()
                                view.showAlert(response.message())
                            }
                        }
                    })
            }
        } catch (e: Exception) {
            view.showAlert(e.message)
        }
    }
}