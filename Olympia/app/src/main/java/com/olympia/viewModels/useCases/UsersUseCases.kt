package com.olympia.viewModels.useCases

import javax.inject.Inject

data class UsersUseCases constructor(val addUserData: AddUserData, val deleteUserData: DeleteUserData, val getUsers: GetUsers, val getUserData: GetUserData)