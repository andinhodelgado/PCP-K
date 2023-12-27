package br.com.usinasantafe.pcpk.features.presenter.view.initial

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import br.com.usinasantafe.pcpk.R
import br.com.usinasantafe.pcpk.common.base.BaseFragment
import br.com.usinasantafe.pcpk.common.dialog.GenericDialogProgressBar
import br.com.usinasantafe.pcpk.common.extension.onBackPressed
import br.com.usinasantafe.pcpk.common.extension.showGenericAlertDialog
import br.com.usinasantafe.pcpk.common.utils.StatusRecover
import br.com.usinasantafe.pcpk.common.utils.StatusUpdate
import br.com.usinasantafe.pcpk.databinding.FragmentConfigBinding
import br.com.usinasantafe.pcpk.features.domain.entities.variable.Config
import br.com.usinasantafe.pcpk.common.utils.ResultUpdateDatabase
import br.com.usinasantafe.pcpk.features.presenter.viewmodel.initial.ConfigFragmentState
import br.com.usinasantafe.pcpk.features.presenter.viewmodel.initial.ConfigViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfigFragment : BaseFragment<FragmentConfigBinding>(
    R.layout.fragment_config,
    FragmentConfigBinding::bind
) {

    private val viewModel: ConfigViewModel by viewModels()
    private var genericDialogProgressBar: GenericDialogProgressBar? = null
    private var typeUpdate: Boolean = true
    private var fragmentAttachListenerInitial: FragmentAttachListenerInitial? = null
    private lateinit var describeRecover: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeState()
        startEvents()
        setListener()

    }

    private fun observeState(){
        viewModel.uiLiveData.observe(viewLifecycleOwner) {
            state -> handleStateChange(state)
        }
    }

    private fun startEvents() {
        with(binding) {
            buttonAtualBaseDados.isEnabled = false
            textStatusAtualDados.isVisible = false
            progressBarAtualDados.isVisible = false
        }
        viewModel.recoverDataConfig()
    }

    private fun setListener() {
        with(binding) {
            buttonAtualBaseDados.setOnClickListener {
                handleUpdateBD()
            }
            buttonSalvarConfig.setOnClickListener {
                val nroEquip = editTextNroAparelhoConfig.text.toString().trim()
                val senha = editTextSenhaConfig.text.toString().trim()
                if(validate(nroEquip, senha)){
                    typeUpdate = false
                    viewModel.saveDataConfig(nroEquip, senha)
                    return@setOnClickListener
                }
                showGenericAlertDialog(getString(R.string.texto_config_invalida), requireContext())
            }
            buttonCancConfig.setOnClickListener {
                fragmentAttachListenerInitial?.goMenuInicial()
            }
        }
    }

    private fun validate(nroEquip: String, senha: String) : Boolean {
        return (nroEquip.isNotEmpty() && senha.isNotEmpty())
    }

    private fun handleStateChange(state: ConfigFragmentState){
        when(state){
            is ConfigFragmentState.RecoverConfig -> handleConfig(state.config)
            is ConfigFragmentState.FeedbackLoadingDataBase -> handleLoadingDataBase(state.statusUpdateDataBase)
            is ConfigFragmentState.FeedbackLoadingToken -> handleLoadingToken(state.statusToken)
            is ConfigFragmentState.IsCheckUpdate -> binding.buttonAtualBaseDados.isEnabled = state.isCheckUpdate
            is ConfigFragmentState.SetResultUpdate -> handleStatus(state.resultUpdateDatabase)
        }
    }

    private fun handleConfig(config: Config){
        with(binding) {
            editTextNroAparelhoConfig.setText(config.nroAparelhoConfig.toString())
            editTextSenhaConfig.setText(config.passwordConfig)
            buttonAtualBaseDados.isEnabled = true
        }
    }

    private fun handleStatus(resultUpdateDatabase: ResultUpdateDatabase?){
        with(binding) {
            resultUpdateDatabase?.let {
                if(typeUpdate){
                    textStatusAtualDados.text = resultUpdateDatabase.describe
                    progressBarAtualDados.progress = resultUpdateDatabase.percentage
                    return
                }
                if(genericDialogProgressBar == null){
                    showProgressBar()
                }
                describeRecover = resultUpdateDatabase.describe
                genericDialogProgressBar!!.setValue(resultUpdateDatabase)
            }
        }
    }

    private fun handleLoadingDataBase(statusUpdate: StatusUpdate){
        when(statusUpdate){
            StatusUpdate.UPDATED -> handleCheckUpdate(true)
            StatusUpdate.FAILURE -> handleCheckUpdate(false)
        }
    }

    private fun handleLoadingToken(statusRecover: StatusRecover){
        when(statusRecover){
            StatusRecover.SUCCESS -> {
                hideProgressBar()
                handleUpdateBD()
            }
            StatusRecover.FAILURE -> {
                hideProgressBar()
                showGenericAlertDialog(getString(R.string.texto_update_failure, describeRecover), requireContext())
            }
            StatusRecover.EMPTY -> {
                hideProgressBar()
                showGenericAlertDialog(getString(R.string.texto_dado_invalido, "TOKEN"), requireContext())
            }
        }
    }

    private fun handleUpdateBD(){
        with(binding) {
            typeUpdate = true
            textStatusAtualDados.isVisible = true
            progressBarAtualDados.isVisible = true
            viewModel.updateDataBaseInitial()
        }
    }

    private fun showProgressBar() {
        genericDialogProgressBar = GenericDialogProgressBar(requireContext())
        genericDialogProgressBar!!.show()
        genericDialogProgressBar!!.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
        )
    }

    private fun hideProgressBar() {
        if(genericDialogProgressBar != null){
            genericDialogProgressBar!!.cancel()
        }
        genericDialogProgressBar = null
    }

    private fun handleCheckUpdate(isCheckUpdate: Boolean){
        with(binding) {
            if(isCheckUpdate){
                showGenericAlertDialog(
                    getString(R.string.texto_update_sucess),
                    requireContext(),
                ) { fragmentAttachListenerInitial?.goMenuInicial() }
                return
            }
            showGenericAlertDialog(getString(R.string.texto_update_failure, textStatusAtualDados.text), requireContext())
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is FragmentAttachListenerInitial){
            fragmentAttachListenerInitial = context
        }
        onBackPressed {
            fragmentAttachListenerInitial?.goMenuInicial()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentAttachListenerInitial = null
    }

}