package com.mobile.harsoft.automatedselfmarket.util

import android.content.Context
import android.content.SharedPreferences
import org.jetbrains.anko.defaultSharedPreferences

class PreferenceHelper2(context: Context) {
    private val NAMA_TOKO = "nama_toko"
    private val ID_TOKO = "id_toko"
    private val ALAMAT_TOKO = "alamat_toko"
    private val NAMA_PEMILIK = "nama_pemilik"
    private val app_prefs: SharedPreferences =
        context.defaultSharedPreferences

//    fun clear() {
//        app_prefs.edit().clear().apply()
//    }

    fun putNamaToko(namaToko: String?) {
        val edit = app_prefs.edit()
        edit.putString(NAMA_TOKO, namaToko)
        edit.apply()
    }

    fun putIdToko(idToko: String?) {
        val edit = app_prefs.edit()
        edit.putString(ID_TOKO, idToko)
        edit.apply()
    }

    fun putAlamatToko(alamatToko: String?) {
        val edit = app_prefs.edit()
        edit.putString(ALAMAT_TOKO, alamatToko)
        edit.apply()
    }

    fun putNamaPemilik(namaPemilik: String?) {
        val edit = app_prefs.edit()
        edit.putString(NAMA_PEMILIK, namaPemilik)
        edit.apply()
    }

    fun getNamaToko(): String? {
        return app_prefs.getString(NAMA_TOKO, "")
    }

    fun getIdToko(): String? {
        return app_prefs.getString(ID_TOKO, "")
    }

    fun getAlamatToko(): String? {
        return app_prefs.getString(ALAMAT_TOKO, "")
    }

    fun getNamaPemilik(): String? {
        return app_prefs.getString(NAMA_PEMILIK, "")
    }
}