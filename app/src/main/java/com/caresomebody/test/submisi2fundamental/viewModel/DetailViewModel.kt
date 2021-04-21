package com.caresomebody.test.submisi2fundamental.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caresomebody.test.submisi2fundamental.data.GitUser
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class DetailViewModel: ViewModel() {
    val userDetail = MutableLiveData<GitUser>()
    private val listFollowing = MutableLiveData<ArrayList<GitUser>>()
    private val listFollowers = MutableLiveData<ArrayList<GitUser>>()
    val git = GitUser()

    fun setUserDetail (user: String?){
        val url = "https://api.github.com/users/$user"
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    git.id = responseObject.getInt("id")
                    git.username = responseObject.getString("login")
                    git.name = responseObject.getString("name")
                    git.location = responseObject.getString("location")
                    git.company = responseObject.getString("company")
                    git.repository = responseObject.getInt("public_repos")
                    git.avatar = responseObject.getString("avatar_url")
                    userDetail.postValue(git)
                } catch (e: Exception) {
                    Log.d("Exception Set User", e.message.toString())
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun setFollowing(user: String?){
        val gitUser = ArrayList<GitUser>()
        val url = "https://api.github.com/users/$user/following"
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseArray = JSONArray(result)
                    for (i in 0 until responseArray.length()) {
                        val listUser = responseArray.getJSONObject(i)
                        val username = listUser.getString("login")
                        val avatar = listUser.getString("avatar_url")
                        val git = GitUser()
                        git.username = username
                        git.avatar = avatar
                        gitUser.add(git)
                    }
                    listFollowing.postValue(gitUser)
                } catch (e: Exception) {
                    Log.d("Exception Set Following", e.message.toString())
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun setFollowers(user: String?){
        val gitUser = ArrayList<GitUser>()
        val url = "https://api.github.com/users/$user/followers"
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseArray = JSONArray(result)
                    for (i in 0 until responseArray.length()) {
                        val listUser = responseArray.getJSONObject(i)
                        val username = listUser.getString("login")
                        val avatar = listUser.getString("avatar_url")
                        val git = GitUser()
                        git.username = username
                        git.avatar = avatar
                        gitUser.add(git)
                    }
                    listFollowers.postValue(gitUser)
                } catch (e: Exception) {
                    Log.d("Exception Set Followers", e.message.toString())
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun getFollowing() : LiveData<ArrayList<GitUser>>{
        return listFollowing
    }

    fun getFollowers() : LiveData<ArrayList<GitUser>>{
        return listFollowers
    }

    fun getUserDetail() : LiveData<GitUser>{
        return userDetail
    }

}