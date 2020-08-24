package com.disorganizzazione.spesapp.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.disorganizzazione.spesapp.R
import com.disorganizzazione.spesapp.db.SpesAppDB
import com.disorganizzazione.spesapp.db.ingredients.GroceryListEntity
import com.disorganizzazione.spesapp.db.ingredients.IngredientEntity
import com.disorganizzazione.spesapp.db.ingredients.StorageEntity
import kotlinx.android.synthetic.main.ingredient.view.*
import kotlinx.android.synthetic.main.ingredient.view.ingr_name
import kotlin.concurrent.thread


/**
 * Creates the IngredientViewHolders and binds the data.
 */

class IngredientAdapter(
    private val ingredientList: MutableList<IngredientEntity>,
    context: Context?,
    fragment: MainActivityFragment) : RecyclerView.Adapter<IngredientViewHolder>() {

    private val ctx = context
    private var position = 0
    private val fragment = fragment


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        /**
         * Create a new row (no one knows how) and return the corresponding ViewHolder.
         */
        return IngredientViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.ingredient,
                parent,
                false))
    }

    override fun getItemCount(): Int {
        /**
         * Return the number of elements of the list.
         */
        return ingredientList.size
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, i: Int) {
        // get current ingredient
        val ingredient =  ingredientList[i]

        // display name
        holder.view.ingr_name.text = ingredient.name

        // manage checkbox (display and OnClickListener)
        val box = holder.view.check_box
        box.isChecked = ingredient.done
        box.setOnClickListener {
            var db = SpesAppDB.getInstance(ctx!!)
            val ingrName = holder.view.ingr_name.text.toString()
            val done = box.isChecked
            if (ingredient is GroceryListEntity)
                thread { db?.groceryListDAO()?.tick(ingrName, done) }
            else if (ingredient is StorageEntity)
                thread { db?.storageDAO()?.tick(ingrName, done) }
        }
    }

    fun removeIngredient(i: Int) {
        /**
         * Removes an ingredient both from the db and from the visible list.
         * TODO: remove if not used later
         */
        val ingredient = ingredientList[i]

        // remove from list
        ingredientList.removeAt(i)

        // update UI
        notifyDataSetChanged()

        // remove from the right table of the db, according to type
        var db = SpesAppDB.getInstance(ctx!!)
        if (ingredient is GroceryListEntity)
            thread {
                db?.groceryListDAO()?.deleteFromGroceryList(ingredient)
            }
        else if (ingredient is StorageEntity)
            thread {
                db?.storageDAO()?.deleteFromStorage(ingredient)
            }
    }

}

class IngredientViewHolder(val view: View): RecyclerView.ViewHolder(view)