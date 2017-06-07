package pl.edu.maciejewskipwr.grzegorz.lab3android

import android.app.DialogFragment
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.WindowManager
import android.widget.ImageView
import kotlinx.android.synthetic.main.main_activity.*
import android.support.v7.widget.ShareActionProvider
import kotlinx.android.synthetic.main.main_activity.view.*
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_activity.view.*
import java.util.*

class MainActivity : AppCompatActivity(), SensorEventListener, OnDotsMaximumChangeListener {

    override fun onMaximumDotsSelection(maximum: Int, dialog: DialogFragment) = when (dialog) {
        dotNumberChangeDialog -> {
            range = maximum
            tvMaxDotsNumber.setText(range.toString())
        }
        cubesNumberChangeDialog->{
            cubes=maximum
            handleVisibility(cubes)
        }
        else -> Unit
    }


    private var range: Int = 6
    private var cubes: Int =1
    private var whichCube: ImageView? = null
    private var r = Random()
    override fun onSensorChanged(event: SensorEvent) {

        if (Math.abs(event.values[0]) > 22 || Math.abs(event.values[1]) > 22 || Math.abs(event.values[2]) > 22) {
            mediaPlayer.start()
            var sum=0
            //var randomNumber2 = theRoll(range)
            for(c in 1..cubes){
                var randomNumber = theRoll(range)
                when(c){
                    1->whichCube=ivCube
                    2->whichCube=ivCube2
                    3->whichCube=ivCube3
                    4->whichCube=ivCube4
                }
                when (randomNumber) {
                    1 -> whichCube?.setImageResource(R.drawable.dice1)
                    2 -> whichCube?.setImageResource(R.drawable.dice2)
                    3 -> whichCube?.setImageResource(R.drawable.dice3)
                    4 -> whichCube?.setImageResource(R.drawable.dice4)
                    5 -> whichCube?.setImageResource(R.drawable.dice5)
                    6 -> whichCube?.setImageResource(R.drawable.dice6)
                }
                sum+=randomNumber
            }


            tvSuma.setText(sum.toString())

        }
    }

    private fun handleVisibility(cubes:Int)
    {
        when(cubes)
        {
            1->{ivCube.visibility=VISIBLE
                ivCube2.visibility=INVISIBLE
                ivCube3.visibility=INVISIBLE
                ivCube4.visibility=INVISIBLE }
            2->{ivCube.visibility=VISIBLE
                ivCube2.visibility=VISIBLE
                ivCube3.visibility=INVISIBLE
                ivCube4.visibility=INVISIBLE }
            3->{ivCube.visibility=VISIBLE
                ivCube2.visibility=VISIBLE
                ivCube3.visibility=VISIBLE
                ivCube4.visibility=INVISIBLE }
            4->{ivCube.visibility=VISIBLE
                ivCube2.visibility=VISIBLE
                ivCube3.visibility=VISIBLE
                ivCube4.visibility=VISIBLE }
        }
    }

    private val mediaPlayer: MediaPlayer by lazy {
        MediaPlayer.create(this, R.raw.`roll`)
    }

    private fun theRoll(range: Int): Int {
        return r.nextInt(range) + 1
    }

    private lateinit var shareActionProvider: ShareActionProvider
    private val sendIntent
        get() = with(android.content.Intent(android.content.Intent.ACTION_SEND)) {
            putExtra(android.content.Intent.EXTRA_TEXT, shareMessage)
            type = "text/plain"
            this
        }
    private val shareMessage: String
        get() {
            val sharingMessageTemplate = getString(R.string.shareMessage)
            return String.format(sharingMessageTemplate)
        }
    private fun prepareSharing(menu: Menu) {
        val shareItem = menu.findItem(R.id.share)
        shareActionProvider = MenuItemCompat.getActionProvider(shareItem) as ShareActionProvider
        updateShareIntent()
    }
    private fun updateShareIntent() {
        shareActionProvider.setShareIntent(sendIntent)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    private val dotNumberChangeDialog by lazy {
        DotNumbersChange(R.string.pick_dots_number)
    }
    private val cubesNumberChangeDialog by lazy{
        CubeNumberChange(R.string.pick_cubes_number)
    }

    private val sensorManager: SensorManager by lazy {
        getSystemService(SENSOR_SERVICE) as SensorManager
    }
    private val accelerometer: Sensor by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)
        ivCube.setImageResource(R.drawable.dice6)
        ivCube2.setImageResource(R.drawable.dice1)
        tvMaxDotsNumber.setText(range.toString())
    }


    override fun onCreateOptionsMenu(menu: Menu) = toTrue {
        menuInflater.inflate(R.menu.menu_main, menu)
        prepareSharing(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_dots_number -> toTrue {
            dotNumberChangeDialog.show(fragmentManager, "CubeDialogFragment")
        }
        R.id.action_cubes_number-> toTrue{
            cubesNumberChangeDialog.show(fragmentManager,"CubeNumberDialogFragment")
        }

        else -> super.onOptionsItemSelected(item)
    }
}

