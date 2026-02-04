package com.example.qrsng

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.qrsng.data.db.QrDatabase
import com.example.qrsng.data.db.QrHistoryEntity
import com.example.qrsng.databinding.FragmentScanBinding
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class ScanFragment : Fragment(R.layout.fragment_scan) {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!

    private lateinit var cameraExecutor: ExecutorService
    private var isScanning = true

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentScanBinding.bind(view)

        cameraExecutor = Executors.newSingleThreadExecutor()
        startScanLineAnimation()
        startCamera()

        binding.scanLine.post {
            val height = binding.previewView.height.toFloat()

            binding.scanLine.animate()
                .translationY(height)
                .setDuration(1200)
                .setInterpolator(android.view.animation.LinearInterpolator())
                .withEndAction {
                    binding.scanLine.translationY = 0f
                    if (isScanning) binding.scanLine.animate().start()
                }
                .start()
        }

    }

    // 🔁 Animated scan line
    private fun startScanLineAnimation() {
        binding.scanLine.post {
            val height = binding.previewView.height.toFloat()
            binding.scanLine.translationY = 0f
            binding.scanLine.animate()
                .translationY(height)
                .setDuration(1500)
                .setInterpolator(android.view.animation.LinearInterpolator())
                .withEndAction { startScanLineAnimation() }
                .start()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalGetImage::class)
    private fun startCamera() {
        val cameraProviderFuture =
            ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.surfaceProvider = binding.previewView.surfaceProvider
            }

            val analysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            val scanner = BarcodeScanning.getClient()

            analysis.setAnalyzer(cameraExecutor) { imageProxy ->
                val mediaImage = imageProxy.image ?: return@setAnalyzer imageProxy.close()

                val image = InputImage.fromMediaImage(
                    mediaImage,
                    imageProxy.imageInfo.rotationDegrees
                )

                scanner.process(image)
                    .addOnSuccessListener { barcodes ->
                        if (!isScanning) return@addOnSuccessListener

                        val qrText = barcodes.firstOrNull()?.rawValue ?: return@addOnSuccessListener
                        onQrDetected(qrText)
                    }
                    .addOnCompleteListener { imageProxy.close() }
            }

            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                viewLifecycleOwner,
                CameraSelector.DEFAULT_BACK_CAMERA,
                preview,
                analysis
            )
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    // 🎯 WHAT HAPPENS WHEN QR IS FOUND
    @RequiresApi(Build.VERSION_CODES.O)
    private fun onQrDetected(qrText: String) {
        isScanning = false

        // 🔔 Haptic + sound
        val vibrator = ContextCompat.getSystemService(
            requireContext(),
            Vibrator::class.java
        )
        vibrator?.vibrate(
            VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE)
        )

        // 🪄 Show result card
        binding.resultText.text = qrText
        binding.resultCard.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate().alpha(1f).setDuration(300).start()
        }

        // 📋 Copy button
        binding.btnCopy.setOnClickListener {
            val clipboard = ContextCompat.getSystemService(
                requireContext(),
                ClipboardManager::class.java
            )
            clipboard?.setPrimaryClip(
                ClipData.newPlainText("QR", qrText)
            )
        }

        // 🌐 Open link button
        binding.btnOpen.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, qrText.toUri())
            startActivity(intent)
        }

        // 🗂 Save to history
        lifecycleScope.launch {
            QrDatabase.getInstance(requireContext())
                .historyDao()
                .insert(QrHistoryEntity(content = qrText, type = "SCAN"))
        }

        // 🔁 Auto reset after 3s
        lifecycleScope.launch {
            delay(3000)
            binding.resultCard.visibility = View.GONE
            isScanning = true
        }

        lifecycleScope.launch {
            delay(3000)
            isScanning = true
            binding.resultCard.animate()
                .alpha(0f)
                .setDuration(200)
                .withEndAction {
                    binding.resultCard.visibility = View.GONE
                    binding.resultCard.alpha = 1f
                }
                .start()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding?.scanLine?.animate()?.cancel()
        cameraExecutor.shutdown()
        _binding = null
    }
}
