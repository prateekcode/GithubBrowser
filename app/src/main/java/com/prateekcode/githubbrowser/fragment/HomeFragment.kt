package com.prateekcode.githubbrowser.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.prateekcode.githubbrowser.R
import com.prateekcode.githubbrowser.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.addRepoButton.setOnClickListener {
            //Toast.makeText(context, "Hey Buddy!!!!!!", Toast.LENGTH_SHORT).show()
            replaceFragment(AddRepoFragment())
        }


        binding.homeMaterialToolbar.setOnMenuItemClickListener {
                menuItem ->
            when (menuItem.itemId) {
                R.id.add_repo_menu_btn -> {
                    replaceFragment(AddRepoFragment())
                    true
                }
                else -> false
            }
        }


        return binding.root
    }


    private fun replaceFragment(fragment: Fragment) {
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}