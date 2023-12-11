package com.olympia.viewModels.useCases

import com.olympia.model.entities.DUsers
import com.olympia.model.repositories.IUserRepository
import com.olympia.model.verification.CheckBirthday
import com.olympia.model.verification.CheckEmail
import com.olympia.model.verification.CheckGender
import com.olympia.model.verification.CheckPassword
import com.olympia.model.verification.CheckUserName

class AddUserData(private val repository: IUserRepository) {
    suspend operator fun invoke(user: DUsers) {
        repository.insertOrUpdateUser(user)
    }
}