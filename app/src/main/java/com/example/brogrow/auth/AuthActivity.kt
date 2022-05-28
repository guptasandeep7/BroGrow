package com.example.brogrow.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.brogrow.R
import com.example.brogrow.repo.Datastore
import com.example.brogrow.view.dashboard.DashBoardActivity
import kotlinx.coroutines.launch

class AuthActivity : AppCompatActivity() {
    lateinit var datastore: Datastore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        datastore = Datastore(this)

        lifecycleScope.launch {
            if(datastore.isLogin()){
                startActivity(Intent(this@AuthActivity, DashBoardActivity::class.java))
                finish()
            }
        }
    }
}