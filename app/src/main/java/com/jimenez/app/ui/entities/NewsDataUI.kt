package com.jimenez.app.ui.entities

import com.jimenez.app.data.network.entities.topNews.Data

data class NewsDataUI(
    val id: String,
    val url: String,
    val name: String,
    val image: String,
    val description: String,
    val languaje :String
)


fun Data.toNewsDataUI(): NewsDataUI {
    return NewsDataUI(
        this.uuid,
        this.url,
        this.title,
        this.image_url,
        this.description,
        this.language
    )
}