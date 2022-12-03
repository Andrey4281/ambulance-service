package ru.ambulance.appeal.config

import kotlinx.coroutines.FlowPreview
import org.springdoc.kotlin.docRouter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.config.ResourceHandlerRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import ru.ambulance.appeal.controller.AppealHandler
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
    fun appealRouter(appealHandler: AppealHandler) : RouterFunction<ServerResponse> = docRouter {
        GET("/appeal", accept(MediaType.APPLICATION_JSON), appealHandler::showAppealList)
        {
            it.operationId("showAppealList")
                .beanClass(AppealService::class.java)
                .beanMethod("showAppealList")
        }
        POST("/appeal/createNewAppeal", accept(MediaType.APPLICATION_JSON), appealHandler::createAppeal)
        {
            it.operationId("createNewAppeal")
                    .beanClass(AppealService::class.java)
                    .beanMethod("createNewAppeal")
        }
    }

}