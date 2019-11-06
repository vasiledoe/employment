package features.edit_talent_account

import features.edit_talent_account.model.EditTalentRepo
import org.koin.dsl.module
import org.koin.experimental.builder.factory

val editTalentModule = module {

    factory<EditTalentRepo>()
}