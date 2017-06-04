package com.wagon.hsxrjd.computerdatabase.fragment

import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.wagon.hsxrjd.computerdatabase.MainActivity
import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.model.Card
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataRepository
import com.wagon.hsxrjd.computerdatabase.other.MatItemDecoration
import com.wagon.hsxrjd.computerdatabase.presenter.CardListPresenter
import com.wagon.hsxrjd.computerdatabase.view.CardListFragmentView
import com.wagon.hsxrjd.computerdatabase.view.CardRecyclerViewAdapter


class CardListFragment : Fragment(), CardListFragmentView {

    @BindView(R.id.recycler_view_cards) lateinit var mRecyclerView: RecyclerView
    @BindView(R.id.progress_bar) lateinit var mProgressBar: ProgressBar


    var mRvAdapter: CardRecyclerViewAdapter = CardRecyclerViewAdapter()
    lateinit var mListPresenter: CardListPresenter


    private var mLayoutManagerState: Parcelable? = null
    private var mDataList: Array<Parcelable>? = null
    private var mIsStart = true
    private var mLoading = false

    private val mRVOnScrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView?.layoutManager as LinearLayoutManager?
            val lastVisibleItemPosition = layoutManager?.findLastVisibleItemPosition()
            if (lastVisibleItemPosition != null) {
                if (lastVisibleItemPosition >= mRvAdapter.itemCount - 4) {
                    if (!mLoading)
                        mListPresenter.loadNextPage()
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_card_list, container, false)
        ButterKnife.bind(this, view)
        mListPresenter = CardListPresenter.instance
        savedInstanceState ?: mListPresenter.setDataSource(CardDataRepository.instance)
        mProgressBar.isIndeterminate = true
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
                    mLayoutManagerState = it.getParcelable(BUNDLE_TAG_LAYOUT_MANAGER_CONFIG)
                    mDataList = it.getParcelableArray(BUNDLE_TAG_DATA_LIST)
                }
                ?: let { if (mIsStart) mListPresenter.start() }
        mIsStart = false

    }

    override fun onResume() {
        super.onResume()
        mDataList?.let { mRvAdapter.setCardList(it.toList() as List<Card>) }
        mLayoutManagerState?.let { mRecyclerView.layoutManager.onRestoreInstanceState(it) }
    }

    fun setupRecyclerView() {
        mRvAdapter.setOnItemClickListener(object : CardRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(view: View, card: Card) {
                mListPresenter.onCardClicked(view, card)
            }
        })
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = mRvAdapter
        mRecyclerView.addItemDecoration(MatItemDecoration(ContextCompat.getDrawable(activity, R.drawable.divider_dark)))
        mRecyclerView.addOnScrollListener(mRVOnScrollListener)

    }

    override fun showLoading() {
        mLoading = true
        if (mRvAdapter.itemCount == 0)
            mProgressBar.visibility = View.VISIBLE
        else
            mRvAdapter.showLoading()
        Log.d("DEBUG", "show loading")
    }

    override fun hideLoading() {
        mLoading = false
//        if (mProgressBar.visibility == View.VISIBLE)
            mProgressBar.visibility = View.GONE
//        else
        mRvAdapter.hideLoading()
        Log.d("DEBUG", "hide loading")
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showCardList(cardList: List<Card>) = mRvAdapter.setCardList(cardList)

    override fun showNextPage(cardList: List<Card>) = mRvAdapter.addCardsToList(cardList)

    override fun switchIndicatorState(flag: Boolean) {
        mRvAdapter.setLoadItemVisibility(flag)
    }

    override fun cardClicked(view: View, card: Card) {
        fragmentManager
                .beginTransaction()
                .addSharedElement(view, ViewCompat.getTransitionName(view))
                .replace(R.id.fragment_container, CardFragment.newInstance(card.id, card.name))
                .addToBackStack(MainActivity.cardFragmentBackStackName)
                .commit()
    }

    companion object {
        val BUNDLE_TAG_LAYOUT_MANAGER_CONFIG: String = "LAYOUT_MANAGER"
        val BUNDLE_TAG_DATA_LIST: String = "LIST_OF_DATA"
        fun newInstance(): CardListFragment {
            return CardListFragment()
        }
    }


}
