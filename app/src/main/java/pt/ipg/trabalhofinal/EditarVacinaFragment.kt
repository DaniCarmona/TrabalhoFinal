package pt.ipg.trabalhofinal

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class EditarVacinaFragment : Fragment(){



    private lateinit var editTextNomeVacina: EditText
    private lateinit var editTextEmailFornecedor: EditText
    private lateinit var editTextStock: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_editar_vacinas

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editar_vacina, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNomeVacina = view.findViewById(R.id.editTextNomeVacinaEdita)
        editTextEmailFornecedor = view.findViewById(R.id.editTextEmailFornecedorEdita)
        editTextStock = view.findViewById(R.id.editTextStockEdita)

        editTextNomeVacina.setText(DadosApp.vacinaSelecionada!!.nomeVacina)
        editTextEmailFornecedor.setText(DadosApp.vacinaSelecionada!!.email)
        editTextStock.setText(DadosApp.vacinaSelecionada!!.stock.toString())
    }

    fun navegaListaVacinas() {
        findNavController().navigate(R.id.action_editarVacinaFragment_to_listaVacinasFragment)
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

        val stockString = editTextStock.text.toString()
        if (stockString.isEmpty()) {
            editTextStock.setError(getString(R.string.preencha_stock))
            editTextStock.requestFocus()
            return
        }

        val stock = stockString.toInt()
        if (stock <= 0) {
            editTextStock.setError(getString(R.string.valor_invalido))
            editTextStock.requestFocus()
            return
        }


        val vacina = DadosApp.vacinaSelecionada!!
        vacina.nomeVacina = nomeVacina
        vacina.email = emailFornecedor
        vacina.stock = stock

        val uriVacina = Uri.withAppendedPath(
            ContentProviderArmazemVacinas.ENDERECO_VACINAS,
            vacina.id.toString()
        )

        val registos = activity?.contentResolver?.update(
            uriVacina,
            vacina.toContentValues(),
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                R.string.erro_editar_vacina,
                Toast.LENGTH_LONG
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
            R.id.action_guardar_edita_vacina -> guardar()
            R.id.action_cancelar_edita_vacina -> navegaListaVacinas()
            else -> return false
        }

        return true
    }


}
