package org.bnb.dw.core.cucumber

import cucumber.api.TypeRegistry
import cucumber.api.TypeRegistryConfigurer
import io.cucumber.cucumberexpressions.ParameterType
import io.cucumber.cucumberexpressions.Transformer
import java.util.*
import java.util.Locale.ENGLISH

class CustomTypesRegistryConfiguration: TypeRegistryConfigurer {
    override fun locale(): Locale {
        return ENGLISH
    }

    override fun configureTypeRegistry(typeRegistry: TypeRegistry) {
        typeRegistry.defineParameterType(ParameterType("boolean", "true|false|yes|no", Boolean::class.java, Transformer { v -> "true".toUpperCase() == v.toUpperCase() || "yes".toUpperCase() == v.toUpperCase() }))
    }
}
