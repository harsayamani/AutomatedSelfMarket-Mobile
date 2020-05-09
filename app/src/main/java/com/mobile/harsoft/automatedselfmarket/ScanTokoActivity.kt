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
import com.mobile.harsoft.automatedselfmarket.presenter.LoginTokoPresenter
import com.mobile.harsoft.automatedselfmarket.util.PreferenceHelper
import com.mobile.harsoft.automatedselfmarket.util.PreferenceHelper2
import com.mobile.harsoft.automatedselfmarket.view.TokoView
import kotlinx.android.synthetic.main.activity_scan_toko.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScanTokoActivity : AppCompatActivity(), ZXingScannerView.ResultHandler, TokoView {

    private lateinit var scannerView: ZXingScannerView
    private var preferenceHelper2: PreferenceHelper2? = null
    private var preferenceHelper: PreferenceHelper? = null
    private lateinit var presenter: LoginTokoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_toko)
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

    override fun handleResult(p0: Result?) {
        val request = ApiRepo()
        preferenceHelper2 = PreferenceHelper2(this)
        preferenceHelper = PreferenceHelper(this)

        presenter = LoginTokoPresenter(this, request, preferenceHelper2)
        presenter.loginToko(p0?.text, preferenceHelper!!.getIdPelanggan())

        val intent = Intent(this, TokoActivity::class.java)
        intent.putExtra("nama_toko", preferenceHelper2!!.getNamaToko())
        intent.putExtra("alamat_toko", preferenceHelper2!!.getAlamatToko())
        startActivity(intent)
    }

    override fun showAlert(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }
}
