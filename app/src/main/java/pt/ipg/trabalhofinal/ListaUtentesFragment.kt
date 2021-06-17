package pt.ipg.trabalhofinal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import pt.ipg.trabalhofinal.databinding.FragmentListaUtentesBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListaUtentesFragment : Fragment() {

    private var _binding: FragmentListaUtentesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListaUtentesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }

    fun navegaNovoLivro() {
        //findNavController().navigate(R.id.action_ListaLivrosFragment_to_NovoLivroFragment)
    }

    fun navegaAlterarLivro() {
        //todo: navegar para o fragmento da edição de um livro
    }

    fun navegaEliminarLivro() {
        //todo: navegar para o fragmento para confirmar eliminação de um livro
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_novo_utente -> navegaNovoLivro()
            R.id.action_editar_utente -> navegaAlterarLivro()
            R.id.action_nova_dose -> navegaEliminarLivro()
            else -> return false
        }

        return true
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}