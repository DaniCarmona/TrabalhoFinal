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
    private fun getTabelaDoses(db: SQLiteDatabase) = TabelaDoses(db)

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

    private fun insereDoses(tabelaDoses: TabelaDoses, doses: Doses): Long {
        val id = tabelaDoses.insert(doses.toContentValues())
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

    private fun getDosesBD(
        tabelaDoses: TabelaDoses, id: Long  ): Doses {
        val cursor = tabelaDoses.query(
            TabelaDoses.TODOS_CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())
        return Doses.fromCursor(cursor)
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
    fun consegueInserirVacinas(){
        val db = getBdArmazemVacinasOpenHelper().writableDatabase
        val tabelaVacinas = getTabelaVacinas(db);
        val vacina = Vacina(stock = 2000, nomeVacina = "AstraZeneca", email = "astrazeneca@exemplo.com")

        vacina.id = insereVacina(tabelaVacinas, vacina)

        val vacinaBD = getVacinaBD(tabelaVacinas, vacina.id)
        assertEquals(vacina, vacinaBD)
        db.close()
    }

    @Test
    fun consegueAlterarVacinas(){
        val db = getBdArmazemVacinasOpenHelper().writableDatabase
        val tabelaVacinas = getTabelaVacinas(db);
        val vacina = Vacina(stock = 0, nomeVacina ="?", email = "?")

        vacina.id = insereVacina(tabelaVacinas, vacina)

        vacina.stock = 320
        vacina.nomeVacina = "Pfizer"
        vacina.email = "pfizer@exemplo.com"

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

    @Test
    fun consegueApagarVacinas(){
        val db = getBdArmazemVacinasOpenHelper().writableDatabase
        val tabelaVacinas = getTabelaVacinas(db);
        val vacina = Vacina(stock = 0,nomeVacina ="?", email = "?" )

        vacina.id = insereVacina(tabelaVacinas, vacina)

        val registosEliminados = tabelaVacinas.delete(
            "${BaseColumns._ID}=?",
            arrayOf(vacina.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerVacinas(){
        val db = getBdArmazemVacinasOpenHelper().writableDatabase
        val tabelaVacinas = getTabelaVacinas(db);
        val vacina = Vacina(stock = 456,nomeVacina ="Moderna", email = "moderna@exemplo.com")

        vacina.id = insereVacina(tabelaVacinas, vacina)

        val vacinaBD = getVacinaBD(tabelaVacinas, vacina.id)
        assertEquals(vacina, vacinaBD)

        db.close()
    }

    @Test
    fun consegueInserirUtentes(){
        val db = getBdArmazemVacinasOpenHelper().writableDatabase
        val tabelaUtente = getTabelaUtentes(db)
        val utente = Utente(nome="Aníbal Almeida", telefone = "+355 932978568",email = "almeida@gmail.com", morada = "Rua Vila de Trancoso, Guarda", dataNascimento = 27051970, dose = 2)

        utente.id = insereUtente(tabelaUtente, utente)
        val utenteBD = getUtenteBD(tabelaUtente, utente.id)
        assertEquals(utente, utenteBD)
        db.close()
    }

    @Test
    fun consegueAlterarUtentes(){
        val db = getBdArmazemVacinasOpenHelper().writableDatabase
        val tabelaUtentes = getTabelaUtentes(db)
        val utente = Utente(nome="?", telefone = "?",email = "?", morada = "?", dataNascimento = 0, dose = 0)

        utente.id = insereUtente(tabelaUtentes, utente)
        utente.nome="Amilcar Sousa"
        utente.telefone="+355 965734894"
        utente.email="asousa@hotmai.com"
        utente.morada="Rua da Fonte, Viseu"
        utente.dataNascimento=31061975
        utente.dose=1
        val registosAlterados = tabelaUtentes.update(
            utente.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(utente.id.toString())
        )

        assertEquals(1, registosAlterados)

        db.close()
    }

    @Test
    fun consegueApagarUtentes(){
        val db = getBdArmazemVacinasOpenHelper().writableDatabase
        val tabelaUtentes = getTabelaUtentes(db)
        val utente = Utente(nome="?", telefone = "?",email = "?", morada = "?", dataNascimento = 0, dose = 0)

        utente.id = insereUtente(tabelaUtentes, utente)

        val registosEliminados =tabelaUtentes.delete(
            "${BaseColumns._ID}=?",
            arrayOf(utente.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerUtentes(){
        val db = getBdArmazemVacinasOpenHelper().writableDatabase
        val tabelaUtentes = getTabelaUtentes(db)
        val utente = Utente(nome="David Gonçalves", telefone = "+355 914369458",email = "dg@outlook.com", morada = "Rua dos Pinheiros, Braga", dataNascimento = 4011980, dose = 2)

        utente.id = insereUtente(tabelaUtentes, utente)

        val utenteBD = getUtenteBD(tabelaUtentes, utente.id)
        assertEquals(utente, utenteBD)

        db.close()
    }

    @Test
    fun consegueInserirDoses(){
        val db = getBdArmazemVacinasOpenHelper().writableDatabase
        val tabelaUtente = getTabelaUtentes(db)
        val utente = Utente(nome="Aníba Almeida", telefone = "+355 962978568",email = "almeda@gmail.com", morada = "Rua Vila de Trancso, Guarda", dataNascimento = 27061970, dose = 1)

        utente.id = insereUtente(tabelaUtente, utente)

        val tabelaVacinas = getTabelaVacinas(db);
        val vacina = Vacina(stock = 200, nomeVacina = "AstrZeneca", email = "astrazeeca@exemplo.com")

        vacina.id = insereVacina(tabelaVacinas, vacina)


        val tabelaDoses = getTabelaDoses(db)
        val doses = Doses(data = 25052021, dose = 1, idUtente = utente.id, idVacina = vacina.id)

        doses.id = insereDoses(tabelaDoses, doses)


        val dosesBD = getDosesBD(tabelaDoses, doses.id)
        assertEquals(doses, dosesBD)
        db.close()
    }

    @Test
    fun consegueAlterarDoses(){
        val db = getBdArmazemVacinasOpenHelper().writableDatabase
        val tabelaUtente = getTabelaUtentes(db)
        val utente = Utente(nome="Daniel Martins", telefone = "+355 962978568",email = "martins@gmail.com", morada = "Rua Vila de Trancso, Guarda", dataNascimento = 27061970, dose = 1)

        utente.id = insereUtente(tabelaUtente, utente)

        val tabelaVacinas = getTabelaVacinas(db);
        val vacina = Vacina(stock = 450, nomeVacina = "AsZeneca", email = "astrazca@exemplo.com")

        vacina.id = insereVacina(tabelaVacinas, vacina)


        val tabelaDoses = getTabelaDoses(db)
        val doses = Doses(data = 0, dose = 0, idUtente = utente.id, idVacina = vacina.id)

        doses.id = insereDoses(tabelaDoses, doses)

        val registosAlterados = tabelaDoses.update(
                doses.toContentValues(),
                "${BaseColumns._ID}=?",
                arrayOf(doses.id.toString())
        )

        assertEquals(1, registosAlterados)

        db.close()
    }

    @Test
    fun consegueApagarDoses(){
        val db = getBdArmazemVacinasOpenHelper().writableDatabase
        val tabelaUtente = getTabelaUtentes(db)
        val utente = Utente(nome="Aníba Almeida", telefone = "+355 962978568",email = "almeda@gmail.com", morada = "Rua Vila de Trancso, Guarda", dataNascimento = 27061970, dose = 1)

        utente.id = insereUtente(tabelaUtente, utente)

        val tabelaVacinas = getTabelaVacinas(db);
        val vacina = Vacina(stock = 200, nomeVacina = "AstrZeneca", email = "astrazeeca@exemplo.com")

        vacina.id = insereVacina(tabelaVacinas, vacina)


        val tabelaDoses = getTabelaDoses(db)
        val doses = Doses(data = 0, dose = 0, idUtente = utente.id, idVacina = vacina.id)

        doses.id = insereDoses(tabelaDoses, doses)

        doses.data = 31052021
        doses.dose= 1

        val registosEliminados =tabelaDoses.delete(
                "${BaseColumns._ID}=?",
                arrayOf(doses.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerDoses(){
        val db = getBdArmazemVacinasOpenHelper().writableDatabase
        val tabelaUtente = getTabelaUtentes(db)
        val utente = Utente(nome="Manuel Pereira", telefone = "+355 962978568",email = "almeda@gmail.com", morada = "Rua Sofia, Coimbra", dataNascimento = 15121980, dose = 2)

        utente.id = insereUtente(tabelaUtente, utente)

        val tabelaVacinas = getTabelaVacinas(db);
        val vacina = Vacina(stock = 280, nomeVacina = "AstrZenca", email = "astrazca@exemplo.com")

        vacina.id = insereVacina(tabelaVacinas, vacina)


        val tabelaDoses = getTabelaDoses(db)
        val doses = Doses(data = 1062021, dose = 2, idUtente = utente.id, idVacina = vacina.id)

        doses.id = insereDoses(tabelaDoses, doses)


        val dosesBD = getDosesBD(tabelaDoses, doses.id)
        assertEquals(doses, dosesBD)
        db.close()
    }

}