package pt.ipg.trabalhofinal

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MenuPrincipalFragment : Fragment(){

    private lateinit var buttonUtentes: Button
    private lateinit var buttonDoses: Button
    private lateinit var buttonVacinas: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_menu_principal

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_principal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonUtentes = view.findViewById(R.id.buttonUtentes)
        buttonDoses = view.findViewById(R.id.buttonDoses)
        buttonVacinas = view.findViewById(R.id.buttonVacinas)

        buttonUtentes.setOnClickListener {
            navegaListaUtentes()
        }
        buttonDoses.setOnClickListener {
            navegaListaDoses()
        }
        buttonVacinas.setOnClickListener {
            navegaListaVacinas()
        }

    }

    fun navegaListaUtentes() {
        findNavController().navigate(R.id.action_menuPrincipalFragment_to_ListaUtentesFragment)
    }

    fun navegaListaDoses() {
        //findNavController().navigate(R.id.action_editarUtenteFragment_to_ListaUtentesFragment)
    }

    fun navegaListaVacinas() {
        //findNavController().navigate(R.id.action_editarUtenteFragment_to_ListaUtentesFragment)
    }


    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            else -> return false
        }

        return true
    }


}
