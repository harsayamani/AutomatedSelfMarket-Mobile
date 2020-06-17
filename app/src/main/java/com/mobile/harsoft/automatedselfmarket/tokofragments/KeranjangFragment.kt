package com.mobile.harsoft.automatedselfmarket.tokofragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.harsoft.automatedselfmarket.R
import com.mobile.harsoft.automatedselfmarket.adapter.KeranjangAdapter
import com.mobile.harsoft.automatedselfmarket.api.ApiRepo
import com.mobile.harsoft.automatedselfmarket.database.database
import com.mobile.harsoft.automatedselfmarket.model.KeranjangLocal
import com.mobile.harsoft.automatedselfmarket.model.sqlitemodel.KeranjangSementara
import com.mobile.harsoft.automatedselfmarket.presenter.ProsesTransaksiPresenter
import com.mobile.harsoft.automatedselfmarket.presenter.TambahKeranjangPresenter
import com.mobile.harsoft.automatedselfmarket.util.PreferenceHelper
import com.mobile.harsoft.automatedselfmarket.util.PreferenceHelper2
import com.mobile.harsoft.automatedselfmarket.view.ProsesView
import kotlinx.android.synthetic.main.fragment_keranjang.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.update


/**
 * A simple [Fragment] subclass.
 */
@Suppress("DEPRECATION")
class KeranjangFragment : Fragment(), ProsesView {

    private var keranjangs: MutableList<KeranjangLocal?> = mutableListOf()
    private var preferenceHelper: PreferenceHelper? = null
    private var preferenceHelper2: PreferenceHelper2? = null
    private lateinit var tambahKeranjang: TambahKeranjangPresenter
    private lateinit var prosesTransaksi: ProsesTransaksiPresenter
    private var hargaUpdate: Int = 0
    private lateinit var adapter: KeranjangAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_keranjang, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferenceHelper = PreferenceHelper(requireContext())
        preferenceHelper2 = PreferenceHelper2(requireContext())

        adapter = KeranjangAdapter(requireContext(), keranjangs, {
            requireContext().database.use {
                update(
                    KeranjangSementara.TABLE_KERANJANG,
                    KeranjangSementara.KUANTITAS to it.kuantitas!! - 1,
                    KeranjangSementara.HARGA_UPDATE to it.harga_unit!! * (it.kuantitas - 1)
                ).whereArgs(
                    "(" + KeranjangSementara.KERANJANG_ID + "={id})",
                    "id" to it.id_keranjang.toString()
                ).exec()
            }
            adapter.notifyDataSetChanged()
            fragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit()

        }, {
            requireContext().database.use {
                update(
                    KeranjangSementara.TABLE_KERANJANG,
                    KeranjangSementara.KUANTITAS to it.kuantitas!! + 1,
                    KeranjangSementara.HARGA_UPDATE to it.harga_unit!! * (it.kuantitas + 1)
                ).whereArgs(
                    "(" + KeranjangSementara.KERANJANG_ID + "={id})",
                    "id" to it.id_keranjang.toString()
                ).exec()
            }
            adapter.notifyDataSetChanged()
            fragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit()
        }, {
            requireContext().database.use {
                delete(
                    KeranjangSementara.TABLE_KERANJANG,
                    "(" + KeranjangSementara.KERANJANG_ID + "={id})",
                    "id" to it.id_keranjang.toString()
                )
            }
            adapter.notifyDataSetChanged()
            fragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit()
        })

        rvKeranjang.layoutManager = LinearLayoutManager(context)
        rvKeranjang.adapter = adapter

        btnBayar.setOnClickListener {
            val alertDialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            alertDialog.setTitle("Alert!")
            alertDialog.setIcon(R.mipmap.logo)
            alertDialog.setMessage("Apakah anda sudah selesai memilih barang di Toko " + preferenceHelper2?.getNamaToko() + "?")
            alertDialog.setPositiveButton("Ya") { dialogInterface, i ->

                val apiRepo = ApiRepo()

                if (tambahKeranjangProses(apiRepo)) {
                    if (transaksiProses(apiRepo)) {
                        requireContext().database.use {
                            delete(KeranjangSementara.TABLE_KERANJANG)
                        }
                        fragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Proses transaksi gagal!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Tambah Keranjang Gagal!", Toast.LENGTH_LONG)
                        .show()
                }
            }
            alertDialog.setNegativeButton("Tidak") { dialogInterface, i ->
            }
            alertDialog.show()
        }
    }

    private fun transaksiProses(apiRepo: ApiRepo): Boolean {
        prosesTransaksi = ProsesTransaksiPresenter(this, apiRepo)
        prosesTransaksi.prosesTransaksi(
            preferenceHelper2?.getIdToko(),
            preferenceHelper?.getIdPelanggan(),
            hargaUpdate
        )
        return true
    }

    private fun tambahKeranjangProses(apiRepo: ApiRepo): Boolean {
        for (data in keranjangs) {
            tambahKeranjang = TambahKeranjangPresenter(this, apiRepo)
            tambahKeranjang.tambahKeranjang(
                data?.id_produk,
                data?.kuantitas,
                data?.harga_update,
                data?.id_pelanggan,
                data?.status
            )
        }
        return true
    }

    override fun onStart() {
        showKeranjang()
        setTotalHarga()
        super.onStart()
    }

    private fun showKeranjang() {
        keranjangs.clear()
        requireContext().database.use {
            val result =
                select(KeranjangSementara.TABLE_KERANJANG).whereArgs(
                    "(" + KeranjangSementara.PELANGGAN_ID + "={id})",
                    "id" to preferenceHelper!!.getIdPelanggan().toString()
                )
            val keranjangList = result.parseList(classParser<KeranjangLocal>())
            keranjangs.addAll(keranjangList)
            adapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setTotalHarga() {
        requireContext().database.use {
            val result =
                select(KeranjangSementara.TABLE_KERANJANG).whereArgs(
                    "(" + KeranjangSementara.PELANGGAN_ID + "={id})",
                    "id" to preferenceHelper!!.getIdPelanggan().toString()
                )
            val state = result.parseList(classParser<KeranjangLocal>())
            hargaUpdate = 0
            for (data in state) {
                hargaUpdate += data.kuantitas!! * data.harga_unit!!
            }
            tvTotalHarga.text = "Rp.$hargaUpdate"
        }
    }

    override fun showAlert(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun showAlertDialog(message: String?) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Alert!")
        alertDialog.setIcon(R.mipmap.logo)
        alertDialog.setMessage(message)
        alertDialog.setNegativeButton("Tutup") { dialogInterface, i ->

        }
        alertDialog.show()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }
}
