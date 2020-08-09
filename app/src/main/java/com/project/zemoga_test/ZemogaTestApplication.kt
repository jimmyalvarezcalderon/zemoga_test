package com.project.zemoga_test

import android.app.Application
import com.project.zemoga_test.repository.post.IPostMapperService
import com.project.zemoga_test.repository.post.IPostRepository
import com.project.zemoga_test.repository.post.impl.PostMapperService
import com.project.zemoga_test.repository.post.impl.PostRepository
import com.project.zemoga_test.repository.user.IUserMapperService
import com.project.zemoga_test.repository.user.IUserRepository
import com.project.zemoga_test.repository.user.impl.UserMapperService
import com.project.zemoga_test.repository.user.impl.UserRepository
import com.project.zemoga_test.services.database.ZemogaTestDataBase
import com.project.zemoga_test.util.rxschduler.BaseSchedulerProvider
import com.project.zemoga_test.util.rxschduler.SchedulerProvider
import com.project.zemoga_test.view.post.PostListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class ZemogaTestApplication : Application() {

    private val postModule = module {
        single<BaseSchedulerProvider> { SchedulerProvider() }
        single(override = true) { PostListViewModel(get(), get(), get()) }
        single<ZemogaTestDataBase> { ZemogaTestDataBase.getInstance(this@ZemogaTestApplication) }
        single<IPostRepository> { PostRepository(get(), get()) }
        single<IPostMapperService> { PostMapperService() }
        single<IUserRepository> { UserRepository(get()) }
        single<IUserMapperService> { UserMapperService() }
    }


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ZemogaTestApplication)
            modules(postModule)
        }
    }

}