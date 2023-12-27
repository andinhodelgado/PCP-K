package br.com.usinasantafe.pcpk.features.presenter.view.initial

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import br.com.usinasantafe.pcpk.R
import br.com.usinasantafe.pcpk.common.adapter.CustomAdapter
import br.com.usinasantafe.pcpk.common.base.BaseFragment
import br.com.usinasantafe.pcpk.databinding.FragmentMenuApontListBinding
import br.com.usinasantafe.pcpk.features.presenter.viewmodel.initial.MenuApontListFragmentState
import br.com.usinasantafe.pcpk.features.presenter.viewmodel.initial.MenuApontListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuApontListFragment : BaseFragment<FragmentMenuApontListBinding>(
    R.layout.fragment_menu_apont_list,
    FragmentMenuApontListBinding::bind,
) {

    private val viewModel: MenuApontListViewModel by viewModels()
    private var fragmentAttachListenerInitial: FragmentAttachListenerInitial? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeState()
        viewList()
        setListener()

    }

    private fun observeState(){
        viewModel.uiLiveData.observe(viewLifecycleOwner) {
            state -> handleStateChange(state)
        }
    }

    private fun viewList() {

        val opcaoMenu = listOf(
            "MOV. VEÍCULO PRÓPRIO",
            "MOV. VEÍCULO VISITANTE/TERCEIRO",
            "MOV. VEÍCULO RESIDÊNCIA",
        )

        val listAdapter = CustomAdapter(opcaoMenu).apply {
            onItemClick = { text, _ ->
                when(text){
                    "MOV. VEÍCULO PRÓPRIO" -> eventMovEquipProprio()
                    "MOV. VEÍCULO VISITANTE/TERCEIRO" -> eventMovEquipTercVisit()
                    "MOV. VEÍCULO RESIDÊNCIA" -> eventMovEquipResidencia()
                }
            }
        }
        binding.listMenuInicial.run {
            setHasFixedSize(true)
            adapter = listAdapter
        }
    }

    private fun setListener() {
        with(binding) {
            buttonSairMenuApont.setOnClickListener {
//                viewModel.updateDataLocal()
            }
        }
    }

    private fun handleStateChange(state: MenuApontListFragmentState){
        when(state){
            is MenuApontListFragmentState.HasCloseMov -> {}
        }
    }

    private fun eventMovEquipProprio(){
        fragmentAttachListenerInitial?.goMovProprio()
    }

    private fun eventMovEquipTercVisit(){
        fragmentAttachListenerInitial?.goMovVisitTerc()
    }

    private fun eventMovEquipResidencia(){
        fragmentAttachListenerInitial?.goMovResidencia()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentAttachListenerInitial) {
            fragmentAttachListenerInitial = context
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentAttachListenerInitial = null
    }
}