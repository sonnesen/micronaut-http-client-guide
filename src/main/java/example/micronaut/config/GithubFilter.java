package example.micronaut.config;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.ClientFilterChain;
import io.micronaut.http.filter.HttpClientFilter;
import org.reactivestreams.Publisher;

@Filter("/repos/**")
@Requires(property = GithubConfiguration.PREFIX + ".username")
@Requires(property = GithubConfiguration.PREFIX + ".token")
public class GithubFilter implements HttpClientFilter {

    private final GithubConfiguration configuration;

    public GithubFilter(GithubConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Publisher<? extends HttpResponse<?>> doFilter(MutableHttpRequest<?> request, ClientFilterChain chain) {
        return chain.proceed(request.basicAuth(configuration.getUsername(), configuration.getToken()));
    }
}
