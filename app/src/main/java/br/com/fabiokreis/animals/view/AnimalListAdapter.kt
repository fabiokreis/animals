package br.com.fabiokreis.animals.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import br.com.fabiokreis.animals.R
import br.com.fabiokreis.animals.databinding.ItemLayoutBinding
import br.com.fabiokreis.animals.model.Animal

class AnimalListAdapter(private val animalList: ArrayList<Animal>) :
    RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>(), AnimalClickListener {

    fun updateAnimalList(newAnimalList: List<Animal>) {
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemLayoutBinding>(
            inflater,
            R.layout.item_layout,
            parent,
            false
        )
        return AnimalViewHolder(view)
    }

    override fun getItemCount(): Int = animalList.size

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.view.animal = animalList[position]
        holder.view.listener = this
    }

    override fun onClick(c: View) {
        animalList.forEach { animal ->
            if (animal.name == c.tag) {
                val action =
                    ListFragmentDirections.actionListFragmentToDetailFragment(animal)
                Navigation.findNavController(c).navigate(action)
            }
        }
    }

    class AnimalViewHolder(var view: ItemLayoutBinding) : RecyclerView.ViewHolder(view.root)
}