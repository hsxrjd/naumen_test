package com.wagon.hsxrjd.computerdatabase.module.card

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.wagon.hsxrjd.computerdatabase.MainApplication
import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.dagger.card.CardModule
import com.wagon.hsxrjd.computerdatabase.log.LoggedFragment
import com.wagon.hsxrjd.computerdatabase.model.net.Card
import com.wagon.hsxrjd.computerdatabase.module.card.adapter.CardRecyclerViewAdapter
import com.wagon.hsxrjd.computerdatabase.module.card.presenter.CardPresenter
import com.wagon.hsxrjd.computerdatabase.navigator.Navigator
import com.wagon.hsxrjd.computerdatabase.other.ToastAdapter
import com.wagon.hsxrjd.computerdatabase.other.ToastMessage
import org.parceler.Parcels
import javax.inject.Inject

/**
 * Created by hsxrjd on 02.06.17.
 */
class CardFragment : LoggedFragment(), CardFragmentView {

    @BindView(R.id.main_list) lateinit var mRecyclerMain: RecyclerView
    @BindView(R.id.card_swipe_refresh_layout) lateinit var mSwipeRefresh: SwipeRefreshLayout

    @Inject lateinit var mCardPresenter: CardPresenter
    @Inject lateinit var mRvAdapter: CardRecyclerViewAdapter
    private var mCardId: Int = -1
    private var mCardName: String = ""
    private var mCard: Card? = null
    private var mOperationCount: Int = 0

    @Inject lateinit var mNavigator: Navigator

    private val mOnScrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView?.layoutManager as LinearLayoutManager?
            val lastVisibleItemPosition = layoutManager?.findLastVisibleItemPosition()
            if (lastVisibleItemPosition != null) {
                if (lastVisibleItemPosition >= mRvAdapter.itemCount - 1) {
                    mCardPresenter.showSimilarTo(mCardId)
                    mRecyclerMain.removeOnScrollListener(this)
                }
            }
        }
    }

    override fun startCardFragment(card: Card) {
        mNavigator.startCardFragment(card)
    }

    private val mOnRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        mCardPresenter.loadCard(mCardId)
        mCardPresenter.showSimilarTo(mCardId)
    }

    @Inject lateinit var toastAdapter: ToastAdapter

    override fun showMessage(message: String) {
        toastAdapter.makeToast(ToastMessage(message))
    }

    override fun showMessage(resource: Int) {
        toastAdapter.makeToast(ToastMessage(resource))
    }

    override fun showLoading() {
        mOperationCount++
        mSwipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        mOperationCount--
        if (mOperationCount == 0) {
            mSwipeRefresh.isRefreshing = false
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_card, container, false)
        ButterKnife.bind(this, view)
        mSwipeRefresh.setOnRefreshListener(mOnRefreshListener)
        setupRecyclerView()
        return view
    }

    override fun onDestroy() {
        mCardPresenter.unBind()
        super.onDestroy()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mCardPresenter.setView(this)
        mCardPresenter.bindAdapter(mRvAdapter)
        savedInstanceState
                ?.let {
                    mCard = Parcels.unwrap(it.getParcelable(BUNDLE_TAG_CARD))
                    mCardName = it.getString(BUNDLE_TAG_CARD_NAME)
                }
                ?: let {
            mCardPresenter.loadCard(mCardId)
        }
    }

    fun setupRecyclerView() {
        mRecyclerMain.layoutManager = LinearLayoutManager(context)
        mRecyclerMain.adapter = mRvAdapter
        mRecyclerMain.addOnScrollListener(mOnScrollListener)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(BUNDLE_TAG_CARD, Parcels.wrap(mCard))
        outState?.putString(BUNDLE_TAG_CARD_NAME, mCardName)
    }

    override fun onResume() {
        super.onResume()
        mNavigator.setToolbarTitle(mCardName)
        mNavigator.enableToolbar(true)
    }

    override fun onPause() {
        super.onPause()
        mSwipeRefresh.isRefreshing = false
        mSwipeRefresh.destroyDrawingCache()
        mSwipeRefresh.clearAnimation()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainApplication.appComponent.plus(CardModule()).inject(this)
        mCardId = arguments.get(CardFragment.BUNDLE_TAG_CARD_ID) as Int
        mCardName = arguments.get(CardFragment.BUNDLE_TAG_CARD_NAME) as String
    }

    override fun getClassName(): String {
        return className
    }

    companion object {
        val BUNDLE_TAG_CARD_ID: String = "CARD_ID"
        val BUNDLE_TAG_CARD: String = "SAVED_CARD"
        val BUNDLE_TAG_CARD_NAME: String = "CARD_NAME"
        val DESCRIPTION_MAX_LINES_COLLAPSED: Int = 2
        val className = "CardFragment"

        fun newInstance(id: Int, name: String): CardFragment {
            val fragment = CardFragment()
            val args = Bundle()
            args.putInt(CardFragment.BUNDLE_TAG_CARD_ID, id)
            args.putString(CardFragment.BUNDLE_TAG_CARD_NAME, name)
            fragment.arguments = args
            return fragment
        }
    }
}