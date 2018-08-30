package itto.pl.retrofitexample.githubrepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface GitHubClient {

    // Load GitHub Repos
    @GET("users/{user}/repos")
    Call<List<GithubRepo>> getGitHubRepos(@Path("user") String user);

    @GET("users/manhterry93/repos")
    Call<List<GithubRepo>> getGitHubRepos1();

    @GET
    Call<List<GithubRepo>> getGitHubRepos2(@Url String url, @Path("user") String user);

    /* -------------------------------*/


}
