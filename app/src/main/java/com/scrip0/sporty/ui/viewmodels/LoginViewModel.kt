package com.scrip0.sporty.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.scrip0.sporty.other.Resource
import com.scrip0.sporty.repositories.AuthAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
	private val authAppRepository: AuthAppRepository
) : ViewModel() {
	private val _userLiveData = authAppRepository.getUserLiveData()
	val userLiveData: LiveData<Resource<FirebaseUser>> = _userLiveData

	private val _userLoggedOut = authAppRepository.getLoggedOutLiveData()
	val userLoggedOut: LiveData<Boolean> = _userLoggedOut

	fun login(email: String, password: String) {
		authAppRepository.login(email, password)
	}

	fun register(email: String, password: String) {
		authAppRepository.register(email, password)
	}
}