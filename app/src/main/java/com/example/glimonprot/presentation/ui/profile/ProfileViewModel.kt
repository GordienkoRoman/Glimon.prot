package stud.gilmon.presentation.ui.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stud.gilmon.data.local.entities.UsersEntity
import stud.gilmon.domain.RoomRepository
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    context: Context,
    val roomRepository: RoomRepository
) : ViewModel() {

    fun updateUserData(usersEntity: UsersEntity){
        viewModelScope.launch {
            roomRepository.upsertUser(usersEntity)
        }
    }
}

