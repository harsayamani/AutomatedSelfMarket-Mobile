package com.mobile.harsoft.automatedselfmarket.util

import android.content.Context
import android.content.SharedPreferences
import org.jetbrains.anko.defaultSharedPreferences

class PreferenceHelper(private val context: Context) {
    private val INTRO = "intro"
    private val NAMA_PELANGGAN = "nama_pelanggan"
    private val ID_PELANGGAN = "id_pelanggan"
    private val ALAMAT_PELANGGAN = "alamat_pelanggan"
    private val app_prefs: SharedPreferences = context.defaultSharedPreferences

    fun putIntro(loginorout: String) {
        val edit = app_prefs.edit()
        edit.putString(INTRO, loginorout)
        edit.apply()
    }

    fun putNamaPelanggan(namaPelanggan: String) {
        val edit = app_prefs.edit()
        edit.putString(NAMA_PELANGGAN, namaPelanggan)
        edit.apply()
    }

    fun putIdPelanggan(idPelanggan: String) {
        val edit = app_prefs.edit()
        edit.putString(ID_PELANGGAN, idPelanggan)
        edit.apply()
    }

    fun putAlamatPelanggan(alamatPelanggan: String) {
        val edit = app_prefs.edit()
        edit.putString(ALAMAT_PELANGGAN, alamatPelanggan)
        edit.apply()
    }

    fun getIntro(): String? {
        return app_prefs.getString(INTRO, "")
    }

    fun getNamaPelanggan(): String? {
        return app_prefs.getString(NAMA_PELANGGAN, "")
    }

    fun getIdPelanggan(): String? {
        return app_prefs.getString(ID_PELANGGAN, "")
    }

    fun getAlamatPelanggan(): String? {
        return app_prefs.getString(ALAMAT_PELANGGAN, "")
    }
}