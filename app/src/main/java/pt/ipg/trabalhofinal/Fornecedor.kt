package pt.ipg.trabalhofinal

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Fornecedor (var id: Long = -1, var nome: String, var email: String) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaFornecedor.CAMPO_NOME, nome)
            put(TabelaFornecedor.CAMPO_EMAIL, email)

        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Fornecedor {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNome = cursor.getColumnIndex(TabelaFornecedor.CAMPO_NOME)
            val colEmail = cursor.getColumnIndex(TabelaFornecedor.CAMPO_EMAIL)

            val id = cursor.getLong(colId)
            val nome = cursor.getString(colNome)
            val email = cursor.getString(colEmail)


            return Fornecedor(id, nome, email)
        }
    }
}