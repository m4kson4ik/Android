package com.olympia.viewModels.useCases

import com.olympia.model.entities.DUsers
import com.olympia.model.repositories.IUserRepository

class DeleteUserData(private val repository: IUserRepository) {
    suspend operator fun invoke(user: DUsers) {
        repository.deleteUser(user)
    }
}