package com.lovewandou.wd.functions.myAttends

import android.support.v7.widget.LinearLayoutManager
import com.alien.newsdk.network.safeSubscribeBy
import com.lovewandou.wd.R
import com.lovewandou.wd.base.CommonAdapter
import com.lovewandou.wd.base.WDFragment
import com.lovewandou.wd.databinding.FragmentMyAttendsBinding
import com.lovewandou.wd.functions.profile.ProfileVM
import com.lovewandou.wd.functions.profile.UserProfileFragment
import kotlinx.android.synthetic.main.fragment_my_attends.*

/**
 * 描述:
 *
 * Created by and on 2018/11/4.
 */
class MyAttendsFragment : WDFragment<FragmentMyAttendsBinding>() {

    companion object {
        fun newInstance(): MyAttendsFragment = MyAttendsFragment()
    }

    val mAdapter = object : CommonAdapter<ProfileVM>(R.layout.item_my_attend) {
        override fun getClickChildIds(): Array<Int>? {
            return arrayOf(R.id.attend_tv)
        }
    }
    val viewmode = MyAttendsVM()

    override fun getLayoutRes(): Int = R.layout.fragment_my_attends


    override fun initView() {

        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = mAdapter

        swipe_refresh_layout.setOnRefreshListener {
            autoLoad()
        }


        mAdapter.setOnItemClickListener { adapter, view, position ->
            mAdapter.getItem(position)?.let {
                start(UserProfileFragment.newInstance(it))
            }
        }

        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            mAdapter.getItem(position)?.apply {
                cancelAttendUser().safeSubscribeBy {
                    mAdapter.remove(position)
                }
            }

        }

//        RxBus.get().tObservable(UserAttendNotifyEvent::class.java)
//                .safeSubscribeBy { event->
//                    mAdapter.data.find { event.userInfo.user_id == it.userInfo?.user_id }?.notifyChange()
//                }
    }

    override fun autoLoad() {
        super.autoLoad()
        viewmode.getMyAttendsUser()
                .doOnSubscribe {
                    swipe_refresh_layout.isRefreshing = true
                }
                .doAfterTerminate {
                    swipe_refresh_layout.isRefreshing = false
                }
                .safeSubscribeBy { list ->
                    mAdapter.setNewData(list.map {
                        it.addAttend()
                        return@map ProfileVM(it)
                    })
                }
    }

}