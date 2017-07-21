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
import com.wagon.hsxrjd.computerdatabase.adapter.RecyclerAdapterFactory
import com.wagon.hsxrjd.computerdatabase.adapter.attribute.CardAttribute
import com.wagon.hsxrjd.computerdatabase.dagger.card.ListModule
import com.wagon.hsxrjd.computerdatabase.dagger.container.ContainerComponent
import com.wagon.hsxrjd.computerdatabase.log.LoggedFragment
import com.wagon.hsxrjd.computerdatabase.model.net.Card
import com.wagon.hsxrjd.computerdatabase.module.list.adapter.CardListRecyclerViewAdapter
import com.wagon.hsxrjd.computerdatabase.module.list.presenter.CardListPresenter
import com.wagon.hsxrjd.computerdatabase.navigator.Navigator
import com.wagon.hsxrjd.computerdatabase.other.ToastAdapter
import com.wagon.hsxrjd.computerdatabase.other.ToastMessage
import org.parceler.Parcels
import javax.inject.Inject


class CardListFragment : LoggedFragment(), CardListFragmentView {

    @BindView(R.id.recycler_view_cards) lateinit var mRecyclerView: RecyclerView
    @BindView(R.id.card_list_swipe_refresh_layout) lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    @Inject lateinit var mNavigator: Navigator

    @Inject lateinit var adapterFactory: RecyclerAdapterFactory
    @Inject lateinit var mRvListAdapter: CardListRecyclerViewAdapter

    @Inject lateinit var mListPresenter: CardListPresenter

    private var mIsStart: Boolean = true
    private var mLoading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_card_list, container, false)
        ButterKnife.bind(this, view)
        mSwipeRefreshLayout.isEnabled = false
        setupRecyclerView()
        return view
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(BUNDLE_TAG_LAYOUT_MANAGER_CONFIG, mRecyclerView.layoutManager.onSaveInstanceState())
        outState?.putParcelable(BUNDLE_TAG_DATA_LIST, Parcels.wrap(mRvListAdapter.getData()))
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mListPresenter.setView(this)
        mListPresenter.bindAdapter(mRvListAdapter)
        hideLoading()
        savedInstanceState
                ?.let {
                    mRecyclerView.layoutManager.onRestoreInstanceState(it.getParcelable(BUNDLE_TAG_LAYOUT_MANAGER_CONFIG))
                    mRvListAdapter.setData(Parcels.unwrap(it.getParcelable(BUNDLE_TAG_DATA_LIST)))
                }
        mIsStart = false
    }

    override fun onDestroy() {
        mListPresenter.unBind()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        mNavigator.resumeCardListFragment()
    }

    fun setupRecyclerView() {
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = mRvListAdapter
    }

    override fun showLoading() {
        mLoading = true
        if (mRvListAdapter.itemCount == 0)
            mSwipeRefreshLayout.isRefreshing = true
    }

    override fun hideLoading() {
        mLoading = false
        mSwipeRefreshLayout.isRefreshing = false
    }

    @Inject lateinit var toastAdapter: ToastAdapter

    override fun showMessage(message: String) {
        toastAdapter.makeToast(ToastMessage(message))
    }

    override fun showMessage(resource: Int) {
        toastAdapter.makeToast(ToastMessage(resource))
    }

    override fun showCardList(cardList: List<Card>) = mRvListAdapter.setData(cardList.map {
        CardAttribute(it)
    })

    override fun startCardFragment(card: Card) {
        mNavigator.startCardFragment(card)
    }

    override fun getClassName(): String {
        return className
    }

    companion object {
        val BUNDLE_TAG_LAYOUT_MANAGER_CONFIG: String = "LAYOUT_MANAGER"
        val BUNDLE_TAG_DATA_LIST: String = "LIST_OF_DATA"
        val className = "CardListFragment"

        fun newInstance(containerComponent: ContainerComponent): CardListFragment {
            val fragment = CardListFragment()
            containerComponent.plus(ListModule()).inject(fragment)
            return fragment
        }
    }


}
