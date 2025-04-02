package com.example.blockcallphone


import android.view.LayoutInflater


import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.blockcallphone.R.layout

class BlockedNumbersAdapter(
    private val blockedNumbers: List<String>,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<BlockedNumbersAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val phoneNumberTextView: TextView = view.findViewById(R.id.txt_phone_number)
        val deleteButton: ImageButton = view.findViewById(R.id.delete_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_number_block, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val number = blockedNumbers[position]
        holder.phoneNumberTextView.text = number
        holder.deleteButton.setOnClickListener {
            onDeleteClick(position)
        }
    }

    override fun getItemCount() = blockedNumbers.size
}