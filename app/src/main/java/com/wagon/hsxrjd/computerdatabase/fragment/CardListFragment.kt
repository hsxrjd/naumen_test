package com.wagon.hsxrjd.computerdatabase.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
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
import com.wagon.hsxrjd.computerdatabase.presenter.CardListPresenter
import com.wagon.hsxrjd.computerdatabase.view.CardListFragmentView
import com.wagon.hsxrjd.computerdatabase.view.CardRecyclerViewAdapter

class CardListFragment : Fragment(), CardListFragmentView {

    interface CardClickListener {
        fun onCardClicked(card: Card)
    }

    @BindView(R.id.recycler_view_cards) lateinit var mRecyclerView: RecyclerView
    @BindView(R.id.progress_bar) lateinit var mProgressBar: ProgressBar

    lateinit var mRvAdapter: CardRecyclerViewAdapter
    lateinit var mListPresenter: CardListPresenter
    lateinit var mClickListener: CardClickListener

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_main, container, false)
        ButterKnife.bind(this, view)
        mListPresenter = CardListPresenter(CardDataRepository.instance)
        savedInstanceState ?: mListPresenter.start()
        mClickListener = object : CardClickListener {
            override fun onCardClicked(card: Card) {
                showMessage("Not implemented")
            }
        }
        mProgressBar.isIndeterminate = true
        hideLoading()
        setupRecyclerView()
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mListPresenter.setView(this)
    }

    fun setupRecyclerView() {
        mRvAdapter = CardRecyclerViewAdapter()
        mRvAdapter.setOnItemClickListener(object : CardRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(card: Card) {
                mClickListener.onCardClicked(card)
            }
        })
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = mRvAdapter

    }

    override fun showLoading() {
        mProgressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        mProgressBar.visibility = View.GONE
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showCardList(cardList: List<Card>) = mRvAdapter.setCardList(cardList)


    override fun showCard(card: Card) = mClickListener.onCardClicked(card)

    companion object {
        fun newInstance(): CardListFragment {
            return CardListFragment()
        }
    }


}
