package ru.ambulance.appeal.config

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import kotlinx.coroutines.FlowPreview
import org.springdoc.core.annotations.RouterOperation
import org.springdoc.core.annotations.RouterOperations
import org.springdoc.core.fn.builders.parameter.Builder
import org.springdoc.kotlin.docRouter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.reactive.config.ResourceHandlerRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import ru.ambulance.appeal.controller.AppealHandler
import ru.ambulance.appeal.model.dto.AppealDto
import ru.ambulance.appeal.service.AppealService

@Configuration
class WebConfig: WebFluxConfigurer {

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/swagger-ui.html**")
            .addResourceLocations("classpath:/META-INF/resources/")

        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/")
    }

    @Bean
    @FlowPreview
//    @RouterOperations(
//        RouterOperation(
//            path = "/appeal/{id}",
//            produces = [MediaType.APPLICATION_JSON_VALUE],
//            method = arrayOf(RequestMethod.GET),
//            beanClass = AppealService::class,
//            beanMethod = "findById",
//            operation = Operation(
//                operationId = "showAppeal",
//                responses = [
//                    ApiResponse(responseCode = "200", description = "successful operation",
//                        content = [Content(schema = Schema(implementation = AppealDto::class))]
//                    ),
//                    ApiResponse(responseCode = "400", description = "Invalid Appeal ID supplied"),
//                    ApiResponse(responseCode = "404", description = "Appeal not found")
//                ],
//                parameters = [
//                    Parameter(`in` = ParameterIn.PATH, name = "id")
//                ]
//            )
//        ),
//        RouterOperation(
//            path = "/appeal",
//            produces = [MediaType.APPLICATION_JSON_VALUE],
//            method = arrayOf(RequestMethod.GET),
//            beanClass = AppealService::class,
//            beanMethod = "findAll",
//            operation = Operation(
//                operationId = "showAppealList",
//                responses = [
//                    ApiResponse(responseCode = "200", description = "successful operation",
//                        content = [Content(schema = Schema(implementation = AppealDto::class))]
//                    ),
//                    ApiResponse(responseCode = "400", description = "Invalid Appeal ID supplied"),
//                    ApiResponse(responseCode = "404", description = "Appeal not found")
//                ]
//            )
//        )
//    )
    fun appealRouter(appealHandler: AppealHandler) : RouterFunction<ServerResponse> = docRouter {
        GET("/appeal/{appealId}", accept(MediaType.APPLICATION_JSON), appealHandler::showAppeal)
        {
            it.operationId("showAppeal")
                .beanClass(AppealService::class.java)
                .beanMethod("findById")
                .parameter(Builder.parameterBuilder().name("appealId").`in`(ParameterIn.PATH))
        }
        GET("/appeal", accept(MediaType.APPLICATION_JSON), appealHandler::showAppealList)
        {
            it.operationId("showAppealList")
                .beanClass(AppealService::class.java)
                .beanMethod("findAll")
        }
    }

}