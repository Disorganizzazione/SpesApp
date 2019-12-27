package com.disorganizzazione.spesapp

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.disorganizzazione.spesapp.db.StorageEntity
import com.disorganizzazione.spesapp.db.SpesAppDB
import com.disorganizzazione.spesapp.ui.main.SectionsPagerAdapter
import java.util.*

class MainActivity : AppCompatActivity() {

    var db: SpesAppDB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        db = SpesAppDB.getInstance(this)

        // TEST
        // TODO: cancellare/delete
        Thread {
            var testIngr = StorageEntity()
            testIngr.name = "Carote"
            testIngr.portions = null
            testIngr.useBefore = Date(0)

            db.storageDAO().insertInStorage(testIngr)
        }.start()

    }
}