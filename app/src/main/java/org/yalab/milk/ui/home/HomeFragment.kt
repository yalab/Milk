package org.yalab.milk.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.yalab.milk.R
import org.yalab.milk.data.Light
import org.yalab.milk.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    companion object {
        private const val TAG = "Milk"
    }

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var light: Light

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            initLightButtion()
        }else{
            val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) { initLightButtion() }
            }
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        light = Light(requireContext(), binding.viewFinder.surfaceProvider, this)
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
                    light.on()
                    binding.textHome.text = getString(R.string.light_button_down)
                    v.performClick()
                    true
                }
                MotionEvent.ACTION_UP -> {
                    light.off()
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
