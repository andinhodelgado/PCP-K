package br.com.usinasantafe.pcpk.features.presenter.view.proprio

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.usinasantafe.pcpk.R
import br.com.usinasantafe.pcpk.features.presenter.viewmodel.proprio.PassagColabListViewModel

class PassagColabListFragment : Fragment() {

    companion object {
        fun newInstance() = PassagColabListFragment()
    }

    private lateinit var viewModel: PassagColabListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_passag_colab_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PassagColabListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}