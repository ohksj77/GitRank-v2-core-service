package com.dragonguard.core.domain.contribution.client

import com.dragonguard.core.domain.contribution.client.dto.ContributionClientRequest
import com.dragonguard.core.domain.contribution.client.dto.ContributionClientResponse
import com.dragonguard.core.global.exception.RestClientException
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class CodeReviewClient(
    private val openApiRestClient: RestClient,
) {
    companion object {
        private const val PATH = "search/issues?q=reviewed-by:%s+type:pr+created:%d-01-01..%d-12-31"
    }

    fun request(request: ContributionClientRequest): ContributionClientResponse =
        openApiRestClient
            .get()
            .uri(PATH.format(request.githubId, request.year, request.year))
            .headers { it.setBearerAuth(request.githubToken) }
            .accept(MediaType.APPLICATION_JSON)
            .acceptCharset(Charsets.UTF_8)
            .retrieve()
            .body(ContributionClientResponse::class.java) ?: throw RestClientException.codeReview()
}
