package pt.ipg.trabalhofinal

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaDose2(db: SQLiteDatabase) : BaseColumns {
    private val db : SQLiteDatabase = db
    fun cria() = db?.execSQL(
        "CREATE TABLE $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_DATA_ADMINISTRACAO INTEGER NOT NULL, $CAMPO_ID_UTENTE INTEGER NOT NULL, $CAMPO_ID_VACINA INTEGER NOT NULL, FOREIGN KEY ($CAMPO_ID_UTENTE) REFERENCES ${TabelaUtentes.NOME_TABELA}, FOREIGN KEY ($CAMPO_ID_VACINA) REFERENCES ${TabelaVacinas.NOME_TABELA})"
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
        selection: String,
        selectionArgs: Array<String>,
        groupBy: String, having: String,
        orderBy: String
    ): Cursor? {
        return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object{
        const val NOME_TABELA = "dose2"
        const val CAMPO_DATA_ADMINISTRACAO = "dataadministracao"
        const val CAMPO_ID_UTENTE = "id_utente"
        const val CAMPO_ID_VACINA = "id_vacina"

        val TODOS_CAMPOS =arrayOf(BaseColumns._ID, CAMPO_DATA_ADMINISTRACAO, CAMPO_ID_UTENTE, CAMPO_ID_VACINA)

    }

}