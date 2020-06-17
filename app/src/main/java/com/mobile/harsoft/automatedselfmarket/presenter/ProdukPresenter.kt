package com.mobile.harsoft.automatedselfmarket.presenter

import com.mobile.harsoft.automatedselfmarket.api.ApiRepo
import com.mobile.harsoft.automatedselfmarket.database.DatabaseHelper
import com.mobile.harsoft.automatedselfmarket.model.KeranjangLocal
import com.mobile.harsoft.automatedselfmarket.model.response.ResponseProduk
import com.mobile.harsoft.automatedselfmarket.model.sqlitemodel.KeranjangSementara
import com.mobile.harsoft.automatedselfmarket.util.PreferenceHelper
import com.mobile.harsoft.automatedselfmarket.view.ProdukView
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.update
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("IMPLICIT_CAST_TO_ANY")
class ProdukPresenter(
    private val view: ProdukView,
    private val apiRepo: ApiRepo,
    private val databaseHelper: DatabaseHelper,
    private val preferenceHelper: PreferenceHelper
) {

    fun scanProduk(id_produk: String?, id_toko: String?) {
        try {
            doAsync {
                view.showLoading()
                apiRepo.api().scanProduk(id_produk!!, id_toko!!)
                    .enqueue(object : Callback<ResponseProduk> {
                        override fun onFailure(call: Call<ResponseProduk>, t: Throwable) {
                            view.hideLoading()
                            view.showAlert(t.message)
                        }

                        override fun onResponse(
                            call: Call<ResponseProduk>,
                            response: Response<ResponseProduk>
                        ) {
                            val message = response.body()?.message
                            val statusCode = response.body()?.status_code
                            val dataProduk = response.body()?.data_produk
                            val dataKategori = response.body()?.data_kategori
                            var kategori: String? = null

                            if (response.isSuccessful) {
                                if (statusCode == 0) {
                                    view.dataKategoriProduk(dataKategori)
                                    view.dataProduk(dataProduk)

                                    for (data in dataKategori!!) {
                                        if (dataProduk?.first()?.id_kategori_produk == data?.id_kategori_produk) {
                                            kategori = data?.kategori
                                            break
                                        }
                                    }

                                    databaseHelper.use {
                                        val result =
                                            select(KeranjangSementara.TABLE_KERANJANG).whereArgs(
                                                "(" + KeranjangSementara.PRODUK_ID + "={id})",
                                                "id" to id_produk
                                            )
                                        val state = result.parseList(classParser<KeranjangLocal>())

                                        if (state.isEmpty()) {
                                            insert(
                                                KeranjangSementara.TABLE_KERANJANG,
                                                KeranjangSementara.PRODUK_ID to id_produk,
                                                KeranjangSementara.NAMA_PRODUK to dataProduk?.first()?.nama_produk,
                                                KeranjangSementara.KATEGORI_PRODUK to kategori,
                                                KeranjangSementara.KUANTITAS to 1,
                                                KeranjangSementara.HARGA_UNIT to dataProduk?.first()?.harga,
                                                KeranjangSementara.HARGA_UPDATE to dataProduk?.first()?.harga,
                                                KeranjangSementara.PELANGGAN_ID to preferenceHelper.getIdPelanggan(),
                                                KeranjangSementara.STATUS to 0
                                            )
                                        } else {
                                            update(
                                                KeranjangSementara.TABLE_KERANJANG,
                                                KeranjangSementara.KUANTITAS to state.first().kuantitas!! + 1,
                                                KeranjangSementara.HARGA_UPDATE to state.first().harga_unit!! * (state.first().kuantitas!! + 1)
                                            ).whereArgs(
                                                "(" + KeranjangSementara.KERANJANG_ID + "={id})",
                                                "id" to state.first().id_keranjang!!
                                            ).exec()
                                        }
                                    }
                                    view.showAlert(message)
                                    view.hideLoading()
                                } else if (statusCode == 1) {
                                    view.hideLoading()
                                    view.showAlert(message)
                                }
                            } else {
                                view.hideLoading()
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