package com.prateekcode.githubbrowser.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.prateekcode.githubbrowser.R
import com.prateekcode.githubbrowser.adapter.RepoAdapter
import com.prateekcode.githubbrowser.databinding.FragmentHomeBinding
import com.prateekcode.githubbrowser.db.RepoDatabase
import com.prateekcode.githubbrowser.db.Repodao
import com.prateekcode.githubbrowser.db.Repotity
import com.prateekcode.githubbrowser.viewmodel.ApiViewModel
import com.prateekcode.githubbrowser.viewmodel.ApiViewModelFactory


class HomeFragment : Fragment(), RepoAdapter.OnItemClickListener {

    lateinit var binding: FragmentHomeBinding
    private val repoAdapter: RepoAdapter by lazy { RepoAdapter(this) }
    var viewModel: ApiViewModel? =null
    lateinit var repodao: Repodao
    private lateinit var repoList: List<Repotity>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        //Initializing the database
        repodao = RepoDatabase.getDatabase(context!!).repoDao()
        val factory = ApiViewModelFactory(repodao)
        viewModel = ViewModelProvider(this, factory).get(ApiViewModel::class.java)

        repoList = repodao.getAllRepo()
        if (repoList.isEmpty()){
            binding.trackRepoTv.visibility = View.VISIBLE
            binding.addRepoButton.visibility = View.VISIBLE
        }else{
            binding.trackRepoTv.visibility = View.GONE
            binding.addRepoButton.visibility = View.GONE
        }

        binding.addRepoButton.setOnClickListener {
            //Toast.makeText(context, "Hey Buddy!!!!!!", Toast.LENGTH_SHORT).show()
            replaceFragment(AddRepoFragment())
        }

        repoAdapter.setData(repoList)
        val layoutManager = LinearLayoutManager(context!!)
        binding.repoRecyclerView.layoutManager = layoutManager
        binding.repoRecyclerView.adapter = repoAdapter
        layoutManager.stackFromEnd = true
        layoutManager.reverseLayout = true

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

    override fun onClick(position: Int) {
        replaceFragment(DetailFragment(
            repoList[position].repositoryName,
            repoList[position].descriptionRepo,
            repoList[position].htmlUrl,
            repoList[position].ownerName
        ))
    }

    override fun onShareButtonClick(position: Int) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Checkout this amazing repo at Github ${repoList[position].htmlUrl}")
        startActivity(Intent.createChooser(intent, "Choose the App"))
        //Toast.makeText(context, "Share this url ${repoList[position].htmlUrl}", Toast.LENGTH_SHORT).show()
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}