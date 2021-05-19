package com.psm.tablelayout.Search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.psm.tablelayout.R


class SearchFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.search, container, false)
    }

    //Va apesar el argumento del adaptador
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner: Spinner
        spinner = view.findViewById(R.id.SearchLayoutAdvanced)

        spinner.adapter = activity?.let {
            ArrayAdapter(
                it,
                R.layout.support_simple_spinner_dropdown_item,
                resources.getStringArray(R.array.StringsSearchOptions)
            )
        }


    }

}