package itto.pl.retrofitexample.overview;

import okhttp3.OkHttpClient;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GitHubRemoteProvider {
    private static final String BASE_URL = "https://api.github.com";
    private OkHttpClient.Builder mClientBuilder;
    private Retrofit.Builder mRetrofitBuilder;
    private Retrofit mRetrofit;

    private OkHttpClient.Builder getClientBuilder() {
        if (mClientBuilder == null)
            mClientBuilder = new OkHttpClient.Builder();
        return mClientBuilder;
    }

    private Retrofit.Builder getRetrofitBuilder() {
        if (mRetrofitBuilder == null) {
            mRetrofitBuilder = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
        }
        return mRetrofitBuilder;
    }

    private Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = getRetrofitBuilder()
                    .client(getClientBuilder()
                            .build()).build();
        }
        return mRetrofit;
    }


    // Load GitHub Repos
    public void loadGitHubRepos(String user, Callback callback) {
        GitHubClient client = getRetrofit().create(GitHubClient.class);
        client.getGitHubRepos(user).enqueue(callback);
    }
}
