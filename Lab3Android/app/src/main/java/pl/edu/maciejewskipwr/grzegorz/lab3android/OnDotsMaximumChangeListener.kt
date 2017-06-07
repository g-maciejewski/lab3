package pl.edu.maciejewskipwr.grzegorz.lab3android

/**
 * Created by PanG on 04.06.2017.
 */
import android.app.DialogFragment

interface OnDotsMaximumChangeListener {
    fun onMaximumDotsSelection(maximum:Int, dialog:DialogFragment)
}