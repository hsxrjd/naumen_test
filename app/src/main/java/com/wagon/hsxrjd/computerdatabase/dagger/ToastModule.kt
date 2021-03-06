package com.wagon.hsxrjd.computerdatabase.dagger

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.wagon.hsxrjd.computerdatabase.other.ToastAdapter
import com.wagon.hsxrjd.computerdatabase.other.ToastMessage
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by erychkov on 7/18/17.
 */
@Module
class ToastModule {
    @Provides
    @Singleton
    fun provideToastObservable(context: Context): ToastAdapter {
        return ToastAdapter(context)
    }
}