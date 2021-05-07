package com.han.owlmergerprototype

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class CommFragment: Fragment() {
    companion object{
        const val TAG : String = "looooog"

        fun newInstance() : CommFragment {
            return CommFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"CommFragment - onCreate() called")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG,"CommFragment - onAttach() called")
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG,"CommFragment - onCreateView() called")
        val view = inflater.inflate(R.layout.fragment_comm,container,false)

        return view
    }
}