package com.wagon.hsxrjd.computerdatabase.fragment

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.transition.AutoTransition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.model.Card
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataRepository
import com.wagon.hsxrjd.computerdatabase.presenter.CardPresenter
import com.wagon.hsxrjd.computerdatabase.view.CardFragmentView

/**
 * Created by hsxrjd on 02.06.17.
 */
class CardFragment : Fragment(), CardFragmentView {
    //todo утечки где-то, не сильные, но есть
    @BindView(R.id.progress_bar_card) lateinit var mProgressBar: ProgressBar
    @BindView(R.id.computer_image) lateinit var mComputerImage: ImageView
    @BindView(R.id.company_name) lateinit var mCompanyName: TextView
    @BindView(R.id.company_text) lateinit var mCompanyNameText: TextView
    @BindView(R.id.description) lateinit var mDescription: TextView
    @BindView(R.id.description_text) lateinit var mDescriptionText: TextView
    @BindView(R.id.similarities_text) lateinit var mSimilaritiesText: TextView
    @BindView(R.id.similarities) lateinit var mSimilarities: ListView

    lateinit var mCardPresenter: CardPresenter
    private var mCardId: Int = -1
    private var mCard: Card? = null

    private val mCardTag: String = "SAVED_CARD"

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


    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        mProgressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        mProgressBar.visibility = View.GONE
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setupTransition() {
        val mTransition = AutoTransition()
//        mTransition.
        sharedElementEnterTransition = mTransition
        enterTransition = mTransition
        sharedElementReturnTransition = mTransition
        exitTransition = mTransition
    }

    override fun showCard(card: Card) {
        mCard = card
        activity?.actionBar?.title = card.name

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
                    Glide
                            .with(this)
                            .load(it)
                            .into(mComputerImage)
                }
                ?: setupImageVisibility(false)
    }

    //TODO привязать, сделать переходы, анимации, подумать над картинкой в оверлее, продумать кнопку
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_card, container, false)
        ButterKnife.bind(this, view)
        mProgressBar.isIndeterminate = true
        mDescription.setOnClickListener {
            l: View ->
            val d = (l as TextView)
            if (d.maxLines == 2)
                d.maxLines = Integer.MAX_VALUE
            else
                d.maxLines = 2
        }
        setupDescriptionVisibility(false)
        setupCompanyNameVisibility(false)
        setupImageVisibility(false)
        mCardPresenter = CardPresenter.instance
        savedInstanceState ?: mCardPresenter.setDataSource(CardDataRepository.instance)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mCardPresenter.setView(this)
        savedInstanceState
                ?.let {
                    mCard = it.getParcelable(mCardTag)
                }
                ?: mCardPresenter.loadCard(mCardId)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(mCardTag, mCard)
    }

    override fun onResume() {
        super.onResume()
        activity.actionBar?.setDisplayShowHomeEnabled(true)
        activity.actionBar?.setDisplayHomeAsUpEnabled(true)
        mCard?.let { showCard(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCardId = arguments.get(CardFragment.tag_id) as Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupTransition()
        }
    }

    companion object {
        val tag_id: String = "CARD_ID"

        fun newInstance(id: Int): CardFragment {
            val fragment = CardFragment()
            val args = Bundle()
            args.putInt(CardFragment.tag_id, id)
            fragment.arguments = args
            return fragment
        }
    }
}