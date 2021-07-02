package pt.ipg.trabalhofinal

import androidx.fragment.app.Fragment

class DadosApp {
    companion object {
        lateinit var activity: MainActivity
        lateinit var fragment: Fragment
        var utenteSelecionado : Utente? = null
    }
}