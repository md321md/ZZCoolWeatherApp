package com.fire.firecontrol.page.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.zz.mycoolweatherapp.R


class RecycleViewListAdapter<Entity, Binding : ViewDataBinding>(val callback: BindingCallback<Entity, Binding>) : RecyclerView.Adapter<RecycleViewListAdapter.ViewHolder<Binding>>() {

    private var mFruitList: MutableList<Entity>? = mutableListOf()

    fun setDataList(mFruitList: MutableList<Entity>?) {
        this.mFruitList = mFruitList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<Binding> {
        val view: View = LayoutInflater.from(parent.context).inflate(callback.itemLayoutId(),parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder<Binding>, position: Int) {
        val item: Entity = mFruitList!![position]
        callback.bindItem(holder,item,position)
    }

    override fun getItemCount(): Int {
        return mFruitList!!.size
    }


    class ViewHolder<T : ViewDataBinding>(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding: T

        fun getBinding(): T = binding

        init {
            binding = DataBindingUtil.bind(itemView)!!
        }

    }

    interface BindingCallback<E, T : ViewDataBinding> {
        fun bindItem(holder: ViewHolder<T>?, entity: E,position: Int)
        fun itemLayoutId() : Int
    }

}