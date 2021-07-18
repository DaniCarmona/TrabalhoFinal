package pt.ipg.trabalhofinal

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass.
 * Use the [EliminaLivroFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VerDadosUtenteFragment : Fragment() {
    private lateinit var textViewNome: TextView
    private lateinit var textViewDoses: TextView
    private lateinit var textViewTelefone: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var textViewMorada: TextView
    private lateinit var textViewDataNascimento: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_ver_dados_utente

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ver_dados_utente, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewNome = view.findViewById(R.id.textViewNomeVer)
        textViewDoses = view.findViewById(R.id.textViewDosesVer)
        textViewTelefone = view.findViewById(R.id.textViewTelefoneVer)
        textViewEmail = view.findViewById(R.id.textViewEmailVer)
        textViewMorada = view.findViewById(R.id.textViewMoradaVer)
        textViewDataNascimento = view.findViewById(R.id.textViewDataNascimentoVer)

        val utente = DadosApp.utenteSelecionado!!
        textViewNome.setText(utente.nome)
        textViewDoses.setText(utente.dose.toString())
        textViewTelefone.setText(utente.telefone)
        textViewEmail.setText(utente.email)
        textViewMorada.setText(utente.morada)
        textViewDataNascimento.setText("${utente.dataNascimento.date}/${utente.dataNascimento.month+1}/${utente.dataNascimento.year+1900}")
    }

    fun navegaListaUtentes() {
        findNavController().navigate(R.id.action_verDadosUtenteFragment_to_ListaUtentesFragment)
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_voltar -> navegaListaUtentes()
            else -> return false
        }

        return true
    }
}