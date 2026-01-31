package com.example.qrsng

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import androidx.core.net.toUri

class ScanFragment : Fragment(R.layout.fragment_scan) {

    private lateinit var previewView: PreviewView
    private lateinit var cameraExecutor: ExecutorService

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        previewView = view.findViewById(R.id.previewView)
        cameraExecutor = Executors.newSingleThreadExecutor()

        startCamera(view)
    }
    private fun handleResult(text: String, textView: TextView) {
        textView.text = text

        if (text.startsWith("http://") || text.startsWith("https://")) {
            textView.setOnLongClickListener {
                val clipboard = requireContext()
                    .getSystemService(android.content.Context.CLIPBOARD_SERVICE)
                        as android.content.ClipboardManager

                clipboard.setPrimaryClip(
                    android.content.ClipData.newPlainText("QR Code", text)
                )

                android.widget.Toast.makeText(
                    requireContext(),
                    "Copied to clipboard",
                    android.widget.Toast.LENGTH_SHORT
                ).show()

                true
            }
        }
    }

    private fun startCamera(rootView: View) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.surfaceProvider = previewView.surfaceProvider
            }

            val analysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            val scanner = BarcodeScanning.getClient()

            analysis.setAnalyzer(
                cameraExecutor
            ) @ExperimentalGetImage
            { imageProxy ->

                val mediaImage = imageProxy.image
                if (mediaImage == null) {
                    imageProxy.close()
                    return@setAnalyzer
                }

                Log.d("ScanFragment", "FRAME RECEIVED")

                val image = InputImage.fromMediaImage(
                    mediaImage,
                    imageProxy.imageInfo.rotationDegrees
                )

                scanner.process(image)
                    .addOnSuccessListener { barcodes ->
                        if (barcodes.isNotEmpty()) {
                            val value = barcodes[0].rawValue ?: "Unknown"

                            Log.d("ScanFragment", "QR FOUND: $value")

                            handleResult(
                                value,
                                rootView.findViewById(R.id.resultText)
                            )
                            cameraProvider.unbindAll()
                        }
                    }
                    .addOnCompleteListener {
                        imageProxy.close()
                    }
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

    override fun onDestroyView() {
        super.onDestroyView()
        cameraExecutor.shutdown()
    }
}
