package pl.edu.maciejewskipwr.grzegorz.lab3android

import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import kotlin.reflect.KProperty


class DotNumbersChange(val title:Int) : DialogFragment() {
    private val listener: OnDotsMaximumChangeListener by lazy {
        try {
            activity as OnDotsMaximumChangeListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement OnColorSelectionListener")
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = AlertDialog.Builder(activity)
            .setTitle(title)
            .setItems(R.array.dot_numebers, { _, which ->
                when (which) {
                    0 -> listener.onMaximumDotsSelection(6, this)
                    1 -> listener.onMaximumDotsSelection(5, this)
                    2 -> listener.onMaximumDotsSelection(4, this)
                }
            })
            .create()
}


