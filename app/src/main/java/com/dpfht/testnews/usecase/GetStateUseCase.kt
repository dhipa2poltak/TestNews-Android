package com.dpfht.testnews.usecase

import androidx.lifecycle.LiveData
import com.dpfht.testnews.framework.rest.api.State

interface GetStateUseCase {

  operator fun invoke(): LiveData<State>?
}