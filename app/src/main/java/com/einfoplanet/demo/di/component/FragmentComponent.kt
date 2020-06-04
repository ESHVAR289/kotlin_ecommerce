package com.einfoplanet.demo.di.component

import com.einfoplanet.demo.di.module.BookDataModule
import com.einfoplanet.demo.di.module.BookDomainModule
import com.einfoplanet.demo.di.scope.FragmentScope
import com.einfoplanet.demo.ui.home.AddBookDialogFragment
import com.einfoplanet.demo.ui.home.BooksFragment
import com.einfoplanet.demo.ui.home.DatePickerFragment
import dagger.Subcomponent

@Subcomponent(modules = [BookDataModule::class, BookDomainModule::class])
@FragmentScope
interface FragmentComponent {
    fun inject(booksFragment: BooksFragment)
    fun inject(addBookDialogFragment: AddBookDialogFragment)
    fun inject(datePickerFragment: DatePickerFragment)
}