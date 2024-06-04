package com.jimenez.app.logic.usercases

import com.jimenez.app.data.network.endpoints.NewsEndpoint
import com.jimenez.app.data.network.endpoints.UUIDNews
import com.jimenez.app.data.network.entities.oneNews.OneNewsDataClass
import com.jimenez.app.data.network.repository.RetrofitBase
import com.jimenez.app.ui.entities.NewsDataUI
import com.jimenez.app.ui.entities.toNewsDataUI

class GetOneNewsUserCase {

    suspend operator fun invoke(uuid:String): Result<OneNewsDataClass?> {

        var items = mutableListOf<NewsDataUI>()

        var response = RetrofitBase.returnBaseRetrofitNews()
            .create(UUIDNews::class.java)
            .getUUIDNews(uuid)
        return if (response.isSuccessful) {
           val x = response.body()
            Result.success(x)
        } else {
            Result.failure(Exception("Ocurrio un error en la API"))
        }
    }
}