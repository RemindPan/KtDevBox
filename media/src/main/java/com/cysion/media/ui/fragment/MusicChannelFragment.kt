package com.cysion.media.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.cysion.ktbox.base.BaseFragment
import com.cysion.ktbox.base.ITEM_CLICK
import com.cysion.media.R
import com.cysion.media.adapter.MusicChnAdapter
import com.cysion.media.constant.BUNDLE_KEY
import com.cysion.media.constant.CHANNEL_NAME
import com.cysion.media.constant.TITLE
import com.cysion.media.entity.ChannelInfo
import com.cysion.media.extension.tos
import com.cysion.media.presenter.ChnPresenter
import com.cysion.media.ui.activity.ChannelDetailActivity
import com.cysion.media.ui.iview.ChnView
import com.cysion.other.startActivity_ex
import kotlinx.android.synthetic.main.fragment_music_chn.*

class MusicChannelFragment : BaseFragment(), ChnView {

    private val mChannelList = mutableListOf<ChannelInfo>()

    private val adapter by lazy {
        MusicChnAdapter(mChannelList, context as Context)
    }
    private val presenter by lazy {
        ChnPresenter().apply { attach(this@MusicChannelFragment) }
    }

    override fun getLayoutId(): Int = R.layout.fragment_music_chn

    override fun initView() {
        rvChnList.layoutManager = GridLayoutManager(context, 2)
        rvChnList.adapter = adapter
        adapter.setOnTypeClickListener { obj, position, flag ->
            if (flag == ITEM_CLICK) {
                obj as ChannelInfo
                val bundle = Bundle()
                bundle.putString(TITLE, obj.name)
                bundle.putString(CHANNEL_NAME, obj.ch_name)
                context?.startActivity_ex<ChannelDetailActivity>(BUNDLE_KEY, bundle)
            }
        }
    }

    override fun lazyLoad() {
        super.lazyLoad()
        presenter.getChnList()
    }

    override fun setChannels(datalist: MutableList<ChannelInfo>) {
        mChannelList.clear()
        mChannelList.addAll(datalist)
        adapter.notifyDataSetChanged()
    }

    override fun loading() {
    }

    override fun stopLoad() {
    }

    override fun error(code: Int, msg: String) {
        context?.tos("$code,$msg")
    }

    override fun closeMvp() {
        presenter.detach()
    }
}