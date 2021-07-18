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
class EditarUtenteFragment : Fragment(){



    private lateinit var editTextNome: EditText
    private lateinit var editTextTelefone: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextMorada: EditText
    private lateinit var calendarViewDataNascimento: CalendarView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_editar_utente

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editar_utente, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNome = view.findViewById(R.id.editTextNome)
        editTextTelefone = view.findViewById(R.id.editTextTelefone)
        editTextEmail = view.findViewById(R.id.editTextEmail)
        editTextMorada = view.findViewById(R.id.editTextMorada)
        calendarViewDataNascimento = view.findViewById(R.id.calendarViewDataNascimento)

        editTextNome.setText(DadosApp.utenteSelecionado!!.nome)
        editTextTelefone.setText(DadosApp.utenteSelecionado!!.telefone)
        editTextEmail.setText(DadosApp.utenteSelecionado!!.email)
        editTextMorada.setText(DadosApp.utenteSelecionado!!.morada)
        calendarViewDataNascimento.setDate(DadosApp.utenteSelecionado!!.dataNascimento.time)
    }

    fun navegaListaUtentes() {
        findNavController().navigate(R.id.action_editarUtenteFragment_to_ListaUtentesFragment)
    }

    fun guardar() {
        val nome = editTextNome.text.toString()
        if (nome.isEmpty()) {
            editTextNome.setError(getString(R.string.preencha_nome))
            editTextNome.requestFocus()
            return
        }

        val telefone = editTextTelefone.text.toString()
        if (telefone.isEmpty()) {
            editTextTelefone.setError(getString(R.string.preencha_telefone))
            editTextTelefone.requestFocus()
            return
        }

        val email = editTextEmail.text.toString()
        if (email.isEmpty()) {
            editTextEmail.setError(getString(R.string.preencha_email))
            editTextEmail.requestFocus()
            return
        }

        val morada = editTextMorada.text.toString()
        if (morada.isEmpty()) {
            editTextMorada.setError(getString(R.string.preencha_morada))
            editTextMorada.requestFocus()
            return
        }

        val dataNascimento = calendarViewDataNascimento.date


        val utente = DadosApp.utenteSelecionado!!
        utente.nome = nome
        utente.telefone = telefone
        utente.email = email
        utente.morada = morada
        utente.dataNascimento = Date(dataNascimento)

        val uriUtente = Uri.withAppendedPath(
            ContentProviderArmazemVacinas.ENDERECO_UTENTES,
            utente.id.toString()
        )

        val registos = activity?.contentResolver?.update(
            uriUtente,
            utente.toContentValues(),
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                R.string.erro_editar_utente,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.utente_guardado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaUtentes()
    }


    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_edita_utente -> guardar()
            R.id.action_cancelar_edita_utente -> navegaListaUtentes()
            else -> return false
        }

        return true
    }


}
