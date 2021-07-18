package pt.ipg.trabalhofinal

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaDoses(db: SQLiteDatabase) : BaseColumns {
    private val db : SQLiteDatabase = db
    fun cria() = db?.execSQL(
        "CREATE TABLE $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_DATA_ADMINISTRACAO INTEGER NOT NULL, $CAMPO_DOSE INTEGER NOT NULL, $CAMPO_ID_UTENTE INTEGER NOT NULL, $CAMPO_ID_VACINA INTEGER NOT NULL, FOREIGN KEY ($CAMPO_ID_UTENTE) REFERENCES ${TabelaUtentes.NOME_TABELA}, FOREIGN KEY ($CAMPO_ID_VACINA) REFERENCES ${TabelaVacinas.NOME_TABELA})"
    )

    fun insert(values: ContentValues): Long {
        return db.insert(NOME_TABELA, null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(NOME_TABELA, values, whereClause, whereArgs)
    }

    fun delete(whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(NOME_TABELA, whereClause, whereArgs)
    }

    fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor? {
        val ultimaColuna = columns.size - 1

        var posColNomeUtente = -1
        for (i in 0..ultimaColuna) {
            if (columns[i] == CAMPO_EXTERNO_NOME_UTENTE) {
                posColNomeUtente = i
                break
            }
        }

        var posColNomeVacina = -1
        for (i in 0..ultimaColuna) {
            if (columns[i] == CAMPO_EXTERNO_NOME_VACINA) {
                posColNomeVacina = i
                break
            }
        }

        if (posColNomeUtente == -1 || posColNomeVacina== -1) {
            return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
        }

        var colunas = ""
        for (i in 0..ultimaColuna) {
            if (i > 0) colunas += ","

            colunas += if (i == posColNomeUtente) {
                "${TabelaUtentes.NOME_TABELA}.${TabelaUtentes.CAMPO_NOME} AS $CAMPO_EXTERNO_NOME_UTENTE"
            } else if (i == posColNomeVacina) {
                "${TabelaVacinas.NOME_TABELA}.${TabelaVacinas.CAMPO_NOME_VACINA} AS $CAMPO_EXTERNO_NOME_VACINA"
            } else {
                "${NOME_TABELA}.${columns[i]}"
            }
        }

        val tabelas = "$NOME_TABELA INNER JOIN ${TabelaUtentes.NOME_TABELA} ON ${TabelaUtentes.NOME_TABELA}.${BaseColumns._ID}=$CAMPO_ID_UTENTE INNER JOIN ${TabelaVacinas.NOME_TABELA} ON ${TabelaVacinas.NOME_TABELA}.${BaseColumns._ID}=$CAMPO_ID_VACINA"

        var sql = "SELECT $colunas FROM $tabelas"

        if (selection != null) sql += " WHERE $selection"

        if (groupBy != null) {
            sql += " GROUP BY $groupBy"
            if (having != null) " HAVING $having"
        }

        if (orderBy != null) sql += " ORDER BY $orderBy"

        return db.rawQuery(sql, selectionArgs)
    }

    companion object{
        const val NOME_TABELA = "doses"
        const val CAMPO_DATA_ADMINISTRACAO = "dataadministracao"
        const val CAMPO_DOSE = "dose"
        const val CAMPO_ID_UTENTE = "id_utente"
        const val CAMPO_ID_VACINA = "id_vacina"
        const val CAMPO_EXTERNO_NOME_UTENTE = "nome_utente"
        const val CAMPO_EXTERNO_NOME_VACINA = "nome_vacina"


        val TODOS_CAMPOS =arrayOf(BaseColumns._ID, CAMPO_DATA_ADMINISTRACAO, CAMPO_DOSE, CAMPO_ID_UTENTE, CAMPO_ID_VACINA, CAMPO_EXTERNO_NOME_UTENTE, CAMPO_EXTERNO_NOME_VACINA)

    }

}