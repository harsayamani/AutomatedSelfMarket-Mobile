package com.mobile.harsoft.automatedselfmarket.api

import com.mobile.harsoft.automatedselfmarket.model.response.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("tambahPelanggan")
    fun tambahPelanggan(
        @Field("nama_pelanggan") namaPelanggan: String,
        @Field("alamat") alamatPelanggan: String
    ): Call<ResponsePelanggan>

    @FormUrlEncoded
    @POST("loginToko")
    fun loginToko(
        @Field("id_toko") idToko: String,
        @Field("id_pelanggan") idPelanggan: String
    ): Call<ResponseToko>

    @FormUrlEncoded
    @POST("getRiwayatToko")
    fun getRiwayatToko(
        @Field("id_pelanggan") idPelanggan: String
    ): Call<ResponseRiwayatToko>

    @FormUrlEncoded
    @POST("getTransaksi")
    fun getTransaksi(
        @Field("id_pelanggan") idPelanggan: String
    ): Call<ResponseTransaksi>

    @FormUrlEncoded
    @POST("getProduk")
    fun scanProduk(
        @Field("id_produk") idProduk: String,
        @Field("id_toko") idToko: String
    ): Call<ResponseProduk>

    @FormUrlEncoded
    @POST("transaksi/proses")
    fun transaksiProses(
        @Field("id_toko") idToko: String,
        @Field("id_pelanggan") idPelanggan: String,
        @Field("total_tagihan") totalTagihan: Int
    ): Call<Response>

    @FormUrlEncoded
    @POST("tambahKeranjang")
    fun tambahKeranjang(
        @Field("id_produk") idToko: String,
        @Field("kuantitas") kuantitas: Int,
        @Field("harga_update") hargaUpdate: Int,
        @Field("id_pelanggan") idPelanggan: String,
        @Field("status") status: Int
    ): Call<Response>

    @FormUrlEncoded
    @POST("detailTransaksi")
    fun detailTransaksi(
        @Field("id_transaksi") idTransaksi: Int
    ): Call<ResponseDetailTransaksi>
}