package com.example.aston_intensiv_final_project.presentation.ui.sources.onesource

import com.example.aston_intensiv_final_project.presentation.model.news.NewsResponseModel
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface OneSourceView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun startLoading()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun endLoading()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSuccess(response: NewsResponseModel)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showError(message: String)
}