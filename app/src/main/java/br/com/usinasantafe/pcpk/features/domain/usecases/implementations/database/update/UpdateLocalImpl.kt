package br.com.usinasantafe.pcpk.features.domain.usecases.implementations.database.update

import br.com.usinasantafe.pcpk.features.domain.usecases.interfaces.database.update.UpdateLocal
import br.com.usinasantafe.pcpk.common.utils.ResultUpdateDatabase
import br.com.usinasantafe.pcpk.common.utils.TB_EQUIP
import br.com.usinasantafe.pcpk.common.utils.TB_LOCAL
import br.com.usinasantafe.pcpk.common.utils.TEXT_CLEAR_TB
import br.com.usinasantafe.pcpk.common.utils.TEXT_RECEIVE_WS_TB
import br.com.usinasantafe.pcpk.common.utils.TEXT_SAVE_DATA_TB
import br.com.usinasantafe.pcpk.features.domain.repositories.stable.LocalRepository
import br.com.usinasantafe.pcpk.features.domain.repositories.variable.ConfigRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateLocalImpl @Inject constructor(
    private val localRepository: LocalRepository,
    private val configRepository: ConfigRepository,
): UpdateLocal {

    override suspend fun invoke(contador: Int, qtde: Int): Flow<Result<ResultUpdateDatabase>> = flow {
        var contUpdate = contador
        emit(Result.success(ResultUpdateDatabase(++contUpdate, TEXT_CLEAR_TB + TB_LOCAL, qtde)))
        localRepository.deleteAllLocal()
        emit(Result.success(ResultUpdateDatabase(++contUpdate, TEXT_RECEIVE_WS_TB + TB_LOCAL, qtde)))
        val config = configRepository.getConfig()
        localRepository.recoverAllLocal(config.nroAparelhoConfig!!)
            .collect{ result ->
                result.fold(
                    onSuccess = { list ->
                        emit(Result.success(ResultUpdateDatabase(++contUpdate, TEXT_SAVE_DATA_TB + TB_LOCAL, qtde)))
                        localRepository.addAllLocal(list)
                    },
                    onFailure = { exception -> emit(Result.failure(Throwable("$exception - $TB_LOCAL"))) }
                )
            }
    }

}