package com.mobile.harsoft.automatedselfmarket.presenter

import com.mobile.harsoft.automatedselfmarket.api.ApiRepo
import com.mobile.harsoft.automatedselfmarket.model.response.ResponseDetailTransaksi
import com.mobile.harsoft.automatedselfmarket.view.DetailTransaksiView
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailTransaksiPresenter(
    private val view: DetailTransaksiView,
    private val apiRepo: ApiRepo
) {
    fun getDetailTransaksi(idTransaksi: Int?) {

        try {
            doAsync {
                view.showLoading()
                apiRepo.api().detailTransaksi(idTransaksi!!)
                    .enqueue(object : Callback<ResponseDetailTransaksi> {
                        override fun onFailure(call: Call<ResponseDetailTransaksi>, t: Throwable) {
                            view.hideLoading()
                            view.showAlert("Gagal!")
                        }

                        override fun onResponse(
                            call: Call<ResponseDetailTransaksi>,
                            response: Response<ResponseDetailTransaksi>
                        ) {
                            val message = response.body()?.message
                            val statusCode = response.body()?.status_code
                            val detail = response.body()?.data_detail
                            val keranjang = response.body()?.data_keranjang
                            val produk = response.body()?.data_produk

                            if (response.isSuccessful) {
                                if (statusCode == 0) {
                                    view.dataDetail(detail)
                                    view.dataKeranjang(keranjang)
                                    view.dataProduk(produk)
                                    view.hideLoading()
                                } else {
                                    view.hideLoading()
                                    view.showAlert(message.toString().trim())
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