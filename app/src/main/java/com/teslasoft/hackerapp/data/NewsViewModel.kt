package com.teslasoft.hackerapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.Article
import com.kwabenaberko.newsapilib.models.request.EverythingRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse


class NewsViewModel : ViewModel() {

    private val _articles = MutableLiveData<List<Article>>()
    val articles:LiveData<List<Article>> = _articles

   init{
       fetchCybersecurityNews()
   }

    private fun fetchCybersecurityNews() {

       val newsApiClient = NewsApiClient(Constant.apiKey)
       val request = EverythingRequest.Builder().q("cybersecurity").language("en").build()

        newsApiClient.getEverything(request, object : NewsApiClient.ArticlesResponseCallback {
            override fun onSuccess(response: ArticleResponse?) {
                response?.articles?.let{
                    _articles.postValue(it)
                }
            }

            override fun onFailure(throwable: Throwable?) {
                if(throwable!=null){
                    Log.i("NewsApi Response Failed", throwable.localizedMessage)
                }
            }

        })


    }

}