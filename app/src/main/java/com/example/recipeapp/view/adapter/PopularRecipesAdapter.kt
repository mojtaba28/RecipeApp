package com.example.recipeapp.view.adapter

import academy.nouri.recipeapp.utils.BaseDiffUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ItemPopularBinding
import com.example.recipeapp.models.data_class.recipes.ResponseRecipes
import com.example.recipeapp.utils.Const
import javax.inject.Inject

class PopularRecipesAdapter @Inject constructor(): RecyclerView.Adapter<PopularRecipesAdapter.ViewHolder>() {

    private lateinit var binding: ItemPopularBinding
    private var items = emptyList<ResponseRecipes.Result>()
    inner class ViewHolder: RecyclerView.ViewHolder(binding.root){
        fun bind(item: ResponseRecipes.Result) {
            binding.apply {
                //Text
                nameTxt.text = item.title
                priceTxt.text = "${item.pricePerServing} $"
                //Image
                val imageSplit = item.image!!.split("-")
                val imageSize = imageSplit[1].replace(Const.OLD_IMAGE_SIZE, Const.NEW_IMAGE_SIZE)
                popularImg.load("${imageSplit[0]}-$imageSize") {
                    crossfade(true)
                    crossfade(800)
                    memoryCachePolicy(CachePolicy.ENABLED)
                    error(R.drawable.ic_placeholder)
                }
                //Click
                root.setOnClickListener {
                    onItemClickListener?.let { it(item.id!!) }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularRecipesAdapter.ViewHolder {
        binding = ItemPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: PopularRecipesAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private var onItemClickListener: ((Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    fun setData(data: List<ResponseRecipes.Result>) {
        val adapterDiffUtils = BaseDiffUtils(items, data)
        val diffUtils = DiffUtil.calculateDiff(adapterDiffUtils)
        items = data
        diffUtils.dispatchUpdatesTo(this)
    }
}