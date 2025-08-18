package com.example.glimonprot.presentation.ui.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.glimonprot.domain.repository.GlimonRepository
import stud.gilmon.data.local.entities.UsersEntity
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    context: Context,
    val glimonRepository: GlimonRepository
) : ViewModel() {

    fun updateUserData(usersEntity: UsersEntity){
        viewModelScope.launch {
            glimonRepository.upsertUser(usersEntity)
        }
    }
}

