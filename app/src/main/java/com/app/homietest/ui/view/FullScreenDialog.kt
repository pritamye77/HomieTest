package homie.app.pritam.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import homie.app.pritam.R
import homie.app.pritam.data.model.PixabayHitsData
import kotlinx.android.synthetic.main.dialog_image_details.*

class ImageDetailsDialog(var model: PixabayHitsData) :
    DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = false
        return inflater.inflate(R.layout.dialog_image_details, container, false)
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iv_close.setOnClickListener {
            dismiss()
        }

        Glide.with(view).load(model.largeImageURL).apply(
            RequestOptions()
                .placeholder(R.mipmap.ic_andro)
                .signature(ObjectKey(model.largeImageURL))
                .error(R.mipmap.ic_andro)
        ).centerInside().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(iv_image)

        tv_user.text = model.user

        tv_tags.text = model.tags


    }
}
