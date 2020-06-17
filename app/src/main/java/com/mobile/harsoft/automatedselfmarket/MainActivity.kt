package com.mobile.harsoft.automatedselfmarket

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mobile.harsoft.automatedselfmarket.mainfragments.DashboardFragment
import com.mobile.harsoft.automatedselfmarket.mainfragments.RiwayatTokoFragment
import com.mobile.harsoft.automatedselfmarket.mainfragments.TransaksiFragment
import com.mobile.harsoft.automatedselfmarket.util.PreferenceHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var preferenceHelper: PreferenceHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        bottomNav()
        setPelanggan()
    }

    private fun setPelanggan() {
        preferenceHelper = PreferenceHelper(this)
        tvNamaPelanggan.text = preferenceHelper?.getNamaPelanggan()
        tvAlamatPelanggan.text = preferenceHelper?.getAlamatPelanggan()
    }

    private fun bottomNav() {
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menuDashboard -> {
                    addFragment(DashboardFragment())
                }
                R.id.menuTransaksi -> {
                    addFragment(TransaksiFragment())
                }
                R.id.menuRiwayatToko -> {
                    addFragment(RiwayatTokoFragment())
                }
            }
            true
        }
        bottomNavigation.selectedItemId = R.id.menuDashboard
    }

    @SuppressLint("PrivateResource")
    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction().addToBackStack(null)
            .setCustomAnimations(
                R.anim.design_bottom_sheet_slide_in,
                R.anim.design_bottom_sheet_slide_out
            )
            .replace(R.id.content, fragment, fragment.javaClass.simpleName)
            .commit()
    }

    override fun onBackPressed() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("Alert!")
        alertDialog.setIcon(R.mipmap.logo)
        alertDialog.setMessage("Apakah anda ingin keluar?")
        alertDialog.setPositiveButton("Ya") { dialogInterface, i ->
            finishAffinity()
        }
        alertDialog.setNegativeButton("Tidak") { dialogInterface, i ->
        }
        alertDialog.show()
    }
}
