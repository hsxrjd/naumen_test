package com.wagon.hsxrjd.computerdatabase.module.list

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.dagger.container.ContainerComponent
import com.wagon.hsxrjd.computerdatabase.dagger.list.ListPresenterModule
import com.wagon.hsxrjd.computerdatabase.model.net.Card
import com.wagon.hsxrjd.computerdatabase.log.LoggedFragment
import com.wagon.hsxrjd.computerdatabase.module.card.CardFragment
import com.wagon.hsxrjd.computerdatabase.module.card.adapter.CardRecyclerViewAdapter
import com.wagon.hsxrjd.computerdatabase.module.list.adapter.EndlessCardRecyclerViewAdapter
import com.wagon.hsxrjd.computerdatabase.module.list.presenter.CardListPresenter
import com.wagon.hsxrjd.computerdatabase.navigator.Navigator
import com.wagon.hsxrjd.computerdatabase.other.ToastMessage
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.parceler.Parcels
import javax.inject.Inject


class CardListFragment : LoggedFragment(), CardListFragmentView {

    @BindView(R.id.recycler_view_cards) lateinit var mRecyclerView: RecyclerView
    @BindView(R.id.card_list_swipe_refresh_layout) lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    @Inject lateinit var mNavigator: Navigator

    private var mRvAdapter: EndlessCardRecyclerViewAdapter = EndlessCardRecyclerViewAdapter()

    @Inject lateinit var mListPresenter: CardListPresenter

    private var mIsStart: Boolean = true
    private var mLoading: Boolean = false

    private val mOnRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        mIsStart = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_card_list, container, false)
        ButterKnife.bind(this, view)
//        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener)
        mSwipeRefreshLayout.isEnabled = false
        setupRecyclerView()
        return view
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(BUNDLE_TAG_LAYOUT_MANAGER_CONFIG, mRecyclerView.layoutManager.onSaveInstanceState())
        outState?.putParcelable(BUNDLE_TAG_DATA_LIST, Parcels.wrap(mRvAdapter.getCardList()))
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mListPresenter.setView(this)
        hideLoading()
        savedInstanceState
                ?.let {
                    mRecyclerView.layoutManager.onRestoreInstanceState(it.getParcelable(BUNDLE_TAG_LAYOUT_MANAGER_CONFIG))
                    mRvAdapter.setCardList(Parcels.unwrap(it.getParcelable(BUNDLE_TAG_DATA_LIST)))
                }
        mIsStart = false
    }

    override fun switchLoadingAbility(flag: Boolean) {
        mRvAdapter.setLoadItemVisibility(flag)
    }

    override fun onResume() {
        super.onResume()
        mNavigator.resumeCardListFragment()
    }

    fun setupRecyclerView() {
        mRvAdapter.setOnItemClickListener(object : CardRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(view: View, card: Card) {
                mNavigator.startCardFragment(view, card)
            }
        })
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = mRvAdapter
//        mRecyclerView.addItemDecoration(MatItemDecoration(ContextCompat.getDrawable(activity, R.drawable.divider_dark)))
    }

    override fun showLoading() {
        mLoading = true
        if (mRvAdapter.itemCount == 0)
            mSwipeRefreshLayout.isRefreshing = true
        else
            mRvAdapter.showLoading()
    }

    override fun hideLoading() {
        mLoading = false
        mSwipeRefreshLayout.isRefreshing = false
        mRvAdapter.hideLoading()
    }

    @Inject lateinit var observable: PublishSubject<ToastMessage>

    override fun showMessage(message: String) {
        Observable.just(message).subscribe { observable.onNext(ToastMessage(message)) }
    }

    override fun showMessage(resource: Int) {
        Observable.just(resource).subscribe { observable.onNext(ToastMessage(resource)) }
    }

    override fun showCardList(cardList: List<Card>) = mRvAdapter.setCardList(cardList)

    override fun showNextPage(cardList: List<Card>) = mRvAdapter.addCardsToList(cardList)

    override fun getClassName(): String {
        return className
    }

    companion object {
        val BUNDLE_TAG_LAYOUT_MANAGER_CONFIG: String = "LAYOUT_MANAGER"
        val BUNDLE_TAG_DATA_LIST: String = "LIST_OF_DATA"
        val className = "CardListFragment"

        fun newInstance(containerComponent: ContainerComponent): CardListFragment {
            val fragment = CardListFragment()
            containerComponent.plus(ListPresenterModule()).inject(fragment)
            return fragment
        }
    }


}
