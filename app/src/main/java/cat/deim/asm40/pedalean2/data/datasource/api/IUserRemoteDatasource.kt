package cat.deim.asm40.pedalean2.data.datasource.api

import com.pedalean2.common.datasource.local.model.UserModel
import com.pedalean2.common.interfaces.IDatasource

interface IUserRemoteDatasource : IDatasource<UserModel>, IUserAuthDatasource
