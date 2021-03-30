package com.zz.mycoolweatherapp.ui.area

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.fire.firecontrol.page.search.RecycleViewListAdapter
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.zz.mycoolweatherapp.R
import com.zz.mycoolweatherapp.databinding.ChooseAreaBinding
import com.zz.mycoolweatherapp.databinding.SimpleItemBinding
import com.zz.mycoolweatherapp.ui.MainActivity
import com.zz.mycoolweatherapp.ui.weather.WeatherActivity
import com.zz.mycoolweatherapp.util.InjectorUtil
import com.zz.mycoolweatherapp.view.Loadingview
import kotlinx.android.synthetic.main.choose_area.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ChooseAreaFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this, InjectorUtil.getChooseAreaModelFactory()).get(ChooseAreaViewModel::class.java)
    }

    lateinit var binding : ChooseAreaBinding

    lateinit var adapter : RecycleViewListAdapter<String, SimpleItemBinding>

    private var  dialog : Loadingview? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.choose_area,container,false)
        binding = DataBindingUtil.bind<ChooseAreaBinding>(view)!!
        binding.viewModel = viewModel

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(activity is WeatherActivity){
            // 距离顶部设置距离
            var statusBar = QMUIStatusBarHelper.getStatusbarHeight(context)
            var params = titleLayout.layoutParams as LinearLayout.LayoutParams
            params.setMargins(0,statusBar,0,0)
            titleLayout.layoutParams = params

        }

        adapter = RecycleViewListAdapter(object : RecycleViewListAdapter.BindingCallback<String, SimpleItemBinding>{
            override fun bindItem(holder: RecycleViewListAdapter.ViewHolder<SimpleItemBinding>?, entity: String,position: Int) {
                holder?.getBinding()?.data = entity
                holder?.getBinding()?.itemClickListener = View.OnClickListener {
                    when(viewModel.level.value){
                        LEVEL_PROVINCE -> {
                            viewModel.province = viewModel.provinceList[position]
                            viewModel.getCityList()
                            viewModel.isWeathered.value = false
                        }
                        LEVEL_CITY -> {
                            viewModel.city = viewModel.cityList[position]
                            viewModel.getCountyList()
                            viewModel.isWeathered.value = false
                        }
                        LEVEL_COUNTY -> {
                            viewModel.county = viewModel.countyList[position]
                            viewModel.isWeathered.value = true
                        }
                    }
                }
            }

            override fun itemLayoutId(): Int =  R.layout.simple_item

        })
        val layoutManager = GridLayoutManager(context, 1)
        listView.layoutManager = layoutManager
        listView.adapter = adapter


        observer()
    }


    @SuppressLint("FragmentLiveDataObserve")
    private fun observer() {

        // 监听是否选择到了天气
        viewModel.isWeathered.observe(this, Observer {
            if(it && viewModel.county != null){

                if(activity is MainActivity){
                    // 跳转到WeatherActivity
                    var intent = Intent(context,WeatherActivity::class.java)
                    intent.putExtra("weatherId", viewModel.county!!.weather_id)
                    startActivity(intent)
                }else if(activity is WeatherActivity){
                    // 关闭DrawerLayout
                    (activity as WeatherActivity).binding.drawerLayout.closeDrawers()
                    (activity as WeatherActivity).viewModel.weatherId.value = viewModel.county!!.weather_id
                    (activity as WeatherActivity).viewModel.refuseWeather()

                }
            }
        })

        // 监听省市区等级变化
        viewModel.level.observe(this, Observer {
            when(it){
                LEVEL_PROVINCE ->  {
                    backButton.visibility = View.GONE
                    titleText.text = "中国"
                }
                LEVEL_CITY -> {
                    backButton.visibility = View.VISIBLE
                    titleText.text = viewModel.province?.name
                }

                LEVEL_COUNTY -> {
                    backButton.visibility = View.VISIBLE
                    titleText.text = viewModel.city?.name
                }
            }
        })

        viewModel.dataChanged.observe(this, Observer {
            adapter.setDataList(viewModel.dataList)
            listView.scrollToPosition(0)
            hideProgressDialog()
        })

        viewModel.isShowDialog.observe(this, Observer {
            if(it) showProgressDialog() else hideProgressDialog()

        })

        if(viewModel.dataList.isEmpty()){
            viewModel.getProvinceList()
        }

    }

    private fun showProgressDialog(){
        if(dialog == null){
            dialog = Loadingview(context,R.style.CustomDialog)
        }
        dialog?.show()
    }
    private fun hideProgressDialog(){
        dialog?.dismiss()
    }

    companion object {
        const val LEVEL_PROVINCE = 0
        const val LEVEL_CITY = 1
        const val LEVEL_COUNTY = 2
    }
}