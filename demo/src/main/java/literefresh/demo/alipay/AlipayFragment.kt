package literefresh.demo.alipay

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import layoutbinder.annotations.BindLayout
import literefresh.demo.R
import literefresh.demo.databinding.FragmentAlipayBinding
import literefresh.sample.base.ui.BaseFragment

class AlipayFragment : BaseFragment() {

    companion object {
        fun newInstance() = AlipayFragment()
    }

    @BindLayout(R.layout.fragment_alipay)
    lateinit var binding: FragmentAlipayBinding

    private lateinit var viewModel: AlipayViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AlipayViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}