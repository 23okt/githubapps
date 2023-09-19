    package com.example.appsgithub.ui.main

    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.view.View
    import androidx.appcompat.widget.SearchView
    import androidx.lifecycle.ViewModelProvider
    import androidx.recyclerview.widget.DividerItemDecoration
    import androidx.recyclerview.widget.LinearLayoutManager
    import com.example.appsgithub.R
    import com.example.appsgithub.adapter.MainAdapter
    import com.example.appsgithub.databinding.ActivityMainBinding
    import com.example.appsgithub.ui.viewmodel.MainViewModel

    class MainActivity : AppCompatActivity() {

       private lateinit var binding: ActivityMainBinding
       private lateinit var mainViewModel:MainViewModel
       private lateinit var searchView: SearchView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            searchView = findViewById(R.id.searchUser)
            searchView.queryHint = resources.getString(R.string.search_user)
            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchView.clearFocus()
                    mainViewModel.getUser(query?:"a")
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
            mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

            mainViewModel.users.observe(this) {
                binding.tvNotFound.visibility = if(it.isNullOrEmpty()){
                    View.VISIBLE
                }else{
                    View.INVISIBLE
                }
                binding.rvUser.apply {
                    val adapter = MainAdapter(it)
                    binding.rvUser.adapter = adapter
                }
            }

            val layoutManager = LinearLayoutManager(this)
            binding.rvUser.layoutManager = layoutManager
            val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
            binding.rvUser.addItemDecoration(itemDecoration)

            mainViewModel.ifLoading.observe(this){
                showLoading(it)
            }

        }
        private fun showLoading(isLoading: Boolean){
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }