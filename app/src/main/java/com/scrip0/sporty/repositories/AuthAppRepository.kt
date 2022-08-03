package com.scrip0.sporty.repositories

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthAppRepository(
	val application: Application
) {
	@Inject
	lateinit var firebaseAuth: FirebaseAuth

	private var userLiveData = MutableLiveData<FirebaseUser>()

	private val loggedOutLiveData = MutableLiveData<Boolean>()

	init {
		firebaseAuth.currentUser?.let {
			userLiveData.postValue(it)
			loggedOutLiveData.postValue(false)
		}
	}

	fun getUserLiveData() = userLiveData

	fun getLoggedOutLiveData() = loggedOutLiveData
}