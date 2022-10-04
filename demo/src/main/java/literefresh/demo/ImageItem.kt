package literefresh.demo

import android.view.View
import androidx.annotation.DrawableRes
import literefresh.demo.databinding.ViewholderImageItemBinding
import literefresh.sample.base.ui.BaseViewHolder
import literefresh.sample.base.ui.ViewBinder

class ImageItem(@DrawableRes var imgRes: Int) {
    var name: String? = null
}

@ViewBinder(R.layout.viewholder_image_item, dataTypes = [ImageItem::class])
class ImageViewHolder(itemView: View?) : BaseViewHolder<ViewholderImageItemBinding>(itemView) {
    override fun <T> onBind(data: T, position: Int) {
        val item = data as ImageItem
        binding?.ivImg?.setImageResource(item.imgRes)
    }
}