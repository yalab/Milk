package org.yalab.milk.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.MotionEventCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.yalab.milk.R
import org.yalab.milk.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initLightButtion()
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initLightButtion() {
        binding.LightButton.setOnTouchListener { v, event ->
            val action: Int = event.action
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.textHome.text = getString(R.string.light_button_down)
                    v.performClick()
                    true
                }
                MotionEvent.ACTION_UP -> {
                    binding.textHome.text = getString(R.string.light_button_up)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}