package dev.iaiabot.thetatrial.ui.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.iaiabot.thetatrial.databinding.FragmentCameraBinding
import org.koin.android.ext.android.inject

class CameraFragment : Fragment() {

    private lateinit var binding: FragmentCameraBinding
    private val viewModel: CameraViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewLifecycleOwner.lifecycle.addObserver(viewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
