package br.com.usinasantafe.pcpk.features.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcpk.common.utils.TB_TERCEIRO
import br.com.usinasantafe.pcpk.features.domain.entities.stable.Terceiro
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = TB_TERCEIRO)
data class TerceiroRoomModel (
    @PrimaryKey(autoGenerate = true)
    val idTerceiro: Long,
    val idBDTerceiro: Long,
    val cpfTerceiro: String,
    val nomeTerceiro: String,
    val empresaTerceiro: String,
)

fun TerceiroRoomModel.toTerceiro(): Terceiro {
    return with(this){
        Terceiro (
            idTerceiro = this.idTerceiro,
            idBDTerceiro = this.idBDTerceiro,
            cpfTerceiro = this.cpfTerceiro,
            nomeTerceiro = this.nomeTerceiro,
            empresaTerceiro = this.empresaTerceiro,
        )
    }
}

fun Terceiro.toTerceiroModel(): TerceiroRoomModel{
    return with(this){
        TerceiroRoomModel(
            idTerceiro = this.idTerceiro,
            idBDTerceiro = this.idBDTerceiro,
            cpfTerceiro = this.cpfTerceiro,
            nomeTerceiro = this.nomeTerceiro,
            empresaTerceiro = this.empresaTerceiro,
        )
    }
}