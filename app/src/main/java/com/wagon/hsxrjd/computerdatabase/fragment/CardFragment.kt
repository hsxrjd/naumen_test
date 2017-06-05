package com.wagon.hsxrjd.computerdatabase.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.wagon.hsxrjd.computerdatabase.BaseNavigator
import com.wagon.hsxrjd.computerdatabase.Navigator
import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.adapter.CardRecyclerViewAdapter
import com.wagon.hsxrjd.computerdatabase.model.Card
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataRepository
import com.wagon.hsxrjd.computerdatabase.other.MatItemDecoration
import com.wagon.hsxrjd.computerdatabase.presenter.CardPresenter
import com.wagon.hsxrjd.computerdatabase.view.CardFragmentView
import java.lang.ref.WeakReference

/**
 * Created by hsxrjd on 02.06.17.
 */
class CardFragment : Fragment(), CardFragmentView {
    @BindView(R.id.progress_bar_card) lateinit var mProgressBar: ProgressBar
    @BindView(R.id.computer_image) lateinit var mComputerImage: ImageView
    @BindView(R.id.company_name) lateinit var mCompanyName: TextView
    @BindView(R.id.company_text) lateinit var mCompanyNameText: TextView
    @BindView(R.id.description) lateinit var mDescription: TextView
    @BindView(R.id.description_text) lateinit var mDescriptionText: TextView
    @BindView(R.id.similar_text) lateinit var mSimilaritiesText: TextView
    @BindView(R.id.similar) lateinit var mSimilarities: RecyclerView

    private lateinit var mCardPresenter: CardPresenter
    private var mRvAdapter: CardRecyclerViewAdapter = CardRecyclerViewAdapter()
    private var mCardId: Int = -1
    private var mCardName: String = ""
    private var mCard: Card? = null

    private val mNavigator: WeakReference<Navigator> = WeakReference(BaseNavigator.instance)

    private var mPressed: Boolean = false

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

    private val mPicassoCallback = object : Callback {
        override fun onError() {
            setupImageVisibility(false)
            showMessage("Error loading image")
        }

        override fun onSuccess() {
        }
    }


    fun setupDescriptionVisibility(flag: Boolean) {
        val visibility: Int = if (flag) View.VISIBLE else View.GONE
        mDescription.visibility = visibility
        mDescriptionText.visibility = visibility
    }

    fun setupImageVisibility(flag: Boolean) {
        mComputerImage.visibility = if (flag) View.VISIBLE else View.GONE
    }

    fun setupCompanyNameVisibility(flag: Boolean) {
        val visibility: Int = if (flag) View.VISIBLE else View.GONE
        mCompanyName.visibility = visibility
        mCompanyNameText.visibility = visibility
    }

    fun setupSimilaritiesVisibility(flag: Boolean) {
        val visibility: Int = if (flag) View.VISIBLE else View.GONE
        mSimilaritiesText.visibility = visibility
        mSimilarities.visibility = visibility
    }


    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(resource: Int) {
        Toast.makeText(context, resource, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        mProgressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        mProgressBar.visibility = View.GONE
    }

    override fun showCard(card: Card) {
        mCard = card

        card.company
                ?.let {
                    mCompanyName.text = it.name
                    setupCompanyNameVisibility(true)
                }
                ?: setupCompanyNameVisibility(false)

        card.description
                ?.let {
                    mDescription.text = it
                    setupDescriptionVisibility(true)
                    mDescription.isClickable = true
                }
                ?: setupDescriptionVisibility(false)

        card.imageUrl
                ?.let {
                    setupImageVisibility(true)
                    Picasso
                            .with(context)
                            .load(it)
                            .fit()
                            .into(mComputerImage, mPicassoCallback)
                }
                ?: setupImageVisibility(false)
    }

    override fun showSimilarTo(cardList: List<Card>) {
        if (cardList.isNotEmpty()) {
            setupSimilaritiesVisibility(true)
            mRvAdapter.setCardList(cardList)
        } else {
            setupSimilaritiesVisibility(false)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_card, container, false)
        ButterKnife.bind(this, view)
        mProgressBar.isIndeterminate = true
        mDescription.setOnClickListener(mClickListener)
        setupDescriptionVisibility(false)
        setupCompanyNameVisibility(false)
        setupImageVisibility(false)
        setupSimilaritiesVisibility(false)
        mCardPresenter = CardPresenter.instance
        savedInstanceState ?: mCardPresenter.setDataSource(CardDataRepository.instance)
        setupRecyclerView()
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mCardPresenter.setView(this)
        savedInstanceState
                ?.let {
                    mCard = it.getParcelable(BUNDLE_TAG_CARD)
                    mCardName = it.getString(BUNDLE_TAG_CARD_NAME)
                    mSimilarities.layoutManager.onRestoreInstanceState(it.getParcelable(BUNDLE_TAG_LAYOUT_MANAGER_CONFIG))
                    it.getParcelableArray(BUNDLE_TAG_DATA_LIST)?.let { showSimilarTo(it.toList() as List<Card>) }
                }
                ?: let {
            mCardPresenter.loadCard(mCardId)
            mCardPresenter.showSimilarTo(mCardId)
        }
    }

    fun setupRecyclerView() {
        mRvAdapter.setOnItemClickListener(object : CardRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(view: View, card: Card) {
                mNavigator.get()?.startCardFragment(view, card)
            }
        })
        mSimilarities.layoutManager = LinearLayoutManager(context)
        mSimilarities.adapter = mRvAdapter
        mSimilarities.addItemDecoration(MatItemDecoration(ContextCompat.getDrawable(activity, R.drawable.divider_dark)))
        mSimilarities.isNestedScrollingEnabled = false
        mSimilarities.isScrollContainer = false
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(BUNDLE_TAG_CARD, mCard)
        outState?.putString(BUNDLE_TAG_CARD_NAME, mCardName)
        outState?.putParcelable(CardListFragment.BUNDLE_TAG_LAYOUT_MANAGER_CONFIG, mSimilarities.layoutManager.onSaveInstanceState())
        outState?.putParcelableArray(CardListFragment.BUNDLE_TAG_DATA_LIST, mRvAdapter.getCardList().toTypedArray())
    }

    override fun onResume() {
        mNavigator.get()?.setToolbarTitle(mCardName)
        mNavigator.get()?.enableToolbar(true)
        super.onResume()
        mCard?.let { showCard(it) }
        startPostponedEnterTransition()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCardId = arguments.get(CardFragment.BUNDLE_TAG_CARD_ID) as Int
        mCardName = arguments.get(CardFragment.BUNDLE_TAG_CARD_NAME) as String
        postponeEnterTransition()
    }

    companion object {
        val BUNDLE_TAG_CARD_ID: String = "CARD_ID"
        val BUNDLE_TAG_CARD: String = "SAVED_CARD"
        val BUNDLE_TAG_CARD_NAME: String = "CARD_NAME"
        val BUNDLE_TAG_LAYOUT_MANAGER_CONFIG: String = "LAYOUT_MANAGER"
        val BUNDLE_TAG_DATA_LIST: String = "LIST_OF_DATA"
        val DESCRIPTION_MAX_LINES_COLLAPSED: Int = 2

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