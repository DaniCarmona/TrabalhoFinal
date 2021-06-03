package pt.ipg.trabalhofinal

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Dose2 (var id: Long = -1, var data: Int, var idUtente: Long, var idVacina: Long) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaDose2.CAMPO_DATA_ADMINISTRACAO, data)
            put(TabelaDose2.CAMPO_ID_UTENTE, idUtente)
            put(TabelaDose2.CAMPO_ID_VACINA, idVacina)
        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Dose2 {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colData = cursor.getColumnIndex(TabelaDose2.CAMPO_DATA_ADMINISTRACAO)
            val colIdUtente = cursor.getColumnIndex(TabelaDose2.CAMPO_ID_UTENTE)
            val colIdVacina = cursor.getColumnIndex(TabelaDose2.CAMPO_ID_VACINA)

            val id = cursor.getLong(colId)
            val data = cursor.getInt(colData)
            val idUtente = cursor.getLong(colIdUtente)
            val idVacina = cursor.getLong(colIdUtente)

            return Dose2(id, data, idUtente, idVacina)
        }
    }
}