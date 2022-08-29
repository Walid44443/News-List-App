package com.linkdevelopment.walid44443.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

abstract class BaseFragment<DB : ViewDataBinding, VM : ViewModel> : Fragment(){
    protected lateinit var binding: DB
    protected lateinit var viewModel: VM

    protected abstract val layoutId: Int
    protected abstract val viewModelClass: Class<VM>


    protected abstract fun viewModelFactory(): ViewModelProvider.Factory

    protected open fun viewModelStoreOwner(): ViewModelStoreOwner = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewModel =
            ViewModelProvider(viewModelStoreOwner(), viewModelFactory())[this.viewModelClass]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        this.binding = DataBindingUtil.inflate(inflater, this.layoutId, container, false)
        this.binding.lifecycleOwner = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        return this.binding.root
    }


    fun isBindingInitialized() = ::binding.isInitialized
}