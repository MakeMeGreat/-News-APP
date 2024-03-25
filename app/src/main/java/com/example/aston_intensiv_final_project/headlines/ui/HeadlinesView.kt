package com.example.aston_intensiv_final_project.headlines.ui

import com.example.aston_intensiv_final_project.headlines.data.models.NewsResponse
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface HeadlinesView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun startLoading()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun endLoading()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSuccess(response: NewsResponse)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showError(message: String)

}