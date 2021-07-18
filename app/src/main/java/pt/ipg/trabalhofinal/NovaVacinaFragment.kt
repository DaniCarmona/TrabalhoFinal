package pt.ipg.trabalhofinal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.ipg.trabalhofinal.databinding.FragmentNovaVacinaBinding
import java.util.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NovaVacinaFragment : Fragment(){

    private var _binding: FragmentNovaVacinaBinding? = null

    private lateinit var editTextNomeVacina: EditText
    private lateinit var editTextEmailFornecedor: EditText
    private lateinit var editTextStockInicial: EditText


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_nova_vacina

        _binding = FragmentNovaVacinaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNomeVacina = view.findViewById(R.id.editTextNomeVacina)
        editTextEmailFornecedor = view.findViewById(R.id.editTextEmailFornecedor)
        editTextStockInicial = view.findViewById(R.id.editTextStockInicial)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navegaListaVacinas() {
        findNavController().navigate(R.id.action_novaVacinaFragment_to_listaVacinasFragment)
    }

    fun guardar() {
        val nomeVacina = editTextNomeVacina.text.toString()
        if (nomeVacina.isEmpty()) {
            editTextNomeVacina.setError(getString(R.string.preencha_nome_vacina))
            editTextNomeVacina.requestFocus()
            return
        }

        val emailFornecedor = editTextEmailFornecedor.text.toString()
        if (emailFornecedor.isEmpty()) {
            editTextEmailFornecedor.setError(getString(R.string.preencha_email_fornecedor))
            editTextEmailFornecedor.requestFocus()
            return
        }

        val stockInicialString = editTextStockInicial.text.toString()
        if (stockInicialString.isEmpty()) {
            editTextStockInicial.setError(getString(R.string.preencha_stock_inicial))
            editTextStockInicial.requestFocus()
            return
        }

        val stockInicial = stockInicialString.toInt()
        if (stockInicial <= 0) {
            editTextStockInicial.setError(getString(R.string.valor_invalido))
            editTextStockInicial.requestFocus()
            return
        }

        val vacina = Vacina(nomeVacina = nomeVacina, email = emailFornecedor, stock = stockInicial )

        val uri = activity?.contentResolver?.insert(
            ContentProviderArmazemVacinas.ENDERECO_VACINAS,
            vacina.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextNomeVacina,
                getString(R.string.erro_inserir_vacina),
                Snackbar.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.vacina_guardada_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaVacinas()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_nova_vacina -> guardar()
            R.id.action_cancelar_nova_vacina -> navegaListaVacinas()
            else -> return false
        }

        return true
    }


}
