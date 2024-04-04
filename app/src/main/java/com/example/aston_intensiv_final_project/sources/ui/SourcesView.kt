package com.example.aston_intensiv_final_project.sources.ui

import com.example.aston_intensiv_final_project.headlines.data.models.NewsResponse
import com.example.aston_intensiv_final_project.sources.data.models.SourcesResponse
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface SourcesView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun startLoading()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun endLoading()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSuccess(response: SourcesResponse)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showError(message: String)
}