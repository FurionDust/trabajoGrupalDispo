package com.jimenez.app.logic.usercases

import com.jimenez.app.data.network.endpoints.NewsEndpoint
import com.jimenez.app.data.network.entities.topNews.Data
import com.jimenez.app.data.network.repository.RetrofitBase
import com.jimenez.app.ui.entities.NewsDataUI

import com.jimenez.app.ui.entities.toNewsDataUI

class GetAllTopsNewUserCase {


    suspend operator fun invoke(): Result<List<NewsDataUI>> {

        var items = mutableListOf<NewsDataUI>()

        var response = RetrofitBase.returnBaseRetrofitNews()
            .create(NewsEndpoint::class.java)
            .getAllTopNews()
        return if (response.isSuccessful) {
            response.body()?.data?.forEach {
                items.add(it.toNewsDataUI())
            }
            Result.success(items.toList())
        } else {
            Result.failure(Exception("Ocurrio un error en la API"))
        }
    }
}