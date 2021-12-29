package ie.wit.theyappyappy.views.walklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.wit.theyappyappy.databinding.CardWalkBinding
import ie.wit.theyappyappy.models.WalkModel

interface WalkListener {
    fun onWalkClick(walk: WalkModel)
}

class WalkAdapter constructor(private var walks: List<WalkModel>,
                                   private val listener: WalkListener) :
    RecyclerView.Adapter<WalkAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardWalkBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val walk = walks[holder.adapterPosition]
        holder.bind(walk, listener)
    }

    override fun getItemCount(): Int = walks.size

    class MainHolder(private val binding : CardWalkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(walk: WalkModel, listener: WalkListener) {
            binding.walkTitle.text = walk.title
            binding.description.text = walk.description
            if (walk.image != "") {
                Picasso.get().load(walk.image).resize(200,200).into(binding.imageIcon)
            }

            binding.root.setOnClickListener { listener.onWalkClick(walk) }
        }
    }
}