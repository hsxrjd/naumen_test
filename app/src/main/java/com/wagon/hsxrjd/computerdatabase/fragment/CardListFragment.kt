package com.wagon.hsxrjd.computerdatabase.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.wagon.hsxrjd.computerdatabase.MainApplication
import com.wagon.hsxrjd.computerdatabase.Navigator
import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.adapter.CardRecyclerViewAdapter
import com.wagon.hsxrjd.computerdatabase.adapter.EndlessCardRecyclerViewAdapter
import com.wagon.hsxrjd.computerdatabase.dagger.InteractorModule
import com.wagon.hsxrjd.computerdatabase.dagger.list.ListPresenterModule
import com.wagon.hsxrjd.computerdatabase.model.Card
import com.wagon.hsxrjd.computerdatabase.other.MatItemDecoration
import com.wagon.hsxrjd.computerdatabase.presenter.CardListPresenter
import com.wagon.hsxrjd.computerdatabase.view.CardListFragmentView
import javax.inject.Inject


class CardListFragment : Fragment(), CardListFragmentView {

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
        MainApplication.appComponent.plus(ListPresenterModule()).inject(this)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_card_list, container, false)
        ButterKnife.bind(this, view)
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener)
        setupRecyclerView()
        return view
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(BUNDLE_TAG_LAYOUT_MANAGER_CONFIG, mRecyclerView.layoutManager.onSaveInstanceState())
        outState?.putParcelableArray(BUNDLE_TAG_DATA_LIST, mRvAdapter.getCardList().toTypedArray())
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mListPresenter.setView(this)
        hideLoading()
        savedInstanceState
                ?.let {
                    mRecyclerView.layoutManager.onRestoreInstanceState(it.getParcelable(BUNDLE_TAG_LAYOUT_MANAGER_CONFIG))
                    val dataList = it.getParcelableArray(BUNDLE_TAG_DATA_LIST)
                    dataList?.let {
                        mRvAdapter.setCardList(it.toList() as List<Card>)
                    }
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
        mRecyclerView.addItemDecoration(MatItemDecoration(ContextCompat.getDrawable(activity, R.drawable.divider_dark)))
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

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(resource: Int) {
        Toast.makeText(context, resource, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(resource: Int, vararg varargs: Any) {
        val message = getString(resource, varargs)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showCardList(cardList: List<Card>) = mRvAdapter.setCardList(cardList)

    override fun showNextPage(cardList: List<Card>) = mRvAdapter.addCardsToList(cardList)

    companion object {
        val BUNDLE_TAG_LAYOUT_MANAGER_CONFIG: String = "LAYOUT_MANAGER"
        val BUNDLE_TAG_DATA_LIST: String = "LIST_OF_DATA"

        fun newInstance(): CardListFragment {
            return CardListFragment()
        }
    }


}
