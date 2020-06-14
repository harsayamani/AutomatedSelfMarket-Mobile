package com.mobile.harsoft.automatedselfmarket

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mobile.harsoft.automatedselfmarket.database.database
import com.mobile.harsoft.automatedselfmarket.model.sqlitemodel.KeranjangSementara
import com.mobile.harsoft.automatedselfmarket.tokofragments.DashboardTokoFragment
import com.mobile.harsoft.automatedselfmarket.tokofragments.KeranjangFragment
import com.mobile.harsoft.automatedselfmarket.util.PreferenceHelper2
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange
import kotlinx.android.synthetic.main.activity_toko.*
import org.jetbrains.anko.db.delete


class TokoActivity : AppCompatActivity() {

    private var preferenceHelper: PreferenceHelper2? = null
    private var menuItem: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toko)
        setSupportActionBar(toolbar)
        pusherNotification()
        bottomNav()
        setToko()
    }

    private fun pusherNotification() {
        try {
            val options = PusherOptions()
            options.setCluster("ap1")

            val pusher = Pusher("cc57b7190625d1c6e36f", options)

            pusher.connect(object : ConnectionEventListener {
                override fun onConnectionStateChange(change: ConnectionStateChange) {
                    Log.i(
                        "Pusher",
                        "State changed from ${change.previousState} to ${change.currentState}"
                    )
                }

                override fun onError(
                    message: String,
                    code: String,
                    e: Exception
                ) {
                    Log.i(
                        "Pusher",
                        "There was a problem connecting! code ($code), message ($message), exception($e)"
                    )
                }
            }, ConnectionState.ALL)

            val channel = pusher.subscribe("harsoft-channel")
            channel.bind("transaksiselesai-event") { event ->
                Log.i("Pusher", "Received event with data: $event")
                val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
                alertDialog.setTitle("Alert!")
                alertDialog.setIcon(R.mipmap.logo)
                alertDialog.setMessage("Transaksi Selesai")
                alertDialog.setNegativeButton("Tutup") { dialogInterface, i ->

                }
                alertDialog.show()
            }
        }catch (e:Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toko, menu)
        menuItem = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.scanQR -> {
                startActivity(Intent(this, ScanProdukActivity::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item!!)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setToko() {
        preferenceHelper = PreferenceHelper2(this)
        tvNamaToko.text = "Toko " + preferenceHelper!!.getNamaToko()
        tvAlamatToko.text = preferenceHelper!!.getAlamatToko()
    }

    override fun onBackPressed() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("Alert!")
        alertDialog.setIcon(R.mipmap.logo)
        alertDialog.setMessage("Apakah anda sudah menyelesaikan belanja di Toko " + preferenceHelper?.getNamaToko() + "?")
        alertDialog.setPositiveButton("Ya") { dialogInterface, i ->
            database.use {
                delete(KeranjangSementara.TABLE_KERANJANG)
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        alertDialog.setNegativeButton("Tidak") { dialogInterface, i ->
        }
        alertDialog.show()
    }

    private fun bottomNav() {
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menuDashboardToko -> {
                    addFragment(DashboardTokoFragment())
                }
                R.id.menuKeranjang -> {
                    addFragment(KeranjangFragment())
                }
            }
            true
        }
        bottomNavigation.selectedItemId = R.id.menuDashboardToko
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
}
