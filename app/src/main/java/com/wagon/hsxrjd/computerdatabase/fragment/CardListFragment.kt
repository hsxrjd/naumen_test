package com.wagon.hsxrjd.computerdatabase.fragment

import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.model.Card
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataRepository
import com.wagon.hsxrjd.computerdatabase.other.MatItemDecoration
import com.wagon.hsxrjd.computerdatabase.presenter.CardListPresenter
import com.wagon.hsxrjd.computerdatabase.view.CardListFragmentView
import com.wagon.hsxrjd.computerdatabase.view.CardRecyclerViewAdapter


class CardListFragment : Fragment(), CardListFragmentView {

    interface CardClickListener {
        fun onCardClicked(card: Card)
    }

    @BindView(R.id.recycler_view_cards) lateinit var mRecyclerView: RecyclerView
    @BindView(R.id.progress_bar) lateinit var mProgressBar: ProgressBar

    var mRvAdapter: CardRecyclerViewAdapter = CardRecyclerViewAdapter()
    lateinit var mListPresenter: CardListPresenter
    lateinit var mClickListener: CardClickListener

    private val tag_lm: String = "LAYOUT_MANAGER"
    private val tag_ld: String = "LIST_OF_DATA"

    private var mLayoutManagerState: Parcelable? = null
    private var mDataList: Array<Parcelable>? = null
    private var isStart = true
    private var mLoading = false

    private val mRVOnScrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView?.layoutManager as LinearLayoutManager?
            val lastVisibleItemPosition = layoutManager?.findLastVisibleItemPosition()
            if (lastVisibleItemPosition == mRvAdapter.itemCount - 2) {
                mListPresenter.loadNextPage()
            }
        }
    }

    override fun isFullFilled(): Boolean {
        val layoutManager = mRecyclerView.layoutManager as LinearLayoutManager?
        if (mLoading && layoutManager?.findLastVisibleItemPosition() == mRvAdapter.itemCount - 1)
            return false
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_main, container, false)
        ButterKnife.bind(this, view)
        mListPresenter = CardListPresenter.instance
        savedInstanceState ?: mListPresenter.setDataSource(CardDataRepository.instance)
        mClickListener = object : CardClickListener {
            override fun onCardClicked(card: Card) {
                mListPresenter.onCardClicked(card)
            }
        }
        mProgressBar.isIndeterminate = true
        hideLoading()
        setupRecyclerView()
        return view
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(tag_lm, mRecyclerView.layoutManager.onSaveInstanceState())
        outState?.putParcelableArray(tag_ld, mRvAdapter.getCardList().toTypedArray())
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mListPresenter.setView(this)
        savedInstanceState
                ?.let {
                    mLayoutManagerState = it.getParcelable(tag_lm)
                    mDataList = it.getParcelableArray(tag_ld)
                }
                ?: let {
            mListPresenter.start()
            isStart = false
        }

    }

    override fun onResume() {
        super.onResume()
        mDataList?.let { mRvAdapter.setCardList(it.toList() as List<Card>) }
        mLayoutManagerState?.let { mRecyclerView.layoutManager.onRestoreInstanceState(it) }
    }

    fun setupRecyclerView() {
        mRvAdapter.setOnItemClickListener(object : CardRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(card: Card) {
                mClickListener.onCardClicked(card)
            }
        })
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = mRvAdapter
        mRecyclerView.addItemDecoration(MatItemDecoration(ContextCompat.getDrawable(activity, R.drawable.dark_divider)))
        mRecyclerView.addOnScrollListener(mRVOnScrollListener)

    }

    override fun showLoading() {
        mLoading = true
        if (isStart)
            mProgressBar.visibility = View.VISIBLE
        else
            mRvAdapter.showLoading()
    }

    override fun hideLoading() {
        mLoading = false
        if (mProgressBar.visibility == View.VISIBLE)
            mProgressBar.visibility = View.GONE
        else
            mRvAdapter.hideLoading()
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showCardList(cardList: List<Card>) = mRvAdapter.addCardsToList(cardList)


    override fun showCard(card: Card) = mClickListener.onCardClicked(card)

    companion object {
        fun newInstance(): CardListFragment {
            return CardListFragment()
        }
    }


}
