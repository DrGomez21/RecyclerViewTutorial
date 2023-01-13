package com.diegogomez.recyclerviewtutorial.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diegogomez.recyclerviewtutorial.SuperHero
import com.diegogomez.recyclerviewtutorial.databinding.ItemSuperheroBinding

class SuperHeroViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemSuperheroBinding.bind(view)

    fun render(
        superHeroModel: SuperHero,
        onClickListener: (SuperHero) -> Unit,
        onClickDelete: (Int) -> Unit
    ) {
        binding.textViewSuperHeroName.text = superHeroModel.superhero
        binding.textViewRealName.text = superHeroModel.realName
        binding.textViewPublisher.text = superHeroModel.publisher
        Glide.with(binding.imageViewSuperHero.context).load(superHeroModel.photo)
            .into(binding.imageViewSuperHero)
        itemView.setOnClickListener { onClickListener(superHeroModel) }
        binding.buttonDelete.setOnClickListener { onClickDelete(adapterPosition) }
    }

}