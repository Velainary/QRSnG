package com.example.qrsng

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set

class GenerateFragment : Fragment(R.layout.fragment_generate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val input = view.findViewById<EditText>(R.id.inputText)
        val button = view.findViewById<Button>(R.id.generateBtn)
        val imageView = view.findViewById<ImageView>(R.id.qrImage)

        button.setOnClickListener {
            val text = input.text.toString()
            if (text.isNotBlank()) {
                val bitmap = generateQr(text)
                imageView.setImageBitmap(bitmap)
                imageView.visibility = View.VISIBLE
            }
        }
    }

    private fun generateQr(text: String): Bitmap {
        val size = 600
        val bitMatrix: BitMatrix =
            MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, size, size)

        val bitmap = createBitmap(size, size, Bitmap.Config.RGB_565)
        for (x in 0 until size) {
            for (y in 0 until size) {
                bitmap[x, y] = if (bitMatrix[x, y]) Color.BLACK else Color.WHITE
            }
        }
        return bitmap
    }
}
