package itto.pl.retrofitexample.overview;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubClient {
    @GET("/users/{user}/repos")
    Call<List<GithubRepo>> getGitHubRepos(@Path("user") String user);
}
