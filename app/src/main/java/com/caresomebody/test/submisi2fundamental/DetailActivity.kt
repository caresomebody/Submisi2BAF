package com.caresomebody.test.submisi2fundamental

import android.app.SearchManager
import android.content.ContentValues
import android.net.Uri
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.caresomebody.test.submisi2fundamental.adapter.SectionsPagerAdapter
import com.caresomebody.test.submisi2fundamental.database.DBHelper
import com.caresomebody.test.submisi2fundamental.database.UserContract
import com.caresomebody.test.submisi2fundamental.database.UserContract.UserColumns.Companion.CONTENT_URI
import com.caresomebody.test.submisi2fundamental.database.UserDbHelper
import com.caresomebody.test.submisi2fundamental.databinding.ActivityDetailBinding
import com.caresomebody.test.submisi2fundamental.viewModel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.jetbrains.anko.toast

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private var favoriteModel : FavoriteModel? = null
    private lateinit var menu : Menu
    private var dbHelper = DBHelper(this)
    private lateinit var db : SQLiteDatabase
    private lateinit var userHelper : UserDbHelper
    private lateinit var uriWithId: Uri
    var statusFav = false
    var position: Int = 0


    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val EXTRA_FAV = "extra_FAV"
        const val EXTRA_POS= "extra_pos"
        const val REQUEST_ADD = 100
        const val RESULT_ADD = 101
        const val REQUEST_UPDATE = 200
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = dbHelper.writableDatabase
        userHelper = UserDbHelper.getInstance(applicationContext)
        userHelper.open()
        favoriteModel = intent.getParcelableExtra(EXTRA_FAV)
        if (favoriteModel != null) {
            position = intent.getIntExtra(EXTRA_POS, 0)
        } else {
            favoriteModel = FavoriteModel()
        }

        val data = intent.getStringExtra("git")
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        detailViewModel.setUserDetail(data)
        detailViewModel.getUserDetail().observe(this, {
            binding.apply {
                Glide.with(this@DetailActivity)
                        .load(it.avatar)
                        .into(binding.avatarGit)
                gitUserName.text = it.username
                gitName.text = it.name
                gitRepo.text = it.repository.toString()
                companyUser.text = it.company
                locUser.text = it.location
            }
            showLoading(false)
        })


        val sectionsPagerAdapter = SectionsPagerAdapter(this,data)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager){
            tab, position -> tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
        supportActionBar?.title = data

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.favorite, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favorite_menu) {
//            toast("$statusFav anjing")
            if (statusFav) {
                statusFav = false
                userAdd()
                menu.getItem(0).icon = ContextCompat.getDrawable(this,R.drawable.ic_favorite_fill_24)
            }
            else {
                statusFav = true
                userRemove()
                menu.getItem(0).icon = ContextCompat.getDrawable(this,R.drawable.ic_favorite_border_24)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun userAdd(){
        val values = ContentValues()
        values.put(UserContract.UserColumns.COLUMN_NAME_USERNAME, binding.gitUserName.text.toString() )
        values.put(UserContract.UserColumns.COLUMN_NAME_AVATAR, binding.avatarGit.toString())
        values.put(UserContract.UserColumns.COLUMN_NAME_COMPANY, binding.companyUser.text.toString())
        values.put(UserContract.UserColumns.COLUMN_NAME_LOCATION, binding.locUser.text.toString())
//        contentResolver.insert(CONTENT_URI, values)
        val result = userHelper.insert(values)
        if (result>0){
            favoriteModel?.id = result.toInt()
            setResult(RESULT_ADD, intent)
        } else {
            Toast.makeText(this@DetailActivity, "Gagal menambah data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun userRemove(){
        //Log.d("ini id ${favoriteModel?.id}", favoriteModel?.id.toString())
        val result = userHelper.deleteById(favoriteModel?.id.toString())
        //Log.d("ini result $result", result.toString())
        if (result >= 0) {
            val intent = Intent()
            intent.putExtra(EXTRA_POS, position)
            setResult(RESULT_DELETE, intent)
        } else {
            Toast.makeText(this, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}