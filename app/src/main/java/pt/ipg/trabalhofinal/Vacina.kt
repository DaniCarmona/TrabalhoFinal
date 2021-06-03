package pt.ipg.trabalhofinal

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Vacina (var id: Long = -1, var stock: Int, var forncedor: String, var email: String) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaVacinas.CAMPO_STOCK, stock)
            put(TabelaVacinas.CAMPO_FORNECEDOR, forncedor)
            put(TabelaVacinas.CAMPO_EMAIL_FORNECEDOR, email)

        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Vacina {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colStock = cursor.getColumnIndex(TabelaVacinas.CAMPO_STOCK)
            val colFornecedor = cursor.getColumnIndex(TabelaVacinas.CAMPO_FORNECEDOR)
            val colEmail = cursor.getColumnIndex(TabelaVacinas.CAMPO_EMAIL_FORNECEDOR)

            val id = cursor.getLong(colId)
            val stock = cursor.getInt(colStock)
            val fornecedor = cursor.getString(colFornecedor)
            val email = cursor.getString(colEmail)

            return Vacina(id, stock, fornecedor, email)
        }
    }
}