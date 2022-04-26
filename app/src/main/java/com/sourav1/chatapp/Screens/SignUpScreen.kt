package com.sourav1.chatapp.Screens

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.sourav1.chatapp.databinding.ActivitySignUpScreenBinding


const val TAG = "Check"

class SignUpScreen : AppCompatActivity() {

    final val pickImage = 100

    private lateinit var binding: ActivitySignUpScreenBinding
    private lateinit var loginBtn: TextView
    private lateinit var signUpBtn: Button

    private lateinit var fullNameTv: EditText
    private lateinit var emailTv: EditText
    private lateinit var passwordTv: EditText
    private lateinit var userImg: ImageView

    private lateinit var fullName: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var userImgUri: Uri

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var mDb: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private lateinit var progressDialog: ProgressDialog

    private var imageSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginBtn = binding.loginTv1
        signUpBtn = binding.signUpBtn

        fullNameTv = binding.fullNameTv
        passwordTv = binding.passwordTv
        emailTv = binding.emailTv
        userImg = binding.signUpUserImg

        progressDialog = ProgressDialog(this)

        loginBtn.setOnClickListener {
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
        }

        userImg.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        signUpBtn.setOnClickListener {
            fullName = fullNameTv.text.toString()
            email = emailTv.text.toString()
            password = passwordTv.text.toString()

            Log.i(TAG, "Full name: $fullName")
            Log.i(TAG, "Email: $email")
            Log.i(TAG, "Password: $password")

            if (fullName.isEmpty()) {
                fullNameTv.error = "Can't be empty!"
            } else if (email.isEmpty()) {
                emailTv.error = "Can't be empty!"
            } else if (password.isEmpty()) {
                passwordTv.error = "Can't be empty!"
            } else if (!imageSelected) {
                Toast.makeText(this, "Please select an image first !..", Toast.LENGTH_SHORT).show()
            } else {
                createAccount(fullName, email, password)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            userImgUri = data?.data!!
            imageSelected = true
            userImg.setImageURI(userImgUri)
        }
    }

    private fun createAccount(fullName: String, email: String, password: String) {
        progressDialog.setTitle("Sign In")
        progressDialog.setMessage("Registering user, please wait a while...")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                progressDialog.dismiss()
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid
                    val ref = mDb.collection("Users").document("Data").collection("PersonalInfo")
                        .document(uid!!)


                    val mp: MutableMap<String, String> = mutableMapOf()
                    mp["Name"] = fullName
                    mp["Uid"] = auth.currentUser?.uid.toString()

                    ref.set(mp)
                        .addOnCompleteListener {
                            Log.d("DB", "Data saved Successfully:)")
                        }
                        .addOnFailureListener {
                            Log.d("DB", "Data is not saved :(")
                        }

                    val storageRef =
                        storage.reference.child("UserImage").child(auth.currentUser!!.uid)
                    storageRef.putFile(userImgUri)
                        .addOnSuccessListener {
                            Log.d("Storage", "Image saved Successfully:)")
                        }

                    //Sign In Successfully, Update UI
                    Log.d("Email Auth:", "User created with email: Success")
                    goToDashboard()
                } else {
                    Toast.makeText(
                        this,
                        "Authentication Failed: ${task.exception}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun goToDashboard() {
        val intent = Intent(this, Dashboard::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}