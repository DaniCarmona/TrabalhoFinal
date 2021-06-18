package pt.ipg.trabalhofinal

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Utente(var id: Long = -1, var nome: String, var telefone: String, var email: String, var morada: String, var dataNascimento: Date, var dose: Int) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaUtentes.CAMPO_NOME, nome)
            put(TabelaUtentes.CAMPO_TELEFONE, telefone)
            put(TabelaUtentes.CAMPO_EMAIL, email)
            put(TabelaUtentes.CAMPO_MORADA, morada)
            put(TabelaUtentes.CAMPO_DATA_NASCIMENTO, dataNascimento.time)
            put(TabelaUtentes.CAMPO_DOSE, dose)
        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Utente {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNome = cursor.getColumnIndex(TabelaUtentes.CAMPO_NOME)
            val colTelefone = cursor.getColumnIndex(TabelaUtentes.CAMPO_TELEFONE)
            val colEmail = cursor.getColumnIndex(TabelaUtentes.CAMPO_EMAIL)
            val colMorada = cursor.getColumnIndex(TabelaUtentes.CAMPO_MORADA)
            val colDataNascimento = cursor.getColumnIndex(TabelaUtentes.CAMPO_DATA_NASCIMENTO)
            val colDose = cursor.getColumnIndex(TabelaUtentes.CAMPO_DOSE)

            val id = cursor.getLong(colId)
            val nome = cursor.getString(colNome)
            val telefone = cursor.getString(colTelefone)
            val email = cursor.getString(colEmail)
            val morada = cursor.getString(colMorada)
            val dataNascimento = cursor.getLong(colDataNascimento)
            val dose = cursor.getInt(colDose)

            return Utente(id, nome, telefone, email, morada, Date(dataNascimento), dose)
        }
    }
}