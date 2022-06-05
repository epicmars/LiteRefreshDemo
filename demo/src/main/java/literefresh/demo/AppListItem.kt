package literefresh.demo

import android.view.View
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import literefresh.demo.databinding.VhAppEntryBinding
import literefresh.demo.databinding.VhAppListBinding
import literefresh.sample.base.ui.BaseViewHolder
import literefresh.sample.base.ui.RecyclerAdapter
import literefresh.sample.base.ui.ViewBinder

class AppEntryItem(
    @DrawableRes var imgRes: Int,
    var name: String,
    val onClickListener: OnEntryClickListener
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AppEntryItem

        if (imgRes != other.imgRes) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = imgRes
        result = 31 * result + name.hashCode()
        return result
    }
}

class AppListItem(var name: String) {
    var entries = mutableSetOf<AppEntryItem>()

    fun add(item: AppEntryItem) {
        entries.add(item)
    }
}

@ViewBinder(R.layout.vh_app_list, dataTypes = [AppListItem::class])
class AppListViewHolder(itemView: View?) : BaseViewHolder<VhAppListBinding>(itemView) {
    override fun <T> onBind(data: T, position: Int) {
        val item = data as AppListItem
        binding?.rvAppList?.layoutManager =
            LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL, false)
        val recyclerAdapter = RecyclerAdapter()
        recyclerAdapter.register(AppEntryViewHolder::class.java)
        binding?.rvAppList?.adapter = recyclerAdapter
        recyclerAdapter.addPayloads(item.entries)
    }
}


@ViewBinder(R.layout.vh_app_entry, dataTypes = [AppEntryItem::class])
class AppEntryViewHolder(itemView: View?) : BaseViewHolder<VhAppEntryBinding>(itemView) {
    override fun <T> onBind(data: T, position: Int) {
        val item = data as AppEntryItem
        binding?.ivApp?.setImageResource(item.imgRes)
        binding?.tvName?.setText(item.name)
        itemView.setOnClickListener {
            item.onClickListener.onEntryClick(item)
        }
    }
}

interface OnEntryClickListener {
    fun onEntryClick(entryItem: AppEntryItem)
}



