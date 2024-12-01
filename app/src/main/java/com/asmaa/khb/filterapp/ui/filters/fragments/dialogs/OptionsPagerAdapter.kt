package com.asmaa.khb.filterapp.ui.filters.fragments.dialogs

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.asmaa.khb.filterapp.data.local.OptionsRealm


class OptionsPagerAdapter(
    fragment: Fragment,
    val options: ArrayList<OptionsRealm>,
    val fieldLabel: String,
    private val onOptionResult: (item: OptionsRealm) -> Unit
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OptionsPageFragment.newInstance(options, fieldLabel, onOptionResult)
            1 -> OptionsPageFragment.newInstance(options, fieldLabel, onOptionResult)
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}
