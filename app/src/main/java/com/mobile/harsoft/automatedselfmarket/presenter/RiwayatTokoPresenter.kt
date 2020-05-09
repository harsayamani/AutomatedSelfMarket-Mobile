package com.mobile.harsoft.automatedselfmarket.presenter

import com.mobile.harsoft.automatedselfmarket.api.ApiRepo
import com.mobile.harsoft.automatedselfmarket.model.response.ResponseRiwayatToko
import com.mobile.harsoft.automatedselfmarket.view.RiwayatTokoView
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RiwayatTokoPresenter(
    private val view: RiwayatTokoView,
    private val apiRepo: ApiRepo
) {
    fun getRiwayatToko(idPelanggan: String?) {
        try {
            doAsync {
                view.showLoading()
                apiRepo.api().getRiwayatToko(idPelanggan!!)
                    .enqueue(object : Callback<ResponseRiwayatToko> {
                        override fun onFailure(call: Call<ResponseRiwayatToko>, t: Throwable) {
                            view.hideLoading()
                            view.showAlert(t.message)
                        }

                        override fun onResponse(
                            call: Call<ResponseRiwayatToko>,
                            response: Response<ResponseRiwayatToko>
                        ) {
                            val message = response.body()?.message
                            val statusCode = response.body()?.status_code
                            val dataRiwayat = response.body()?.data_riwayat
                            val dataToko = response.body()?.data_toko

                            if (response.isSuccessful) {
                                if (statusCode == 0) {
                                    view.dataRiwayatToko(dataRiwayat!!)
                                    view.dataToko(dataToko!!)
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