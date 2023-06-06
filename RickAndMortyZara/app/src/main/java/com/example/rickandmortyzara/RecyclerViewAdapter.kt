package com.example.rickandmortyzara
import com.squareup.picasso.Picasso
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(private val characterList: List<ModelDataRM>) :
    RecyclerView.Adapter<CharacterViewHolder>() {

    private var filteredList: List<ModelDataRM> = characterList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character =  filteredList[position]

        // Item configuration
        holder.txtName.text = character.name
        holder.txtStatus.text = character.status
        holder.txtGender.text = character.gender
        holder.txtSpecies.text = character.species
        holder.txtOrigin.text = character.origin.name

        Picasso.get().load(character.image).into(holder.imgCharacter)
    }

    override fun getItemCount(): Int {
        return characterList.size
    }
    fun filterByName(name: String) {
        filteredList = characterList.filter { it.name.contains(name, ignoreCase = true) }
        notifyDataSetChanged()
    }
}