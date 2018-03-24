package com.appham.gimmerecipes.view

import android.animation.ObjectAnimator
import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.RotateDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import com.appham.gimmerecipes.R
import com.appham.gimmerecipes.model.recipes.RecipesList
import com.squareup.picasso.Picasso

/**
 * @author thomas
 */
class RecipesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var recipesList: RecipesList = RecipesList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recipe_item, parent, false)
        return RecipesHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is RecipesHolder) {
            val recipe = recipesList.recipes?.get(position)
            holder.txtName.text = recipe?.title
            holder.txtDesc.text = holder.txtDesc.context.getString(R.string.recipe_id, recipe?.recipeId)
            holder.txtCompany.text = recipe?.publisher

            val context = holder.itemView.context

            if (recipe?.imageUrl != null) {

                // rotating placeholder image
                val placeholder = ContextCompat.getDrawable(context,
                        R.drawable.ic_launcher_rotate) as RotateDrawable?
                val animator = ObjectAnimator.ofInt(placeholder, "level", 0, 10000)
                animator.repeatCount = Animation.INFINITE
                animator.duration = 2000
                animator.interpolator = LinearInterpolator()
                animator.start()

                // screen width and height values for calculating optimal image size
                val screenWidthPx = Resources.getSystem().displayMetrics.widthPixels
                val screenHeightPx = Resources.getSystem().displayMetrics.heightPixels

                Picasso.with(holder.itemView.context).load(recipe.imageUrl)
                        .resize(Math.min(screenWidthPx / 3, screenHeightPx / 2),
                                Math.min(screenWidthPx / 4, screenHeightPx / 3))
                        .onlyScaleDown()
                        .centerInside()
                        .placeholder(placeholder)
                        .error(R.mipmap.ic_launcher_round)
                        .into(holder.imgLogo)
            }

            // on click launch recipe details activity
            holder.itemView.setOnClickListener {
                val intent = Intent(context, RecipeActivity::class.java)
                intent.putExtra(RECIPE, recipe)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return recipesList.recipes?.size ?: 0
    }

    internal inner class RecipesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtDesc: TextView = itemView.findViewById(R.id.txtDesc)
        val txtCompany: TextView = itemView.findViewById(R.id.txtCompany)
        val imgLogo: ImageView = itemView.findViewById(R.id.imgRecipes)

    }

    companion object {

        val RECIPE = "recipe"
    }
}
