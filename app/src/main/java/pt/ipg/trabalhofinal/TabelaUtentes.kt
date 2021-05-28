package pt.ipg.trabalhofinal

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaUtentes (db: SQLiteDatabase) : BaseColumns {
    private val db : SQLiteDatabase = db
    fun cria() = db?.execSQL("CREATE TABLE $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOME TEXT NOT NULL, $CAMPO_TELEFONE TEXT NOT NULL ,$CAMPO_EMAIL TEXT, $CAMPO_MORADA TEXT NOT NULL, $CAMPO_DATA_NASCIMENTO INTEGER NOT NULL, $CAMPO_DOSE INTEGER NOT NULL, $CAMPO_DATA_DOSE_1 INTEGER NOT NULL, $CAMPO_DATA_DOSE_2 INTEGER NOT Null, $CAMPO_ID_VACINA INTEGER NOT NULL, FOREIGN KEY ($CAMPO_ID_VACINA) REFERENCES ${TabelaVacinas.NOME_TABELA} )"
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
        const val NOME_TABELA = "utentes"
        const val CAMPO_NOME = "nome"
        const val CAMPO_TELEFONE = "telefone"
        const val CAMPO_EMAIL = "email"
        const val CAMPO_MORADA = "morada"
        const val CAMPO_DATA_NASCIMENTO = "datanascimento"
        const val CAMPO_DOSE = "dose"
        const val CAMPO_DATA_DOSE_1 = "datadose1"
        const val CAMPO_DATA_DOSE_2 = "datadose2"
        const val CAMPO_ID_VACINA = "id_vacina"

    }

}