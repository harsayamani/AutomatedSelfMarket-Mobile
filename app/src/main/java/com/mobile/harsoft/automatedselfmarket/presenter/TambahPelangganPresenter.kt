package com.mobile.harsoft.automatedselfmarket.presenter

import com.mobile.harsoft.automatedselfmarket.api.ApiRepo
import com.mobile.harsoft.automatedselfmarket.model.response.ResponsePelanggan
import com.mobile.harsoft.automatedselfmarket.util.PreferenceHelper
import com.mobile.harsoft.automatedselfmarket.view.PelangganView
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahPelangganPresenter(
    private val view: PelangganView,
    private val apiRepo: ApiRepo,
    private var preferenceHelper: PreferenceHelper?
) {

    fun tambahPelanggan(namaPelanggan: String?, alamatPelanggan: String?) {
        try {
            doAsync {
                view.showLoading()
                apiRepo.api().tambahPelanggan(namaPelanggan!!, alamatPelanggan!!)
                    .enqueue(object : Callback<ResponsePelanggan> {
                        override fun onFailure(call: Call<ResponsePelanggan>, t: Throwable) {
                            view.hideLoading()
                            view.showAlert(t.message)
                        }

                        override fun onResponse(
                            call: Call<ResponsePelanggan>,
                            response: Response<ResponsePelanggan>
                        ) {
                            val dataPelanggan = response.body()?.data_pelanggan
                            val message = response.body()?.message
                            val statusCode = response.body()?.status_code

                            if (response.isSuccessful) {
                                if (statusCode == 0) {
                                    view.pelangganData(dataPelanggan!!)
                                    preferenceHelper?.putIntro("no")
                                    preferenceHelper?.putIdPelanggan(dataPelanggan[0]?.id_pelanggan!!)
                                    preferenceHelper?.putNamaPelanggan(dataPelanggan[0]?.nama_pelanggan!!)
                                    preferenceHelper?.putAlamatPelanggan(dataPelanggan[0]?.alamat!!)
                                    view.hideLoading()
                                    view.showAlert(message)
                                } else if (statusCode == 1) {
                                    preferenceHelper?.putIntro("")
                                    view.hideLoading()
                                    view.showAlert(message)
                                }
                            } else {
                                view.showAlert(message)
                            }
                        }
                    })
            }
        } catch (e: Exception) {
            view.showAlert(e.message)
        }
    }
}