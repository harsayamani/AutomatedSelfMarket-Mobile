package com.mobile.harsoft.automatedselfmarket.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.mobile.harsoft.automatedselfmarket.model.sqlitemodel.KeranjangSementara
import org.jetbrains.anko.db.*

class DatabaseHelper(context: Context) :
    ManagedSQLiteOpenHelper(context, "DatabaseASM.db", null, 1) {

    companion object {
        private var instance: DatabaseHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseHelper {
            if (instance == null) {
                instance = DatabaseHelper(ctx.applicationContext)
            }
            return instance as DatabaseHelper
        }
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.createTable(
            KeranjangSementara.TABLE_KERANJANG, true,
            KeranjangSementara.KERANJANG_ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            KeranjangSementara.PRODUK_ID to TEXT,
            KeranjangSementara.NAMA_PRODUK to TEXT,
            KeranjangSementara.KATEGORI_PRODUK to TEXT,
            KeranjangSementara.KUANTITAS to INTEGER,
            KeranjangSementara.HARGA_UNIT to INTEGER,
            KeranjangSementara.HARGA_UPDATE to INTEGER,
            KeranjangSementara.PELANGGAN_ID to TEXT,
            KeranjangSementara.STATUS to INTEGER
        )
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.dropTable(KeranjangSementara.TABLE_KERANJANG, true)
    }
}

val Context.database: DatabaseHelper
    get() = DatabaseHelper.getInstance(applicationContext)