package pt.ipg.trabalhofinal

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Doses (var id: Long = -1, var data: Int, var dose: Int, var idUtente: Long, var idVacina: Long) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaDoses.CAMPO_DATA_ADMINISTRACAO, data)
            put(TabelaDoses.CAMPO_DOSE, dose)
            put(TabelaDoses.CAMPO_ID_UTENTE, idUtente)
            put(TabelaDoses.CAMPO_ID_VACINA, idVacina)
        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Doses {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colData = cursor.getColumnIndex(TabelaDoses.CAMPO_DATA_ADMINISTRACAO)
            val colDose = cursor.getColumnIndex(TabelaDoses.CAMPO_DOSE)
            val colIdUtente = cursor.getColumnIndex(TabelaDoses.CAMPO_ID_UTENTE)
            val colIdVacina = cursor.getColumnIndex(TabelaDoses.CAMPO_ID_VACINA)

            val id = cursor.getLong(colId)
            val data = cursor.getInt(colData)
            val dose = cursor.getInt(colDose)
            val idUtente = cursor.getLong(colIdUtente)
            val idVacina = cursor.getLong(colIdVacina)

            return Doses(id, data, dose, idUtente, idVacina)
        }
    }
}