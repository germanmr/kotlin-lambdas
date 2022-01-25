package com.spacexdata.api.external

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/**
 * We move the @Value to a separate class so that we can Inject mock on tests
 */
@Component
class ApiKeyProperty {
    @Value("\${randommer.api.key}")
    lateinit var api_key: String
}
