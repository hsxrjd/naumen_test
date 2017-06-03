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
    //TODO зафигачить di, при быстром скролле исчезает нижний элемент и ничего не грузится
    interface CardClickListener {
        fun onCardClicked(card: Card)
    }

    @BindView(R.id.recycler_view_cards) lateinit var mRecyclerView: RecyclerView
    @BindView(R.id.progress_bar) lateinit var mProgressBar: ProgressBar


    var mRvAdapter: CardRecyclerViewAdapter = CardRecyclerViewAdapter()
    lateinit var mListPresenter: CardListPresenter

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
            if (lastVisibleItemPosition != null) {
                if (lastVisibleItemPosition >= mRvAdapter.itemCount - 3) {
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
        val view: View = inflater!!.inflate(R.layout.fragment_main, container, false)
        ButterKnife.bind(this, view)
        mListPresenter = CardListPresenter.instance
        savedInstanceState ?: mListPresenter.setDataSource(CardDataRepository.instance)

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
        Log.d("EBUH", "$savedInstanceState")
        savedInstanceState
                ?.let {
                    mLayoutManagerState = it.getParcelable(tag_lm)
                    mDataList = it.getParcelableArray(tag_ld)
                }
                ?: let {
            if (isStart) mListPresenter.start()
            isStart = false
        }

    }

    override fun onResume() {
        super.onResume()
        activity.actionBar?.setDisplayShowHomeEnabled(false)
        activity.actionBar?.setDisplayHomeAsUpEnabled(false)
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


    override fun cardClicked(view: View, card: Card) {
        fragmentManager
                .beginTransaction()
                .addSharedElement(view, ViewCompat.getTransitionName(view))
                .replace(R.id.fragment_container, CardFragment.newInstance(ViewCompat.getTransitionName(view).toInt()))
                .addToBackStack(MainActivity.cardFragmentBackStackName)
                .commit()
    }

    companion object {
        fun newInstance(): CardListFragment {
            return CardListFragment()
        }
    }


}
