package com.jimenez.app.ui.core

import android.view.View
import com.jimenez.app.data.network.entities.topNews.Data
import com.jimenez.app.ui.entities.NewsDataUI


class FunctionExtensions

fun Data.toNewsDataUI(): NewsDataUI =
    NewsDataUI(
        this.uuid,
        this.url,
        this.title,
        this.image_url,
        this.description,
        this.language
    )
