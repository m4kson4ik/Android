package com.olympia.viewModels.useCases

import com.olympia.model.entities.DUsers
import com.olympia.model.repositories.IUserRepository

class GetUserData(private val repository: IUserRepository) {
    suspend operator fun invoke(id: Int?): DUsers? {
        return repository.getRecord(id)
    }
}