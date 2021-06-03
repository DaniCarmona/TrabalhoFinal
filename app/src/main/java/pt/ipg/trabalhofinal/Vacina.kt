package pt.ipg.trabalhofinal

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Vacina (var id: Long = -1, var stock: Int, var idForncedor: Long) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaVacinas.CAMPO_STOCK, stock)
            put(TabelaVacinas.CAMPO_ID_FORNECEDOR, idForncedor)

        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Vacina {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colStock = cursor.getColumnIndex(TabelaVacinas.CAMPO_STOCK)
            val colIdFornecedor = cursor.getColumnIndex(TabelaVacinas.CAMPO_ID_FORNECEDOR)

            val id = cursor.getLong(colId)
            val stock = cursor.getInt(colStock)
            val idFornecedor = cursor.getLong(colIdFornecedor)


            return Vacina(id, stock, idFornecedor)
        }
    }
}