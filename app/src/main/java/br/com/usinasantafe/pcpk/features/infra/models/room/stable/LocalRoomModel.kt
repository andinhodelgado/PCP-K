package br.com.usinasantafe.pcpk.features.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcpk.common.utils.TB_LOCAL
import br.com.usinasantafe.pcpk.features.domain.entities.stable.Local
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = TB_LOCAL)
data class LocalRoomModel (
    @PrimaryKey
    val idLocal: Long,
    val descrLocal: String,
)

fun LocalRoomModel.toLocal(): Local {
    return with(this){
        Local(
            idLocal = this.idLocal,
            descrLocal = this.descrLocal,
        )
    }
}

fun Local.toLocalModel(): LocalRoomModel{
    return with(this){
        LocalRoomModel(
            idLocal = this.idLocal,
            descrLocal = this.descrLocal,
        )
    }
}