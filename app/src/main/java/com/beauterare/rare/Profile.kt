package com.beauterare.rare

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.beauterare.rare.R

class Profile : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var uploadedImageUri: Uri? = null

    private lateinit var firstnameEditText: EditText
    private lateinit var lastnameEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var birthdateEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var idTypeEditText: EditText
    private lateinit var idNumEditText: EditText
    private lateinit var editButton: ImageButton
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button
    private lateinit var logoutButton: Button
    private lateinit var uploadImageButton: ImageButton
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private lateinit var storageReference: StorageReference

    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize views
        firstnameEditText = view.findViewById(R.id.Firstname)
        lastnameEditText = view.findViewById(R.id.Lastname)
        ageEditText = view.findViewById(R.id.Age)
        birthdateEditText = view.findViewById(R.id.Birthdate)
        addressEditText = view.findViewById(R.id.Address)
        emailEditText = view.findViewById(R.id.Email)
        phoneEditText = view.findViewById(R.id.Phone)
        idTypeEditText = view.findViewById(R.id.IdType)
        idNumEditText = view.findViewById(R.id.IdNum)
        editButton = view.findViewById(R.id.editbuttonn)
        saveButton = view.findViewById(R.id.buttonsave)
        cancelButton = view.findViewById(R.id.buttoncancel)
        logoutButton = view.findViewById(R.id.logoutButton)
        uploadImageButton = view.findViewById(R.id.uploadImageButton)

        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        val userID = auth.currentUser?.uid

        val documentReference = fStore.collection("users").document(userID!!)
        documentReference.addSnapshotListener { documentSnapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (documentSnapshot != null && documentSnapshot.exists()) {
                firstnameEditText.setText(documentSnapshot.getString("fname"))
                lastnameEditText.setText(documentSnapshot.getString("lname"))
                emailEditText.setText(documentSnapshot.getString("email"))
                addressEditText.setText(documentSnapshot.getString("address"))
                ageEditText.setText(documentSnapshot.getString("age"))
                birthdateEditText.setText(documentSnapshot.getString("birthdate"))
                phoneEditText.setText(documentSnapshot.getString("phone"))
                idTypeEditText.setText(documentSnapshot.getString("idType"))
                idNumEditText.setText(documentSnapshot.getString("idNum"))
                val imageUrl = documentSnapshot.getString("imageUrl")
                if (imageUrl != null) {
                    uploadedImageUri = Uri.parse(imageUrl)
                    uploadImageButton.setImageURI(uploadedImageUri)
                }
            } else {
                Log.d(TAG, "Current data: null")
            }
        }

        // Set onClickListener for the edit button
        editButton.setOnClickListener {
            enableEditTexts(true)
        }
        saveButton.setOnClickListener {
            if (uploadedImageUri != null) {
                uploadImageAndSaveData(userID)
            } else {
                saveData(userID, null)
            }
        }
        cancelButton.setOnClickListener {
            goToHomeFragment()
        }
        logoutButton.setOnClickListener {
            goToLogInActivity()
        }

        // Set onClickListener for the uploadImageButton
        uploadImageButton.setOnClickListener {
            openGalleryForImage()
        }

        // If there is a saved instance state, retrieve the image URI
        if (savedInstanceState != null) {
            uploadedImageUri = savedInstanceState.getParcelable(KEY_IMAGE_URI)
            // If uploadedImageUri is not null, set it to the ImageButton
            uploadedImageUri?.let {
                uploadImageButton.setImageURI(it)
            }
        }

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the uploaded image URI if it's not null
        uploadedImageUri?.let {
            outState.putParcelable(KEY_IMAGE_URI, it)
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        // Restore the uploaded image URI if it's not null
        uploadedImageUri?.let {
            uploadImageButton.setImageURI(it)
        }
    }

    private fun enableEditTexts(enabled: Boolean) {
        firstnameEditText.isEnabled = enabled
        lastnameEditText.isEnabled = enabled
        ageEditText.isEnabled = enabled
        birthdateEditText.isEnabled = enabled
        addressEditText.isEnabled = enabled
        emailEditText.isEnabled = enabled
        phoneEditText.isEnabled = enabled
        idTypeEditText.isEnabled = enabled
        idNumEditText.isEnabled = enabled
        uploadImageButton.isEnabled = enabled
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            uploadedImageUri = data?.data
            uploadedImageUri?.let {
                // Set the selected image URI to the ImageButton
                uploadImageButton.setImageURI(it)
            }
        }
    }

    private fun uploadImageAndSaveData(userID: String) {
        val imageRef = storageReference.child("profile_images/${userID}.jpg")
        val uploadTask = imageRef.putFile(uploadedImageUri!!)

        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                saveData(userID, uri.toString())
            }.addOnFailureListener { e ->
                Log.w(TAG, "Error getting download URL", e)
                Toast.makeText(context, "Error getting download URL", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { e ->
            Log.w(TAG, "Image upload failed", e)
            Toast.makeText(context, "Image upload failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveData(userID: String, imageUrl: String?) {
        val user = hashMapOf(
            "fname" to firstnameEditText.text.toString(),
            "lname" to lastnameEditText.text.toString(),
            "email" to emailEditText.text.toString(),
            "address" to addressEditText.text.toString(),
            "age" to ageEditText.text.toString(),
            "birthdate" to birthdateEditText.text.toString(),
            "phone" to phoneEditText.text.toString(),
            "idType" to idTypeEditText.text.toString(),
            "idNum" to idNumEditText.text.toString(),
            "imageUrl" to imageUrl
        ).filterValues { it != null }

        val documentReference = fStore.collection("users").document(userID)
        documentReference.set(user)
            .addOnSuccessListener {
                Toast.makeText(context, "Profile Updated Successfully", Toast.LENGTH_SHORT).show()
                enableEditTexts(false)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating document", e)
                Toast.makeText(context, "Error updating profile", Toast.LENGTH_SHORT).show()
            }
    }

    private fun goToHomeFragment() {
        val homeFragment = HomeFragment() // Replace with your home fragment class
        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
        transaction.replace(
            R.id.frame_layout,
            homeFragment
        ) // Make sure you have a container to replace
        transaction.addToBackStack(null) // Optional: add to backstack
        transaction.commit()
    }

    private fun goToLogInActivity() {
        val intent =
            Intent(activity, LogInActivity::class.java) // Replace with your login activity class
        startActivity(intent)
        activity?.finish() // Optionally finish the current activity to remove it from the back stack
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        private const val REQUEST_CODE = 100
        private const val KEY_IMAGE_URI = "imageUri"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Profile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
