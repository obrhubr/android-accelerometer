package com.example.accelerometer

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal

class MainActivity : AppCompatActivity() , SensorEventListener {

    private var sensorManager : SensorManager ?= null
    private var sensor : Sensor ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        setContentView(R.layout.activity_main)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {

            var acc = BigDecimal((event.values[1] - 9.809980f).toDouble())

            tvSensor.text = acc.toPlainString().take(5)

            val pbVal = acc.toInt() + 10
            pbAcc.progress = pbVal
            if(pbVal > 10) {
                pbAcc.setProgressTintList(ColorStateList.valueOf(Color.GREEN))
            } else {
                pbAcc.setProgressTintList(ColorStateList.valueOf(Color.RED))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this, sensor,
            SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }
}