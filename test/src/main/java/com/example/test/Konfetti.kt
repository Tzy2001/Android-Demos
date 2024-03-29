package com.example.test

import Presets
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import nl.dionsegijn.konfetti.xml.KonfettiView

class Konfetti : AppCompatActivity() {
    private lateinit var viewKonfetti: KonfettiView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_konfetti)

        viewKonfetti = findViewById(R.id.konfettiView)
        findViewById<Button>(R.id.btnFestive).setOnClickListener { festive() }
        findViewById<Button>(R.id.btnExplode).setOnClickListener { explode() }
        findViewById<Button>(R.id.btnParade).setOnClickListener { parade() }
        findViewById<Button>(R.id.btnRain).setOnClickListener { rain() }
    }

    private fun festive() {
        /**
         * See [Presets] for this configuration
         */
        viewKonfetti.start(Presets.festive())
    }

    private fun explode() {
        /**
         * See [Presets] for this configuration
         */
        viewKonfetti.start(Presets.explode())
    }

    private fun parade() {
        /**
         * See [Presets] for this configuration
         */
        viewKonfetti.start(Presets.parade())
    }

    private fun rain() {
        /**
         * See [Presets] for this configuration
         */
        viewKonfetti.start(Presets.rain())
    }
}