package br.com.fabiokreis.animals.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.fabiokreis.animals.R
import br.com.fabiokreis.animals.model.Animal
import br.com.fabiokreis.animals.util.getProgressDrawable
import br.com.fabiokreis.animals.util.loadImage
import kotlinx.android.synthetic.main.item_layout.view.*

class AnimalListAdapter(private val animalList: ArrayList<Animal>):
    RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>() {

    fun updateAnimalList(newAnimalList: List<Animal>) {
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_layout, parent, false)
        return AnimalViewHolder(view)
    }

    override fun getItemCount(): Int = animalList.size

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.view.animalName.text = animalList[position].name
        holder.view.animalImage.loadImage(animalList[position].imageUrl,
            getProgressDrawable(holder.view.context))
    }

    class AnimalViewHolder(var view: View): RecyclerView.ViewHolder(view)
}