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
class AumentarStockFragment : Fragment(){



    private lateinit var editTextQuantidade: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_aumentar_stock

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_aumentar_stock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextQuantidade = view.findViewById(R.id.editTextQuantidade)

    }

    fun navegaListaVacinas() {
        findNavController().navigate(R.id.action_aumentarStockFragment_to_listaVacinasFragment)
    }

    fun guardar() {
        val quantidadeString = editTextQuantidade.text.toString()
        if (quantidadeString.isEmpty()) {
            editTextQuantidade.setError(getString(R.string.preencha_quantidade))
            editTextQuantidade.requestFocus()
            return
        }

        val quantidade = quantidadeString.toInt()
        if (quantidade <= 0) {
            editTextQuantidade.setError(getString(R.string.valor_invalido))
            editTextQuantidade.requestFocus()
            return
        }

        val vacina = DadosApp.vacinaSelecionada!!
        vacina.stock = vacina.stock + quantidade

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
                R.string.erro_aumentar_stock,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.stock_aumentado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaVacinas()
    }


    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_aumentar_stock -> guardar()
            R.id.action_cancelar_aumenta_stock -> navegaListaVacinas()
            else -> return false
        }

        return true
    }


}
