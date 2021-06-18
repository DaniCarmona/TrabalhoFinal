package pt.ipg.trabalhofinal

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Dose (var id: Long = -1, var data: Date, var dose: Int, var idUtente: Long, var idVacina: Long){
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaDoses.CAMPO_DATA_ADMINISTRACAO, data.time)
            put(TabelaDoses.CAMPO_DOSE, dose)
            put(TabelaDoses.CAMPO_ID_UTENTE, idUtente)
            put(TabelaDoses.CAMPO_ID_VACINA, idVacina)
        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Dose {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colData = cursor.getColumnIndex(TabelaDoses.CAMPO_DATA_ADMINISTRACAO)
            val colDose = cursor.getColumnIndex(TabelaDoses.CAMPO_DOSE)
            val colIdUtente = cursor.getColumnIndex(TabelaDoses.CAMPO_ID_UTENTE)
            val colIdVacina = cursor.getColumnIndex(TabelaDoses.CAMPO_ID_VACINA)

            val id = cursor.getLong(colId)
            val data = cursor.getLong(colData)
            val dose = cursor.getInt(colDose)
            val idUtente = cursor.getLong(colIdUtente)
            val idVacina = cursor.getLong(colIdVacina)

            return Dose(id, Date(data), dose, idUtente, idVacina)
        }
    }
}