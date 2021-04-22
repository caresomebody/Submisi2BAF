package com.caresomebody.test.submisi2fundamental

import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.caresomebody.test.submisi2fundamental.adapter.SectionsPagerAdapter
import com.caresomebody.test.submisi2fundamental.data.FavoriteData
import com.caresomebody.test.submisi2fundamental.database.DBHelper
import com.caresomebody.test.submisi2fundamental.database.GitUserContract
import com.caresomebody.test.submisi2fundamental.database.GitUserContract.UserColumns.Companion.CONTENT_URI
import com.caresomebody.test.submisi2fundamental.database.GitUserHelper
import com.caresomebody.test.submisi2fundamental.databinding.ActivityDetailBinding
import com.caresomebody.test.submisi2fundamental.helper.MappingHelper
import com.caresomebody.test.submisi2fundamental.viewModel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.item_row.*
import org.jetbrains.anko.toast

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private var favoriteData : FavoriteData? = null
    private lateinit var menu : Menu
    private var dbHelper = DBHelper(this)
    private lateinit var db : SQLiteDatabase
    private lateinit var gitUserHelper : GitUserHelper
    private lateinit var uriWithId: Uri
    var git = FavoriteData()
    var statusFav = false
    var position: Int = 0
    private var data: String? = null


    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val EXTRA_FAV = "extra_FAV"
        const val EXTRA_POS= "extra_pos"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data = intent.getStringExtra("git")
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
            git.id = it.id
            checkFav()
            Log.d("ini userid", git.id.toString())
            git.username = it.username
            git.avatar = it.avatar
            git.company = it.company
            git.location = it.location
            showLoading(false)
        })

        gitUserHelper = GitUserHelper.getInstance(applicationContext)
        gitUserHelper.open()

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
//        if (statusFav) {
//            menu.getItem(0).icon = ContextCompat.getDrawable(this,R.drawable.ic_favorite_border_24)
//        }
//        else {
//            menu.getItem(0).icon = ContextCompat.getDrawable(this,R.drawable.ic_favorite_fill_24)
//        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favorite_menu) {
            setFavStatus(statusFav)
//            if (statusFav) {
//                userAdd()
//                menu.getItem(0).icon = ContextCompat.getDrawable(this,R.drawable.ic_favorite_border_24)
//                toast("Remove From Favorite")
//            }
//            else {
//                userAdd()
//                menu.getItem(0).icon = ContextCompat.getDrawable(this,R.drawable.ic_favorite_fill_24)
//                toast("Added to Favorite")
//            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkFav() {
        uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + git.id)
        Log.d("ini uriid", uriWithId.toString())
        Log.d("ini gitid", git.id.toString())
        val cursor = contentResolver.query(uriWithId, null, null, null, null)
        val myFavorites = MappingHelper.mapCursorToArrayList(cursor)
        for (data in myFavorites) {
            if (git.id == data.id) {
                menu.getItem(0).icon = ContextCompat.getDrawable(this,R.drawable.ic_favorite_fill_24)
                statusFav = true
            }
        }
    }

    private fun setFavStatus(statusFav: Boolean){
        if (statusFav) {
            userAdd()
            menu.getItem(0).icon = ContextCompat.getDrawable(this,R.drawable.ic_favorite_border_24)
            toast("Remove From Favorite")
        }
        else {
            userAdd()
            menu.getItem(0).icon = ContextCompat.getDrawable(this,R.drawable.ic_favorite_fill_24)
            toast("Added to Favorite")
        }
    }

    private fun userAdd(){
        if (statusFav){
            statusFav = false
            gitUserHelper.deleteById(git.id.toString())
            Log.d("ini id user =", git.id.toString())
        } else {
            val values = ContentValues()
            values.put(GitUserContract.UserColumns.ID, git.id)
            values.put(GitUserContract.UserColumns.USERNAME, git.username)
            values.put(GitUserContract.UserColumns.AVATAR, git.avatar)
            values.put(GitUserContract.UserColumns.COMPANY, git.company)
            values.put(GitUserContract.UserColumns.LOCATION, git.location)
            Log.d("ini id useradd =", git.id.toString())
            statusFav = true
            contentResolver.insert(CONTENT_URI, values)
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