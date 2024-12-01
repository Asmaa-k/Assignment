package com.asmaa.khb.filterapp.ui.launcher

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.asmaa.khb.filterapp.databinding.ActivityLauncherBinding
import com.asmaa.khb.filterapp.domain.helpers.LocalDataHelper
import com.asmaa.khb.filterapp.ui.filters.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {

    @Inject
    lateinit var localDataHelper: LocalDataHelper
    private lateinit var binding: ActivityLauncherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startSavingData()
    }

    private fun startSavingData() = lifecycleScope.launch {
        for (i in 1..100) {
            try {
                localDataHelper.saveAllCategoriesAndSubCategoriesIfPossible()
                localDataHelper.saveAllDynamicAttributesIfPossible()
            } catch (exception: Throwable) {
                showSnackbar(exception.message ?: "error saving data")
            }

            withContext(Dispatchers.Main) {
                binding.progressBar.progress = i
                binding.textProgress.text = "$i%"
            }
        }
        startTheMainActivityAndFinish()
    }


    private fun showSnackbar(message: String) =
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()


    private fun startTheMainActivityAndFinish() {
        val intent = Intent(this@LauncherActivity, MainActivity::class.java)
        startActivity(intent)

        finish()
    }
}
