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
class EliminaLivroFragment : Fragment() {
    private lateinit var textViewTitulo: TextView
    private lateinit var textViewAutor: TextView
    private lateinit var textViewCategoria: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_apagar_utente

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_apagar_utente, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewTitulo = view.findViewById(R.id.textViewTitulo)
        textViewAutor = view.findViewById(R.id.textViewAutor)
        textViewCategoria = view.findViewById(R.id.textViewCategoria)

        val livro = DadosApp.livroSelecionado!!
        textViewTitulo.setText(livro.titulo)
        textViewAutor.setText(livro.autor)
        textViewCategoria.setText(livro.nomeCategoria)
    }

    fun navegaListaLivros() {
        findNavController().navigate(R.id.action_eliminaLivroFragment_to_listaLivrosFragment)
    }

    fun elimina() {
        val uriLivro = Uri.withAppendedPath(
            ContentProviderLivros.ENDERECO_LIVROS,
            DadosApp.livroSelecionado!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
            uriLivro,
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                R.string.erro_eliminar_livro,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.livro_eliminado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaLivros()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_confirma_eliminar_livro -> elimina()
            R.id.action_cancelar_eliminar_livro -> navegaListaLivros()
            else -> return false
        }

        return true
    }
}