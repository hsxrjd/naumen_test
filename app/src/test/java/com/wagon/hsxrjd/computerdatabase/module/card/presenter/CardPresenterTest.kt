package com.wagon.hsxrjd.computerdatabase.module.card.presenter

import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.adapter.attribute.Attribute
import com.wagon.hsxrjd.computerdatabase.adapter.attribute.ClickableTextAttribute
import com.wagon.hsxrjd.computerdatabase.adapter.attribute.ImageAttribute
import com.wagon.hsxrjd.computerdatabase.adapter.attribute.TextAttribute
import com.wagon.hsxrjd.computerdatabase.model.net.Card
import com.wagon.hsxrjd.computerdatabase.model.net.Company
import com.wagon.hsxrjd.computerdatabase.module.card.CardFragmentView
import com.wagon.hsxrjd.computerdatabase.module.card.Interactor.CardInteractor
import com.wagon.hsxrjd.computerdatabase.module.card.adapter.ICardRecyclerViewAdapter
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import org.mockito.Mockito.`when` as _when


/**
 * Created by erychkov on 7/24/17.
 */
@RunWith(MockitoJUnitRunner::class)
open class CardPresenterTest {

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUpRxSchedulers() {
            val immediate = object : Scheduler() {
                override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                    // this prevents StackOverflowErrors when scheduling with a delay
                    return super.scheduleDirect(run, 0, unit)
                }

                override fun createWorker(): Worker {
                    return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
                }
            }

            RxJavaPlugins.setInitIoSchedulerHandler { immediate }
            RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
            RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
            RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
        }
    }

    @get:Rule public var mMockitoRule: MockitoRule = MockitoJUnit.rule()

    fun toCompanyAttr(company: Company): TextAttribute = TextAttribute(company.name, R.string.company)
    fun toDescriptionAttr(description: String): ClickableTextAttribute = ClickableTextAttribute(description, R.string.description)
    fun toImageAttribute(url: String): ImageAttribute = ImageAttribute(url)

    lateinit var tetxdt: TextAttribute

    fun transformToAttributeList(card: Card): List<Attribute> {
        val list: MutableList<Attribute> = mutableListOf()
        tetxdt = TextAttribute(card.name)
        list.add(tetxdt)
        card.company?.let(this::toCompanyAttr)?.let(list::add)
        card.description?.let(this::toDescriptionAttr)?.let(list::add)
        card.imageUrl?.let(this::toImageAttribute)?.let(list::add)
        return list
    }

    val mMockInteractor: CardInteractor = Mockito.mock(CardInteractor::class.java)
    val mView: CardFragmentView = Mockito.mock(CardFragmentView::class.java)
    val mAdapter: ICardRecyclerViewAdapter = Mockito.mock(ICardRecyclerViewAdapter::class.java)

    @Test
    fun getCardNormalSimple() {
        val presenter = CardPresenter(mMockInteractor)

        _when(mMockInteractor.getCard(1)).thenReturn(
                Observable
                        .just(Card(1, "Test Card", null, null, null))
                        .map(this::transformToAttributeList))

        presenter.setView(mView)
        presenter.bindAdapter(mAdapter)
        presenter.loadCard(1)
        verify(mMockInteractor).getCard(1)
        verify(mView).showLoading()
        verify(mView).hideLoading()
        verify(mAdapter).addAttributes(tetxdt)
    }

    @Test
    fun getCardEmpty() {
        val presenter = CardPresenter(mMockInteractor)
        _when(mMockInteractor.getCard(1)).thenReturn(Observable.just(listOf()))

        presenter.setView(mView)
        presenter.bindAdapter(mAdapter)
        presenter.loadCard(1)

        verify(mMockInteractor).getCard(1)
        verify(mView).showLoading()
        verify(mView).showMessage(R.string.message_error_loading_card)
        verify(mView).hideLoading()
    }

    @Test
    fun getCardError() {
        val presenter = CardPresenter(mMockInteractor)

        _when(mMockInteractor.getCard(1)).thenReturn(Observable.error(Throwable("mock")))
        presenter.setView(mView)
        presenter.bindAdapter(mAdapter)
        presenter.loadCard(1)
        verify(mMockInteractor).getCard(1)
        verify(mView).showLoading()
        verify(mView).showMessage(R.string.message_error_loading_card)
        verify(mView).hideLoading()
    }
}