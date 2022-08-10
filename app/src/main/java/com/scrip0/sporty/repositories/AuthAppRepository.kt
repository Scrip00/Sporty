package com.scrip0.sporty.repositories

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.scrip0.sporty.other.Resource
import javax.inject.Inject

class AuthAppRepository @Inject constructor(
	private val firebaseAuth: FirebaseAuth
) {
	private var userLiveData = MutableLiveData<Resource<FirebaseUser>>()

	private val userLoggedOut = MutableLiveData<Boolean>()

	init {
		firebaseAuth.currentUser?.let {
			userLiveData.postValue(Resource.loading(it))
			userLoggedOut.postValue(false)
		}
	}

	fun getUserLiveData() = userLiveData

	fun getLoggedOutLiveData() = userLoggedOut

	fun register(email: String, password: String) {
		userLiveData.postValue(Resource.loading(null))
		firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
			if (task.isSuccessful) {
				userLiveData.postValue(Resource.success(firebaseAuth.currentUser))
			} else {
				val message = task.exception?.message ?: "Unknown error"
				userLiveData.postValue(Resource.error(message, null))
			}
		}
	}

	fun login(email: String, password: String) {
		userLiveData.postValue(Resource.loading(null))
		firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
			if (task.isSuccessful) {
				userLiveData.postValue(Resource.success(firebaseAuth.currentUser))
			} else {
				val message = task.exception?.message ?: "Unknown error"
				userLiveData.postValue(Resource.error(message, null))
			}
		}
	}

	fun logout() {
		firebaseAuth.signOut()
		userLoggedOut.postValue(true)
	}
}