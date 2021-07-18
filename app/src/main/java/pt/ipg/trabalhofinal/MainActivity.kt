package pt.ipg.trabalhofinal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import pt.ipg.trabalhofinal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var menu: Menu

    var menuAtual = R.menu.menu_lista_utentes
        set(value) {
            field = value
            invalidateOptionsMenu()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        DadosApp.activity = this
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(menuAtual, menu)
        this.menu = menu

        if(menuAtual == R.menu.menu_lista_utentes) {
            atualizaMenuListaUtentes(false)
        }else if (menuAtual == R.menu.menu_lista_vacinas){
            atualizaMenuListaVacinas(false)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val opcaoProcessada = when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(this, R.string.versao, Toast.LENGTH_LONG).show()
                true
            }

            else -> when(menuAtual) {
                R.menu.menu_lista_utentes -> (DadosApp.fragment as ListaUtentesFragment).processaOpcaoMenu(item)
                R.menu.menu_novo_utente -> (DadosApp.fragment as NovoUtenteFragment).processaOpcaoMenu(item)
                R.menu.menu_editar_utente -> (DadosApp.fragment as EditarUtenteFragment).processaOpcaoMenu(item)
                R.menu.menu_apagar_utente -> (DadosApp.fragment as ApagarUtenteFragment).processaOpcaoMenu(item)
                R.menu.menu_ver_dados_utente -> (DadosApp.fragment as VerDadosUtenteFragment).processaOpcaoMenu(item)
                R.menu.menu_menu_principal -> (DadosApp.fragment as MenuPrincipalFragment).processaOpcaoMenu(item)
                R.menu.menu_lista_vacinas -> (DadosApp.fragment as ListaVacinasFragment).processaOpcaoMenu(item)
                R.menu.menu_nova_vacina -> (DadosApp.fragment as NovaVacinaFragment).processaOpcaoMenu(item)
                R.menu.menu_editar_vacinas -> (DadosApp.fragment as EditarVacinaFragment).processaOpcaoMenu(item)
                R.menu.menu_aumentar_stock -> (DadosApp.fragment as AumentarStockFragment).processaOpcaoMenu(item)
                R.menu.menu_lista_doses -> (DadosApp.fragment as ListaDosesFragment).processaOpcaoMenu(item)
                R.menu.menu_nova_dose -> (DadosApp.fragment as NovaDoseFragment).processaOpcaoMenu(item)
                else -> false
            }
        }
        return if(opcaoProcessada) true else super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun atualizaMenuListaUtentes(mostraBotoesUtente : Boolean) {
        menu.findItem(R.id.action_editar_utente).setVisible(mostraBotoesUtente)
        menu.findItem(R.id.action_nova_dose).setVisible(mostraBotoesUtente)
        menu.findItem(R.id.action_apagar_utente).setVisible(mostraBotoesUtente)
        menu.findItem(R.id.action_ver_dados_utente).setVisible(mostraBotoesUtente)
    }

    fun atualizaMenuListaVacinas(mostraBotoesVacina : Boolean) {
        menu.findItem(R.id.action_editar_vacina).setVisible(mostraBotoesVacina)
        menu.findItem(R.id.action_aumentar_stock).setVisible(mostraBotoesVacina)

    }
}