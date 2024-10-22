package com.beauterare.rare

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactsAdapter(private val contacts: List<Contacts>) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contacts[position]
        holder.contactName.text = contact.name

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ConversationActivity::class.java)
            intent.putExtra("contactName", contact.name)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contactName: TextView = itemView.findViewById(R.id.contactName)
    }
}