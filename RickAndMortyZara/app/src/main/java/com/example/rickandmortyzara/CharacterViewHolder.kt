package com.example.rickandmortyzara

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imgCharacter: ImageView = itemView.findViewById(R.id.imgchar)
    val txtName: TextView = itemView.findViewById(R.id.name_char)
    val txtStatus: TextView = itemView.findViewById(R.id.status_char)
    val txtGender: TextView = itemView.findViewById(R.id.gender_char)
    val txtSpecies: TextView = itemView.findViewById(R.id.species_char)
    val txtOrigin: TextView = itemView.findViewById(R.id.origin_char)
}