package pt.ipg.trabalhofinal

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TesteBaseDados {
    private fun getAppContext() = InstrumentationRegistry.getInstrumentation().targetContext
    private fun getBdArmazemVacinasOpenHelper() = BdArmazemVacinasOpenHelper(getAppContext())

    private fun getTabelaUtentes(db: SQLiteDatabase) = TabelaUtentes(db)
    private fun getTabelaVacinas(db: SQLiteDatabase) = TabelaVacinas(db)
    private fun getTabelaFornecedor(db: SQLiteDatabase) = TabelaFornecedor(db)
    private fun getTabelaDose1(db: SQLiteDatabase) = TabelaDose1(db)
    private fun getTabelaDose2(db: SQLiteDatabase) = TabelaDose2(db)

    private fun insereUtente(tabelaUtentes: TabelaUtentes, utente: Utente): Long {
        val id = tabelaUtentes.insert(utente.toContentValues())
        assertNotEquals(-1, id)
        return id
    }

    private fun insereVacina(tabelaVacinas: TabelaVacinas, vacina: Vacina): Long {
        val id = tabelaVacinas.insert(vacina.toContentValues())
        assertNotEquals(-1, id)
        return id
    }

    private fun insereFornecedor(tabelaFornecedor: TabelaFornecedor, fornecedor: Fornecedor): Long {
        val id = tabelaFornecedor.insert(fornecedor.toContentValues())
        assertNotEquals(-1, id)
        return id
    }

    private fun insereDose1(tabelaDose1: TabelaDose1, dose1: Dose1): Long {
        val id = tabelaDose1.insert(dose1.toContentValues())
        assertNotEquals(-1, id)
        return id
    }

    private fun insereDose2(tabelaDose2: TabelaDose2, dose2: Dose2): Long {
        val id = tabelaDose2.insert(dose2.toContentValues())
        assertNotEquals(-1, id)
        return id
    }






    @Before
    fun apagaBaseDados() {
        getAppContext().deleteDatabase(BdArmazemVacinasOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados(){

        val db = getBdArmazemVacinasOpenHelper().readableDatabase
        assert(db.isOpen)

        db.close()
    }



}