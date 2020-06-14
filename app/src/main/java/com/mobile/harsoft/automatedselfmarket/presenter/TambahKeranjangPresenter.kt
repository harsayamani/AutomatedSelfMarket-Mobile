package com.mobile.harsoft.automatedselfmarket.presenter

import com.mobile.harsoft.automatedselfmarket.api.ApiRepo
import com.mobile.harsoft.automatedselfmarket.model.response.Response
import com.mobile.harsoft.automatedselfmarket.view.ProsesView
import retrofit2.Call
import retrofit2.Callback

class TambahKeranjangPresenter(
    private val view: ProsesView,
    private val apiRepo: ApiRepo
) {
    fun tambahKeranjang(
        id_produk: String?,
        kuantitas: Int?,
        harga_update: Int?,
        id_pelanggan: String?,
        status: Int?
    ) {
        try {
            view.showLoading()
            apiRepo.api().tambahKeranjang(
                id_produk!!,
                kuantitas!!,
                harga_update!!,
                id_pelanggan.toString(),
                status!!
            ).enqueue(object : Callback<Response> {
                override fun onFailure(call: Call<Response>, t: Throwable) {
                    view.hideLoading()
                    view.showAlert("Gagal!")
                }

                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    val statusCode = response.body()?.status_code
                    val message = response.body()?.message

                    if (response.isSuccessful) {
                        if(statusCode == 0){
                            view.hideLoading()
//                            view.showAlert(message)
                        }
                    }else{
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