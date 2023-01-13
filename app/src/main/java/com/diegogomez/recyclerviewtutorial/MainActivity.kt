package com.diegogomez.recyclerviewtutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diegogomez.recyclerviewtutorial.adapter.SuperHeroAdapter
import com.diegogomez.recyclerviewtutorial.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Para poder modificar el listado.
    private var superHeroMutableList:MutableList<SuperHero> = SuperHeroProvider.superHeroList.toMutableList()
    private lateinit var adapter: SuperHeroAdapter
    private val llmanager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configFilter()
        initRecyclerView()
        configSwipe()
    }

    private fun configSwipe() {

        binding.swipe.setColorSchemeResources(R.color.purple_200)
        binding.swipe.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.purple_700))

        binding.swipe.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                binding.swipe.isRefreshing  = false
            },1000)

        }
    }

    private fun configFilter() {
        binding.etBusqueda.addTextChangedListener { userFilter ->
            val superHeroFilter = superHeroMutableList.filter { hero ->
                hero.superhero.lowercase().contains(userFilter.toString().lowercase())
            }
            adapter.updateSuperHero(superHeroFilter)
        }
    }

    private fun createSuperHero() {
        val superHero = SuperHero(
            "Pantera",
            "Cerro Porteño",
            "Morales",
            "https://media.ultimahora.com/p/38c098cc53fe0fefa4ed318eac326390/adjuntos/169/imagenes/011/066/0011066981/robertmoralesjfif.jfif"
        )

        superHeroMutableList.add(3, superHero)
        adapter.notifyItemInserted(3)
        //m Este funcion lleva a la posicion indicada <3> con un margen de <20>
        llmanager.scrollToPositionWithOffset(3, 20)
    }

    private fun initRecyclerView() {
        adapter = SuperHeroAdapter(
            superHeroList = superHeroMutableList,
            onClickListener = { superHero -> onItemSelected( superHero ) },
            onClickDelete = { onDeletedItem(it) }
        )

        binding.RecyclerSuperHero.layoutManager = llmanager
        binding.RecyclerSuperHero.adapter = adapter
    }

    private fun onDeletedItem(position: Int) {
        superHeroMutableList.removeAt(position)
        // Avisamos al adapter de que ocurrio un cambio.
        adapter.notifyItemRemoved(position)
    }

    private fun onItemSelected(superHero: SuperHero) {
        Toast.makeText(this, superHero.superhero, Toast.LENGTH_SHORT).show()
    }

}