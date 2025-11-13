package com.mantushnikita.movieapp.ui.profile

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.mantushnikita.movieapp.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var filePath: Uri

    private var binding: FragmentProfileBinding? = null

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        return binding?.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadUserInfo()
                viewModel.profile.collectLatest { profile ->
                    binding?.usernameProfile?.text = profile.username
                    binding?.emailProfile?.text = profile.email
                    if (profile.image.isNotEmpty()) {
                        Glide.with(requireContext()).load(profile.image)
                            .into(binding?.userImage as ImageView)
                    }
                }
            }
        }
        binding?.userImage?.setOnClickListener {
            selectImage()
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                result.data?.data?.let { uri ->
                    filePath = uri

                    val bitmap = MediaStore.Images.Media.getBitmap(
                        requireContext().contentResolver,
                        filePath
                    )
                    binding?.userImage?.setImageBitmap(bitmap)
                    uploadImage()
                }
            }
        }


    @RequiresApi(Build.VERSION_CODES.P)
    private fun selectImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        resultLauncher.launch(intent)
    }

    private fun uploadImage() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (filePath != null && currentUser != null) {
            val uid = currentUser.uid
            val storageReference = FirebaseStorage.getInstance().getReference("images/$uid")

            storageReference.putFile(filePath).addOnSuccessListener { uploadTask ->
                storageReference.downloadUrl.addOnSuccessListener { uri ->
                    val userReference =
                        FirebaseDatabase.getInstance().getReference("Users").child(uid)
                    userReference.child("profileImage").setValue(uri.toString())
                }
                Toast.makeText(requireContext(), "Photo upload complete", Toast.LENGTH_LONG).show()
            }.addOnFailureListener { exception ->
                Toast.makeText(
                    requireContext(),
                    "Photo upload failed: ${exception.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}