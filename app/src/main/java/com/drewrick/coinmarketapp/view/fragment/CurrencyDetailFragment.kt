package com.drewrick.coinmarketapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.drewrick.coinmarketapp.databinding.FragmentCurrencyDetailBinding
import com.drewrick.coinmarketapp.viewmodel.CurrencyViewModel

class CurrencyDetailFragment : Fragment() {

    private var _binding: FragmentCurrencyDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var currencyViewModel: CurrencyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencyDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currencyViewModel = ViewModelProvider(requireActivity()).get(CurrencyViewModel::class.java)

        currencyViewModel.selectedCurrency.observe(viewLifecycleOwner, { currency ->
            binding.currency = currency
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CurrencyDetailFragment()
    }
}