package com.wagon.hsxrjd.computerdatabase.module.card

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.wagon.hsxrjd.computerdatabase.MainApplication
import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.adapter.RecyclerAdapterFactory
import com.wagon.hsxrjd.computerdatabase.adapter.attribute.*
import com.wagon.hsxrjd.computerdatabase.dagger.card.CardInteractorModule
import com.wagon.hsxrjd.computerdatabase.dagger.card.CardPresenterModule
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
    //    private var mRvAdapterList: CardListRecyclerViewAdapter = CardListRecyclerViewAdapter()
    @Inject lateinit var adapterFactory: RecyclerAdapterFactory
    private lateinit var mRvAdapter2: CardRecyclerViewAdapter
    private var mCardId: Int = -1
    private var mCardName: String = ""
    private var mCard: Card? = null
    private var mOperationCount: Int = 0

    @Inject lateinit var mNavigator: Navigator
    private var mPressed: Boolean = false


    private val mOnScrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView?.layoutManager as LinearLayoutManager?
            val lastVisibleItemPosition = layoutManager?.findLastVisibleItemPosition()
            if (lastVisibleItemPosition != null) {
                if (lastVisibleItemPosition >= mRvAdapter2.itemCount - 1) {
                    mCardPresenter.showSimilarTo(mCardId)
                    mRecyclerMain.removeOnScrollListener(this)
                }
            }
        }
    }

    private val mClickListener = View.OnClickListener {
        l: View ->
        val d = (l as TextView)
        if (!mPressed) {
            d.maxLines = Integer.MAX_VALUE
            mPressed = true
        } else {
            d.maxLines = DESCRIPTION_MAX_LINES_COLLAPSED
            mPressed = false
        }
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

    override fun showCard(card: Card) {
        mCard = card
        val list: MutableList<Attribute> = mutableListOf()

        list.add(TextAttribute(card.name, null))

        card.company?.let {
            list.add(TextAttribute(it.name, getString(R.string.company)))
        }
        card.description?.let {
            list.add(ClickableTextAttribute(it, getString(R.string.description), mClickListener))
        }
        card.imageUrl?.let {
            list.add(ImageAttribute(it))
        }
        mRvAdapter2.setData(list)

    }

    override fun showSimilarTo(cardList: List<Card>) {
        if (cardList.isNotEmpty()) {
            val list = mRvAdapter2.getData().toMutableList()
            list.add(TextAttribute(getString(R.string.you_might_be_looking_for), null))
            list.addAll(cardList.map { c ->
                CardAttribute(c, object : CardAttribute.OnItemClickListener {
                    override fun onItemClick(view: View, card: Card) {
                        mNavigator.startCardFragment(view, card)
                    }
                })
            })
            mRvAdapter2.setData(list)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_card, container, false)
        ButterKnife.bind(this, view)
        mSwipeRefresh.setOnRefreshListener(mOnRefreshListener)
        setupRecyclerView()
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mCardPresenter.setView(this)
        savedInstanceState
                ?.let {

                    mCard = Parcels.unwrap(it.getParcelable(BUNDLE_TAG_CARD))
                    mCardName = it.getString(BUNDLE_TAG_CARD_NAME)
                    showSimilarTo(Parcels.unwrap(it.getParcelable(BUNDLE_TAG_DATA_LIST)))
                }
                ?: let {
            mCardPresenter.loadCard(mCardId)
        }
    }

    fun setupRecyclerView() {
//        mRvAdapter2.setOnItemClickListener(object : CardListRecyclerViewAdapter.OnItemClickListener {
//            override fun onItemClick(view: View, card: Card) {
//                mSwipeRefresh.isRefreshing = false
//                mNavigator.startCardFragment(view, card)
//            }
//        })

        mRecyclerMain.layoutManager = LinearLayoutManager(context)
        mRecyclerMain.adapter = mRvAdapter2
        mRecyclerMain.isNestedScrollingEnabled = false
        mRecyclerMain.addOnScrollListener(mOnScrollListener)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(BUNDLE_TAG_CARD, Parcels.wrap(mCard))
        outState?.putString(BUNDLE_TAG_CARD_NAME, mCardName)
//        outState?.putParcelable(CardListFragment.BUNDLE_TAG_DATA_LIST, Parcels.wrap(mRvAdapterList.getData()))
    }

    override fun onResume() {
        super.onResume()
        mNavigator.setToolbarTitle(mCardName)
        mNavigator.enableToolbar(true)
        mCard?.let { showCard(it) }
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
        MainApplication.appComponent.plus(CardPresenterModule(), CardInteractorModule()).inject(this)
        mRvAdapter2 = CardRecyclerViewAdapter(adapterFactory)
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
        val BUNDLE_TAG_DATA_LIST: String = "LIST_OF_DATA"
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