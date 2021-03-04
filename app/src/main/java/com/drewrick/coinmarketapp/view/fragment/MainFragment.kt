package com.drewrick.coinmarketapp.view.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkInfo
import com.drewrick.coinmarketapp.R
import com.drewrick.coinmarketapp.databinding.FragmentMainBinding
import com.drewrick.coinmarketapp.model.models.Currency
import com.drewrick.coinmarketapp.utils.Constants
import com.drewrick.coinmarketapp.view.adapter.listadapter.CurrencyAdapter
import com.drewrick.coinmarketapp.viewmodel.CurrencyViewModel
import java.util.concurrent.TimeUnit

class MainFragment : Fragment(), CurrencyAdapter.OnItemClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    private lateinit var currencyViewModel: CurrencyViewModel

    private val currencyAdapter = CurrencyAdapter(this)
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        currencyViewModel = ViewModelProvider(requireActivity()).get(CurrencyViewModel::class.java)
        sharedPreferences = requireContext().getSharedPreferences(
            getString(R.string.shared_name),
            Context.MODE_PRIVATE
        )

        makeRequestCall(false)
        inflateAdapter()
    }

    private fun makeRequestCall(swipeFlag: Boolean) {
        if (swipeFlag || System.currentTimeMillis() >= sharedPreferences.getLong(
                Constants.LAST_UPDATE,
                0
            ) + TimeUnit.MINUTES.toMillis(5)
        ) {
            currencyViewModel.makeNetworkRequest().observe(viewLifecycleOwner, { workInfo ->
                workInfo?.let {
                    if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                        if (swipeFlag)
                            binding.swRefreshLayout.isRefreshing = false
                        inflateAdapter()

                        with(sharedPreferences.edit()) {
                            putLong(Constants.LAST_UPDATE, System.currentTimeMillis())
                            apply()
                        }
                    }
                }
            })
        }
    }

    private fun inflateAdapter() {
        currencyViewModel.getCurrencyFromDatabase().observe(viewLifecycleOwner, {
            currencyAdapter.submitList(it)
        })
        binding.rvCurrencyList.adapter = currencyAdapter
    }

    private fun initViews() {
        navController = findNavController()
        binding.rvCurrencyList.layoutManager = LinearLayoutManager(requireActivity())
        binding.swRefreshLayout.setOnRefreshListener {
            makeRequestCall(binding.swRefreshLayout.isRefreshing)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MainFragment()
    }

    override fun onItemClicked(currency: Currency?) {
        currency?.let {
            currencyViewModel.selectedCurrency(currency)
            navController.navigate(R.id.action_mainFragment_to_currencyDetailFragment)
        }
    }
}