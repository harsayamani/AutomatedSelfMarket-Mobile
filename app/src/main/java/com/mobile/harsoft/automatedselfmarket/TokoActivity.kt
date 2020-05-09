package com.mobile.harsoft.automatedselfmarket

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.mobile.harsoft.automatedselfmarket.util.PreferenceHelper2
import kotlinx.android.synthetic.main.activity_toko.*

class TokoActivity : AppCompatActivity() {

    private var preferenceHelper: PreferenceHelper2? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toko)
    }

    override fun onResume() {
        preferenceHelper = PreferenceHelper2(this)
        if (!preferenceHelper?.getIdToko()!!.isEmpty()) {
            setToko()
        }
        super.onResume()
    }

    override fun onRestart() {
        preferenceHelper = PreferenceHelper2(this)
        if (!preferenceHelper?.getIdToko()!!.isEmpty()) {
            setToko()
        }
        super.onRestart()
    }

    @SuppressLint("SetTextI18n")
    private fun setToko() {
        tvNamaToko.text = "Toko " + preferenceHelper!!.getNamaToko()
        tvAlamatToko.text = preferenceHelper!!.getAlamatToko()
    }

    override fun onBackPressed() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("Alert!")
        alertDialog.setIcon(R.mipmap.logo)
        alertDialog.setMessage("Apakah anda sudah menyelesaikan belanja di Toko " + preferenceHelper?.getNamaToko() + "?")
        alertDialog.setPositiveButton("Ya") { dialogInterface, i ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        alertDialog.setNegativeButton("Tidak") { dialogInterface, i ->
        }
        alertDialog.show()
    }
}
