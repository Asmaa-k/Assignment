package com.asmaa.khb.filterapp.ui.filters.fragments

import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.asmaa.khb.filterapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class BaseFragment : Fragment() {
    private lateinit var dialog: AlertDialog

    fun showLoadingDialog() {
        if (!::dialog.isInitialized) {
            val view = layoutInflater.inflate(R.layout.dialog_loader, null)
            dialog = MaterialAlertDialogBuilder(requireContext())
                .setView(view)
                .setCancelable(false)
                .create()
        }

        if (dialog.isShowing) {
            return
        }
        dialog.show()
    }

    fun hideLoadingDialog() {
        if (!::dialog.isInitialized) return
        CoroutineScope(Dispatchers.Main).launch {
            //this delay just to gave illusion of fetching data like normal app behavior
            //and show the dialog extra more
            delay(100L)
            dialog.dismiss()
        }
    }

    fun showErrorDialog(errorMessage: String) {
        val dialog = layoutInflater.inflate(R.layout.layout_alert_dialog, null).apply {
            findViewById<TextView>(R.id.dialogMessage).text = errorMessage
        }
        MaterialAlertDialogBuilder(requireContext())
            .setView(dialog)
            .setPositiveButton(R.string.dialog_action_ok, null)
            .show()
    }
}
