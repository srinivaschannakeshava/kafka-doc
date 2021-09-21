package in.srini91.learn.kafkatwittersolr.config;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;

@Configuration
public class SolrConfig {
    @Value("${solr.host}")
    private String host;

    @Value("${solr.username}")
    private String user;

    @Value("${solr.password}")
    private String pwd;

    @Bean
    public SolrClient solrClient() {
//        HttpClientUtil.addRequestInterceptor(preemptiveAuthInterceptor());
        final ModifiableSolrParams params = new ModifiableSolrParams();
        params.set(HttpClientUtil.PROP_FOLLOW_REDIRECTS, false);
        params.set(HttpClientUtil.PROP_ALLOW_COMPRESSION, false);
        params.set(HttpClientUtil.PROP_BASIC_AUTH_USER, user);
        params.set(HttpClientUtil.PROP_BASIC_AUTH_PASS, pwd);
        final CloseableHttpClient httpClient = HttpClientUtil.createClient(params);

        return new HttpSolrClient.Builder(host).withHttpClient(httpClient).build();
    }

    @Bean
    public SolrTemplate solrTemplate(SolrClient client) throws Exception {
        return new SolrTemplate(client);
    }
}

