package it.unical.demacs.siieco

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import it.unical.demacs.siieco.AuthenticationActivity
import it.unical.demacs.siieco.data.local.PreferenceRepository
import it.unical.demacs.siieco.data.remote.AuthRepository
import it.unical.demacs.siieco.data.remote.FirestoreRepository
import it.unical.demacs.siieco.ui.home.HomeFragment
import it.unical.demacs.siieco.ui.home.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.concurrent.Executors
import it.unical.demacs.siieco.databinding.ActivityMainBinding
import it.unical.demacs.siieco.domain.model.Post
import it.unical.demacs.siieco.ui.home.PostListAdapter
import java.net.URL


class MainActivity : AppCompatActivity(), HomeFragment.OnHomeFragmentListener, (Post) -> Unit {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private var postList: List<Post> = ArrayList()
    private val postListAdapter: PostListAdapter = PostListAdapter(postList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FacebookSdk.sdkInitialize(applicationContext)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupBottomNavigationView()

        val user = mainViewModel.getCurrentUser()
        user?.let {
            // Name, email address, and profile photo Url

           // nameD.text = it.name

            //mail.text = it.email

            val photoUrl = it.photoUrl


            // Declaring executor to parse the URL
            val executor = Executors.newSingleThreadExecutor()

            // Once the executor parses the URL
            // and receives the image, handler will load it
            // in the ImageView
            val handler = Handler(Looper.getMainLooper())

            // Initializing the image
            var image: Bitmap? = null

            // Only for Background process (can take time depending on the Internet speed)
            executor.execute {
                // Tries to get the image and post it in the ImageView
                // with the help of Handler
                try {
                    val `in` = URL(photoUrl.toString()).openStream()
                    image = BitmapFactory.decodeStream(`in`)

                    // Only for making changes in UI
                    handler.post {
                       // photo.setImageBitmap(image)
                    }
                    Log.d("PHOTO", photoUrl.toString())
                }

                // If the URL doesnot point to
                // image or any other kind of failure
                catch (e: Exception) {
                    e.printStackTrace()
                }
            }


            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid
        }

      //  FirestoreRepository.readUser(user!!.uid, PreferenceRepository(this))
        val db = Firebase.firestore
        val docRef = db.collection("user_type").document(user!!.uid)
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("FALLITO", "Listen failed.", e)
                return@addSnapshotListener
            }
            if(snapshot!!.exists())
                Log.d("ESISTE", "ESISTE")

            if (snapshot != null && snapshot.exists()) {
                Log.d("FATTO", "Current data: ${snapshot.data}")
                Log.d("FATTO", "Current data: ${snapshot.get("type")}")
                //admin.text = snapshot.get("type").toString()
                //PreferenceRepository(this).putTypeUser(snapshot.get("type").toString())
            } else {
                Log.d("NULLO", "Current data: null")
            }
        }


        var callbackManager = CallbackManager.Factory.create()

       /* logout.setOnClickListener {
            Firebase.auth.signOut()
            onLogout()
        }
*//*
        login_button_facebook.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(AuthenticationFragment.TAG, "facebook:onSuccess:$loginResult")
                val credential = FacebookAuthProvider.getCredential(loginResult.accessToken.token)


                mainViewModel.linkWithCredential(credential)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Log.d("COLLEGATO FACEBOOK", "linkWithCredential:success")
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        } else {
                            Log.w("COLLEGATO FACEBOOK", "linkWithCredential:failure", it.exception)
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                }
            }

            override fun onCancel() {
                Log.d(AuthenticationFragment.TAG, "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d(AuthenticationFragment.TAG, "facebook:onError", error)
            }
        })
*/
        if(AuthRepository.checkCurrentUser() != null) {
            loadPostData()
        }
/*
        // init recycler view
        firebase_list.layoutManager = LinearLayoutManager(this)
        firebase_list.adapter = postListAdapter
*/
    }

    private fun loadPostData() {
        FirestoreRepository.getPostList().addOnCompleteListener {
            if(it.isSuccessful) {
                postList = it.result!!.toObjects(Post::class.java)
                postListAdapter.postListItems = postList
                postListAdapter.notifyDataSetChanged()
            } else {
                Log.d("ERROR LOAD POST DATA", it.exception!!.message!!)
            }
        }
    }

    private fun setupBottomNavigationView() {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_goals, R.id.navigation_rewards,
                R.id.navigation_mission, R.id.navigation_trash
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onLogout() {

        mainViewModel.logout(this)

        PreferenceRepository(this).clearData()

        val intent = Intent(this, AuthenticationActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

    }

    override fun invoke(post: Post) {
        Toast.makeText(this, "Click on item ${post.title}", Toast.LENGTH_SHORT).show()
    }
}