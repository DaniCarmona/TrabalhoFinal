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

    private fun getUtenteBD(
        tabelaUtentes: TabelaUtentes, id: Long    ): Utente {
        val cursor = tabelaUtentes.query(
            TabelaUtentes.TODOS_CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())
        return Utente.fromCursor(cursor)
    }

    private fun getVacinaBD(
        tabelaVacinas: TabelaVacinas, id: Long    ): Vacina {
        val cursor = tabelaVacinas.query(
            TabelaVacinas.TODOS_CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())
        return Vacina.fromCursor(cursor)
    }
    private fun getFornecedorBD(
        tabelaFornecedor: TabelaFornecedor, id: Long    ): Fornecedor {
        val cursor = tabelaFornecedor.query(
            TabelaFornecedor.TODOS_CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())
        return Fornecedor.fromCursor(cursor)
    }

    private fun getDose1BD(
        tabelaDose1: TabelaDose1, id: Long): Dose1 {
        val cursor = tabelaDose1.query(
            TabelaDose1.TODOS_CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())
        return Dose1.fromCursor(cursor)
    }

    private fun getDose2BD(
        tabelaDose2: TabelaDose2, id: Long    ): Dose2 {
        val cursor = tabelaDose2.query(
            TabelaDose2.TODOS_CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())
        return Dose2.fromCursor(cursor)
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

    @Test
    fun consegueInserirFornecedores(){
        val db = getBdArmazemVacinasOpenHelper().writableDatabase
        val tabelaFornecedor = getTabelaFornecedor(db)
        val fornecedor = Fornecedor(nome="Pfizer", email = "pfizer@exemplo.com" )

        fornecedor.id = insereFornecedor(tabelaFornecedor, fornecedor)
        val fornecedorBD = getFornecedorBD(tabelaFornecedor, fornecedor.id)
        assertEquals(fornecedor, fornecedorBD)
        db.close()
    }

    @Test
    fun consegueAlterarCategorias(){
        val db = getBdArmazemVacinasOpenHelper().writableDatabase
        val tabelaFornecedor = getTabelaFornecedor(db)
        val fornecedor = Fornecedor(nome="?", email = "?" )

        fornecedor.id = insereFornecedor(tabelaFornecedor, fornecedor)
        fornecedor.nome="Moderna"
        fornecedor.email="moderna@exemplo.com"
        val registosAlterados = tabelaFornecedor.update(
            fornecedor.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(fornecedor.id.toString())
        )

        assertEquals(1, registosAlterados)

        db.close()
    }

    @Test
    fun consegueApagarFornecedores(){
        val db = getBdArmazemVacinasOpenHelper().writableDatabase
        val tabelaFornecedor = getTabelaFornecedor(db)
        val fornecedor = Fornecedor(nome="?", email = "?" )


        fornecedor.id = insereFornecedor(tabelaFornecedor, fornecedor)

        val registosEliminados =tabelaFornecedor.delete(
            "${BaseColumns._ID}=?",
            arrayOf(fornecedor.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerFornecedores(){
        val db = getBdArmazemVacinasOpenHelper().writableDatabase
        val tabelaFornecedor = getTabelaFornecedor(db)
        val fornecedor = Fornecedor(nome="AstraZeneca", email = "astrazeneca@exemplo.com" )

        fornecedor.id = insereFornecedor(tabelaFornecedor, fornecedor)

        val fornecedorBD = getFornecedorBD(tabelaFornecedor, fornecedor.id)
        assertEquals(fornecedor, fornecedorBD)

        db.close()
    }

    @Test
    fun consegueInserirVacinas(){
        val db = getBdArmazemVacinasOpenHelper().writableDatabase
        val tabelaFornecedor = getTabelaFornecedor(db)
        val fornecedor = Fornecedor(nome="AstraZeneca", email = "astrazeneca@exemplo.com" )

        fornecedor.id = insereFornecedor(tabelaFornecedor, fornecedor)

        val tabelaVacinas = getTabelaVacinas(db);
        val vacina = Vacina(stock = 2000, idForncedor = fornecedor.id )
        vacina.id = insereVacina(tabelaVacinas, vacina)

        val vacinaBD = getVacinaBD(tabelaVacinas, vacina.id)
        assertEquals(vacina, vacinaBD)
        db.close()
    }

    @Test
    fun consegueAlterarVacinas(){
        val db = getBdArmazemVacinasOpenHelper().writableDatabase
        val tabelaFornecedor = getTabelaFornecedor(db)
        val fornecedor = Fornecedor(nome="Pfizer", email = "pfizer@exemplo.com" )

        fornecedor.id = insereFornecedor(tabelaFornecedor, fornecedor)

        val tabelaVacinas = getTabelaVacinas(db);
        val vacina = Vacina(stock = 0, idForncedor = fornecedor.id )
        vacina.id = insereVacina(tabelaVacinas, vacina)

        vacina.stock = 320
        vacina.idForncedor = fornecedor.id


        val registosAlterados = tabelaVacinas.update(
            vacina.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(vacina.id.toString())
        )

        assertEquals(1, registosAlterados)
        val livroBD = getVacinaBD(tabelaVacinas, vacina.id)
        assertEquals(vacina, livroBD)
        db.close()
    }


}