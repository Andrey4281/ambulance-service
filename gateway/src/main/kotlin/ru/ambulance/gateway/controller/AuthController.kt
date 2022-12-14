package ru.ambulance.gateway.controller


import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController


@RestController
class AuthController {

    @GetMapping(value = ["/"])
    fun getIndex(): String? {
        return "index"
    }

    @GetMapping(value = ["/home"])
    fun getHome(): String? {
        return "home"
    }

    @GetMapping("/whoami")
    @ResponseBody
    fun index(
        @RegisteredOAuth2AuthorizedClient authorizedClient: OAuth2AuthorizedClient,
        @AuthenticationPrincipal(errorOnInvalidType = true) user: OidcUser
    ): Map<String, Any>? {
        val model: MutableMap<String, Any> = HashMap()
        model["clientName"] = authorizedClient.getClientRegistration().getClientName()
        model["userName"] = user.name
        model["userAttributes"] = user.attributes
        return model
    }


}