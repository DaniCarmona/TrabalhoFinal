package pt.ipg.trabalhofinal

class DadosApp {
    companion object {
        lateinit var activity: MainActivity
        lateinit var listaUtentesFragment: ListaUtentesFragment
        var utenteSelecionado : Utente? = null
    }
}