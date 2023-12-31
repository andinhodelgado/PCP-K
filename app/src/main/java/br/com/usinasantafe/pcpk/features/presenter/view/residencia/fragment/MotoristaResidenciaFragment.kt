package br.com.usinasantafe.pcpk.features.presenter.view.residencia.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.usinasantafe.pcpk.R
import br.com.usinasantafe.pcpk.features.presenter.viewmodel.residencia.MotoristaResidenciaViewModel

class MotoristaResidenciaFragment : Fragment() {

    companion object {
        fun newInstance() = MotoristaResidenciaFragment()
    }

    private lateinit var viewModel: MotoristaResidenciaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_motorista_residencia, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MotoristaResidenciaViewModel::class.java)
        // TODO: Use the ViewModel
    }

}