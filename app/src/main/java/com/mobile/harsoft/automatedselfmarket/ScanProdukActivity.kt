package com.mobile.harsoft.automatedselfmarket

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.Result
import com.mobile.harsoft.automatedselfmarket.api.ApiRepo
import com.mobile.harsoft.automatedselfmarket.database.database
import com.mobile.harsoft.automatedselfmarket.model.KategoriProduk
import com.mobile.harsoft.automatedselfmarket.model.Produk
import com.mobile.harsoft.automatedselfmarket.presenter.ProdukPresenter
import com.mobile.harsoft.automatedselfmarket.util.PreferenceHelper
import com.mobile.harsoft.automatedselfmarket.util.PreferenceHelper2
import com.mobile.harsoft.automatedselfmarket.view.ProdukView
import kotlinx.android.synthetic.main.activity_scan_toko.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

@Suppress("IMPLICIT_CAST_TO_ANY")
class ScanProdukActivity : AppCompatActivity(), ProdukView, ZXingScannerView.ResultHandler {

    private lateinit var scannerView: ZXingScannerView
    private var preferenceHelper: PreferenceHelper? = null
    private var preferenceHelper2: PreferenceHelper2? = null
    private lateinit var presenter: ProdukPresenter
    private var produk: MutableList<Produk?> = mutableListOf()
    private var kategoriProduk: MutableList<KategoriProduk?> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_produk)
        initScannerView()
    }

    private fun initScannerView() {
        scannerView = ZXingScannerView(this)
        scannerView.setAutoFocus(true)
        scannerView.setResultHandler(this)
        flCamera.addView(scannerView)
    }

    override fun onStart() {
        doRequestPermission()
        super.onStart()
    }

    override fun onResume() {
        scannerView.startCamera()
        super.onResume()
    }

    private fun doRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            100 -> {
                initScannerView()
            }
            else -> {
                /* nothing to do in here */
            }
        }
    }

    override fun onPause() {
        scannerView.stopCamera()
        super.onPause()
    }

    override fun showAlert(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun dataProduk(data: List<Produk?>?) {
        produk.clear()
        produk.addAll(data!!)
    }

    override fun dataKategoriProduk(data: List<KategoriProduk?>?) {
        kategoriProduk.clear()
        kategoriProduk.addAll(data!!)
    }

    override fun handleResult(p0: Result?) {
        preferenceHelper = PreferenceHelper(this)
        preferenceHelper2 = PreferenceHelper2(this)

        val request = ApiRepo()
        presenter = ProdukPresenter(this, request, database, preferenceHelper!!)
        presenter.scanProduk(p0?.text, preferenceHelper2?.getIdToko())
        finish()

        startActivity(
            Intent(
                this,
                TokoActivity::class.java
            )
        )
    }

    override fun onBackPressed() {
        startActivity(
            Intent(
                this,
                TokoActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        )
    }
}
