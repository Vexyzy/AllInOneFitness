package com.example.all_in_one_fitness.fitness

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.all_in_one_fitness.R

class RemoveDialogForm : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Точно хотите удалить элемент?")
                .setMessage("Покормите кота!")
                .setPositiveButton("Удалить") {
                        dialog, id ->  dialog.cancel()
                }
                .setNegativeButton(
                    "Отмена"
                ) { _, _ -> }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}