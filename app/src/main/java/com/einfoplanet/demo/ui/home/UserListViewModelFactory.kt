/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.einfoplanet.demo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.einfoplanet.demo.AppExecutors
import com.einfoplanet.demo.db.BookDao
import com.einfoplanet.demo.domain.booklist.UserListUseCase
import timber.log.Timber
import javax.inject.Inject

/**
 * Factory for ViewModels
 */
class UserListViewModelFactory @Inject constructor(private val userListUseCase: UserListUseCase,
                                                   private val appExecutors: AppExecutors,
                                                   private val bookDao: BookDao) : ViewModelProvider.Factory {
    init {
        Timber.d("init")
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserListViewModel(userListUseCase, appExecutors, bookDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}