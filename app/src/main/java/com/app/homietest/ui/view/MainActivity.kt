package homie.app.pritam.ui.view

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import homie.app.pritam.R
import homie.app.pritam.data.model.PixabayHitsData
import homie.app.pritam.databinding.ActivityMainBinding
import homie.app.pritam.ui.adapter.CellClickListener
import homie.app.pritam.ui.adapter.ImagesAdapter
import homie.app.pritam.ui.adapter.TagClickListener
import homie.app.pritam.ui.viewmodel.MainViewModel
import homie.app.pritam.utils.Status
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity :
    AppCompatActivity(),
    CellClickListener,
    TagClickListener {

    private lateinit var databinding: ActivityMainBinding
    private lateinit var adapter: ImagesAdapter
    private var isLoading = false

    val adapterFood = mutableListOf<PixabayHitsData>()

    lateinit var mainViewModel: MainViewModel
    fun View.hideKeyboard() {
        val inputMethodManager =
            context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        databinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupUI()
        setupViewModel()
        setupObservers()

        databinding.imagesViewModel = mainViewModel
        databinding.lifecycleOwner = this

        hideSoftKeyboard()

    }

    private fun hideSoftKeyboard() {
        Handler().postDelayed({
            iv_search.hideKeyboard()
        }, 1000)
    }

    private fun renderList(imagess: List<PixabayHitsData>) {
        adapterFood.clear()
        adapterFood.addAll(imagess)
        adapter.updateList(imagess as ArrayList<PixabayHitsData>)
        adapter.notifyDataSetChanged()
    }

    private fun setupUI() {
        adapter = ImagesAdapter(this, adapterFood as ArrayList<PixabayHitsData>, this, this)

        recyclerView.adapter = adapter
        recyclerView.setItemViewCacheSize(100);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        initScrollListener()

        et_searchword.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                mainViewModel.fetchImages(et_searchword.text.toString(), true)
                hideSoftKeyboard()
                true
            } else {
                false
            }
        }
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
    }

    private fun initScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null &&
                        linearLayoutManager.findLastCompletelyVisibleItemPosition() == recyclerView.adapter!!.itemCount - 5
                    ) {
                        mainViewModel.loadMore(et_searchword.text.toString())
                        isLoading = true
                    }
                }
            }
        })
    }

    private fun setupObservers() {
        mainViewModel.getImages().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    isLoading = false
                    progressBar.visibility = View.GONE
                    it.data?.let { pixabayData -> renderList(pixabayData) }
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    isLoading = false
                    progressBar.visibility = View.GONE
                    onSNACK(content, it.message.toString())
                }
            }
        })

        mainViewModel.getPageStatus().observe(this, Observer {
            adapterFood.clear()
        })

        mainViewModel.getListStatus().observe(this, Observer {
            if (it) {
                ll_noResults.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            } else {
                ll_noResults.visibility = View.GONE
            }
        })
    }

    override fun onCellClickListener(image: PixabayHitsData) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setMessage("Do you want to see the details?")
        alertDialog.setPositiveButton("Yes") { dialog, id ->
            val dialogFragment = ImageDetailsDialog(image)
            dialogFragment.show(supportFragmentManager, "imageDetails")
        }
        alertDialog.setNegativeButton("Cancel") { dialog, id ->
        }
        val alert = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

    private fun onSNACK(view: View, message: String) {
        val snackbar = Snackbar.make(
            view, message,
            Snackbar.LENGTH_INDEFINITE
        ).setAction("retry") {
            mainViewModel.fetchImages(et_searchword.text.toString(), false)
            hideSoftKeyboard()
        }
        val snackbarView = snackbar.view
        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.textSize = 14f
        snackbar.show()
    }

    override fun onTagClickListener(tag: String) {
        et_searchword.setText(tag)
        mainViewModel.fetchImages(tag, true)
        hideSoftKeyboard()
    }

}