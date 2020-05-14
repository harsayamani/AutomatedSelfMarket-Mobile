package com.mobile.harsoft.automatedselfmarket.presenter

import com.mobile.harsoft.automatedselfmarket.api.ApiRepo
import com.mobile.harsoft.automatedselfmarket.model.response.ResponseToko
import com.mobile.harsoft.automatedselfmarket.util.PreferenceHelper2
import com.mobile.harsoft.automatedselfmarket.view.TokoView
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginTokoPresenter(
    private val view: TokoView,
    private val apiRepo: ApiRepo,
    private var preferenceHelper: PreferenceHelper2?
) {
    fun loginToko(idToko: String?, idPelanggan: String?) {
        try {
            doAsync {
                view.showLoading()
                apiRepo.api().loginToko(idToko!!, idPelanggan!!)
                    .enqueue(object : Callback<ResponseToko> {
                        override fun onFailure(call: Call<ResponseToko>, t: Throwable) {
                            view.hideLoading()
                            view.showAlert(t.message)
                        }

                        override fun onResponse(
                            call: Call<ResponseToko>,
                            response: Response<ResponseToko>
                        ) {
                            val dataToko = response.body()?.data_toko
                            val message = response.body()?.message
                            val statusCode = response.body()?.status_code

                            if (response.isSuccessful) {
                                if (statusCode == 0) {
                                    preferenceHelper?.putIdToko(dataToko?.get(0)?.id_toko!!)
                                    preferenceHelper?.putNamaToko(dataToko?.get(0)?.nama_toko!!)
                                    preferenceHelper?.putAlamatToko(dataToko?.get(0)?.alamat!!)
                                    preferenceHelper?.putNamaPemilik(dataToko?.get(0)?.nama_pemilik!!)
                                    view.hideLoading()
                                    view.showAlert(message)
                                } else if (statusCode == 1) {
                                    view.hideLoading()
                                    view.showAlert(message)
                                }
                            } else {
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