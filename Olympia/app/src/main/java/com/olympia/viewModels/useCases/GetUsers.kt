package com.olympia.viewModels.useCases

import com.olympia.model.entities.DUsers
import com.olympia.model.repositories.IUserRepository
import kotlinx.coroutines.flow.Flow

class GetUsers(private val repository: IUserRepository) {
    operator fun invoke(): Flow<List<DUsers>> {
        return repository.getUsers()
    }
}