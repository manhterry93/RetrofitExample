package itto.pl.retrofitexample.githubrepo;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GitHubRemoteProvider {
    private static final String BASE_URL = "https://api.github.com/";
    private OkHttpClient.Builder mClientBuilder;
    private Retrofit.Builder mRetrofitBuilder;
    private Retrofit mRetrofit;
    private HttpLoggingInterceptor mInterceptor;

    private HttpLoggingInterceptor getInterceptor() {
        if (mInterceptor == null) {
            mInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        return mInterceptor;
    }

    private OkHttpClient.Builder getClientBuilder() {
        if (mClientBuilder == null) {
            mClientBuilder = new OkHttpClient.Builder();
            mClientBuilder.addInterceptor(getInterceptor());
        }
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
                    .client(getClientBuilder().build())  // will be to do some work like Authentication, Logging,...
                    .build();
        }
        return mRetrofit;
    }


    // Load GitHub Repos
    public void loadGitHubRepos(String user, Callback callback) {
        GitHubClient client = getRetrofit().create(GitHubClient.class);
        client.getGitHubRepos(user).enqueue(callback);
    }

    public void loadGitHubRepos1(String user, Callback callback) {
        GitHubClient client = getRetrofit().create(GitHubClient.class);
        client.getGitHubRepos1().enqueue(callback);
    }

    public void loadGitHubRepos2(String user, Callback callback) {
        GitHubClient client = getRetrofit().create(GitHubClient.class);
        client.getGitHubRepos2("/users/manhterry93/repos", user).enqueue(callback);
    }
}
