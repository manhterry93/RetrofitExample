package itto.pl.retrofitexample.overview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import itto.pl.retrofitexample.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GithubRepoActivity extends AppCompatActivity {
    private static final String TAG = "PL_itto." + GithubRepoActivity.class.getSimpleName();
    private RecyclerView mRepoList;
    private Button mScanBtn;
    private Context mContext;
    private ReposAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        mRepoList = findViewById(R.id.repo_list);
        mScanBtn = findViewById(R.id.scan_repo_btn);
        mScanBtn.setOnClickListener(v -> scanRepos());

        mAdapter = new ReposAdapter();
        mRepoList.setLayoutManager(new LinearLayoutManager(this));
        mRepoList.setAdapter(mAdapter);
    }

    void scanRepos() {
        GitHubRemoteProvider remoteProvider = new GitHubRemoteProvider();
        remoteProvider.loadGitHubRepos("manhterry93", new Callback<List<GithubRepo>>() {
            @Override
            public void onResponse(Call<List<GithubRepo>> call, Response<List<GithubRepo>> response) {
                if (response != null) {
                    mAdapter.replaceData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<GithubRepo>> call, Throwable t) {
                showMessage("Loading repos failed");
                if (t != null) Log.e(TAG, "onFailure: " + t.toString());
            }
        });
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.RepoHolder> {
        private List<GithubRepo> mData;

        public ReposAdapter() {
            mData = new ArrayList<>();
        }

        void replaceData(List<GithubRepo> data) {
            if (data != null) {
                mData.clear();
                mData.addAll(data);
                notifyDataSetChanged();
            }
        }

        @NonNull
        @Override
        public RepoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.github_repo_item, parent, false);
            return new RepoHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RepoHolder holder, int position) {
            holder.bind(getItemAt(position));
        }

        GithubRepo getItemAt(int pos) {
            return mData.get(pos);
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class RepoHolder extends RecyclerView.ViewHolder {
            TextView mName, mUrl;

            public RepoHolder(@NonNull View itemView) {
                super(itemView);
                mName = itemView.findViewById(R.id.name);
                mUrl = itemView.findViewById(R.id.url);
            }

            void bind(GithubRepo repo) {
                mName.setText(repo.getName());
                mUrl.setText(repo.getUrl());
            }
        }
    }
}
