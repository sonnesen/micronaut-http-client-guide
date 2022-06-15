package example.micronaut.controller;

import example.micronaut.model.GithubRelease;
import example.micronaut.service.GithubApiClient;
import example.micronaut.service.GithubLowLevelClient;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller("/github")
public class GithubController {

    private final GithubLowLevelClient githubLowLevelClient;
    private final GithubApiClient githubApiClient;

    public GithubController(GithubLowLevelClient githubLowLevelClient, GithubApiClient githubApiClient) {
        this.githubLowLevelClient = githubLowLevelClient;
        this.githubApiClient = githubApiClient;
    }

    @Get("/releases-lowlevel")
    Mono<List<GithubRelease>> releasesWithLowLevelClient() {
        return githubLowLevelClient.fetchReleases();
    }

    @Get(uri = "/releases", produces = MediaType.APPLICATION_JSON_STREAM)
    Publisher<GithubRelease> fetchReleases() {
        return githubApiClient.fetchReleases();
    }
}
