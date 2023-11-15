package com.example.mypokedex.view

import android.graphics.Typeface
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.ShareCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mypokedex.R
import com.example.mypokedex.databinding.ActivityDetailBinding
import com.example.mypokedex.model.FlavorTextEntry
import com.example.mypokedex.model.PokemonDetail
import com.example.mypokedex.viewModel.PokemonViewModel
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: PokemonViewModel by lazy {
        ViewModelProvider(this)[PokemonViewModel::class.java]
    }
    private val defaultLang = "es"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name: String = intent.getStringExtra("name") ?: return
        fetchPokemonData(name)
        subscribeObservers()
        handleUserActions(name)
    }

    private fun subscribeObservers() {
        viewModel.pokemonDetail.observe(this, Observer { poke ->
            poke?.let {
                fetchDescription(it.id)
                renderPokemonDetails(it)
            }
        })

        viewModel.pokemonDescription.observe(this, Observer { pokemon ->
            pokemon?.let {
                renderDescription(it)
            }
        })

        viewModel.apiError.observe(this, Observer {
            Log.i("Error", it.toString())
        })
    }

    private fun addStatsToTextView(poke: PokemonDetail) {
        val text = SpannableStringBuilder()
        for (stat in poke.stats) {
            val propName = SpannableStringBuilder(stat.stat.name + ": ").apply {
                setSpan(
                    StyleSpan(Typeface.BOLD),
                    0,
                    this.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            text.append(propName).append(stat.baseStat.toString()).append("\n")
        }

        runOnUiThread {
            binding.pokemonDetailTxr.text = text
        }
    }

    private fun addPhysicalMeasurementsToTextView(poke: PokemonDetail) {
        val text = SpannableStringBuilder()
        val height = SpannableStringBuilder("Height: ").apply {
            setSpan(StyleSpan(Typeface.BOLD), 0, this.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        val weight = SpannableStringBuilder("Weight: ").apply {
            setSpan(StyleSpan(Typeface.BOLD), 0, this.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        text.append(height).append(poke.height.toString()).append("\n")
            .append(weight).append(poke.weight.toString()).append("\n")

        runOnUiThread {
            binding.pokemonDetailTxr.text = text
        }
    }

    private fun handleTabsEvents() {
        binding.tabsContainer.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab?.position
                when (position) {
                    0 -> {
                        binding.aboutLabel.text = "Description:"
                        viewModel.pokemonDescription?.let { desc ->
                            renderDescription(desc.value)
                        }
                    }

                    1 -> {
                        binding.aboutLabel.text = "Physical mesaruments:"
                        viewModel.pokemonDetail.value?.let { poke ->
                            addPhysicalMeasurementsToTextView(poke)
                        }
                    }

                    2 -> {
                        binding.aboutLabel.text = "Stats:"
                        viewModel.pokemonDetail.value?.let { poke ->
                            addStatsToTextView(poke)
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                binding.aboutLabel.text = ""
                binding.pokemonDetailTxr.text = ""
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                binding.aboutLabel.text = ""
                binding.pokemonDetailTxr.text = ""
            }

        })
    }

    private fun handleUserActions(name: String) {
        val url = getPokemonUrl(name)
        handleBackButton()
        handleMoreDetailsButton(url)
        handleShareButton(url)
        handleTabsEvents()
        handleFavButton(name)
        verifyPokemonSaved(name)
    }

    private  fun verifyPokemonSaved(name: String){
        viewModel.isSavedPokemon(name)
        viewModel.saved.observe(this){
            if (!it){
                binding.likePokemonBtn.visibility = View.VISIBLE
            }
        }
    }

    private fun handleFavButton(name: String) {
        binding.likePokemonBtn.setOnClickListener {
            viewModel.savePokemonToFireStore(name)
            Toast.makeText(this, "Added to My favorites", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun handleBackButton() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    private fun handleMoreDetailsButton(url: String) {
        binding.modeDetailsTxt.setOnClickListener {
            val customTabsIntent = CustomTabsIntent.Builder().build()
            customTabsIntent.launchUrl(this, Uri.parse(url))
        }
    }

    private fun handleShareButton(url: String) {
        binding.shareBtn.setOnClickListener {
            val shareText = getShareText(url)
            sendShareIntent(shareText)
        }
    }

    private fun fetchPokemonData(name: String) {
        // Some logic may be needed to validate the Pokemon name
        viewModel.fetchPokemonDetail(name)
    }

    private fun fetchDescription(id: Int) {
        // Some logic may be needed to validate the id
        viewModel.fetchDescription(id)
    }

    private fun renderPokemonDetails(poke: PokemonDetail) {
        Picasso.get()
            .load(poke.sprites.frontDefault)
            .error(R.drawable.pokemon)
            .into(binding.pokemonImg)
        binding.pokemonName.text = poke.name
        binding.pokemonNumber.text = this.getString(R.string.n_0001) + " " + poke.order
    }

    private fun renderDescription(pokemon: PokemonDetail?) {
        val spanishEntries = pokemon?.flavorTextEntries?.filter { it.language.name == defaultLang }
        val spanishText = spanishEntries?.firstOrNull()?.flavorText

        binding.pokemonDetailTxr.text = spanishText ?: ""

        spanishEntries?.let { entries: List<FlavorTextEntry> ->
            viewModel.pokemonDescription.value?.spanishFlavorTextEntries =
                entries.map { entry -> entry.flavorText }
        }
    }

    private fun getPokemonUrl(name: String) = "https://www.pokemon.com/el/pokedex/${name}"
    private fun getShareText(url: String) = "Check out this Pokemon: $url"
    private fun sendShareIntent(text: String) {
        val mimeType = "text/plain"
        val shareIntent = ShareCompat.IntentBuilder.from(this)
            .setType(mimeType)
            .setText(text)
            .createChooserIntent()
            .apply {
                if (resolveActivity(packageManager) == null) {
                    showToastMessage("No application found to share")
                    return@apply
                }
            }
        startActivity(shareIntent)
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}