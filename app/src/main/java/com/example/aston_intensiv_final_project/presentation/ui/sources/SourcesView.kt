package com.example.aston_intensiv_final_project.presentation.ui.sources

import com.example.aston_intensiv_final_project.presentation.model.source.SourceResponseModel
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface SourcesView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun startLoading()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun endLoading()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSuccess(response: SourceResponseModel)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showError(message: String)

    @StateStrategyType(SkipStrategy::class)
    fun showNoInternet()

}