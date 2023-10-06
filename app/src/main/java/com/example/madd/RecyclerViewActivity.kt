package com.example.madd

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madd.databinding.ActivityRecyclerViewBinding
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class RecyclerViewActivity : AppCompatActivity() {

    lateinit var binding : ActivityRecyclerViewBinding
    lateinit var drawerLayout: DrawerLayout
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var storageReference: StorageReference
    private lateinit var dbref: DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<PostModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)




        binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.posts -> {
                    val intent = Intent(this, RecyclerViewActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.Settings -> {
                    val intent = Intent(this, MoreActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.feedback -> {
                    val intent = Intent(this, UserProfile::class.java)
                    startActivity(intent)
                    true
                }
                R.id.jobs -> {
                    val intent = Intent(this, Add_Post::class.java)
                    startActivity(intent)
                    true
                }

                else -> {
                    false
                }
            }
        }

        drawerLayout = findViewById(R.id.drawerLayout)




        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)

        drawerLayout.addDrawerListener(toggle)

        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)






        userRecyclerView = findViewById(R.id.userlist)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()
        getUserData()


    }

    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance().getReference("Posts")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(PostModel::class.java)
                        user?.let { userArrayList.add(it) }
                    }
                    userRecyclerView.adapter = Post_Adapter(userArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error here
            }
        })
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}