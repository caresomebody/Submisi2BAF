package com.caresomebody.test.submisi2fundamental.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caresomebody.test.submisi2fundamental.data.GitUser
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainViewModel(): ViewModel() {
    val listUser = MutableLiveData<ArrayList<GitUser>>()
    private val toastMessageObserver = MutableLiveData<String>()

    fun setUser(user: String){
        val gitUser = ArrayList<GitUser>()

        val url = "https://api.github.com/search/users?q=$user"
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                Log.d("error list", statusCode.toString())
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("items")
                    for (i in 0 until list.length()) {
                        val listUser = list.getJSONObject(i)
                        val git = GitUser()
                        git.username = listUser.getString("login")
                        git.avatar = listUser.getString("avatar_url")
                        gitUser.add(git)
                    }
                    listUser.postValue(gitUser)
                } catch (e: Exception) {
                    Log.d("Exception Main View", e.message.toString())
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                toastMessageObserver.value = errorMessage
            }
        })
    }
    fun getUser(): LiveData<ArrayList<GitUser>>{
        return listUser
    }

    fun getToastObserver(): MutableLiveData<String> {
        return toastMessageObserver
    }
}