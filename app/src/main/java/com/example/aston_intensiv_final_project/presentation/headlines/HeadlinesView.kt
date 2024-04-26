package com.example.aston_intensiv_final_project.presentation.headlines

import com.example.aston_intensiv_final_project.presentation.model.news.NewsResponseModel
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.Skip

interface HeadlinesView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun startFirstLoading()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun startPaginateLoading()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun endLoading()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSuccess(response: NewsResponseModel)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showError(message: String)

    @StateStrategyType(SkipStrategy::class)
    fun showNoInternet()

}