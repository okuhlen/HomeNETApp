package Adapters;

import android.app.Activity;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koeksworld.homenet.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import Models.HouseMember;
import Models.HousePost;
import Models.HousePostMetaDataViewModel;
import Models.User;

/**
 * Created by Okuhle on 2017/07/01.
 */

public class HomeNetFeedAdapter extends RecyclerView.Adapter<HomeNetFeedAdapter.HomeNetFeedViewHolder> {

    private List<HousePost> housePostList; //Keep list of house posts
    private List<User> userList; //keep list of related users to the house posts
    private List<HouseMember> houseMemberList; //keep list of related memberships for each house post
    private List<HousePostMetaDataViewModel> housePostData; //Keeps a list of total likes/dislikes for each house post
    private Activity currentActivity;
    public HomeNetFeedAdapter(List<HousePost> housePostList, List<User> userList, List<HouseMember> houseMemberList, List<HousePostMetaDataViewModel> housePostData, Activity currentActivity) {
        this.housePostList = housePostList;
        this.userList = userList;
        this.houseMemberList = houseMemberList;
        this.housePostData = housePostData;
        this.currentActivity = currentActivity;
    }

    @Override
    public HomeNetFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View currentView = inflater.inflate(R.layout.news_feed_item, parent, false);
        return new HomeNetFeedViewHolder(currentView);
    }

    @Override
    public void onBindViewHolder(final HomeNetFeedViewHolder holder, int position) {
        HousePost selectedPost = housePostList.get(position);
        HouseMember selectedMembership = null;
        HousePostMetaDataViewModel housePostMetrics = null;
        for(HouseMember membership : houseMemberList) {
            if (selectedPost.getHouseMemberID() == membership.getHouseMemberID()) {
                selectedMembership = membership;
                break;
            }
        }
        for(HousePostMetaDataViewModel model : housePostData) {
            if (model.getHousePostID() == selectedPost.getHousePostID()) {
                housePostMetrics = model;
            }
        }
        if (selectedMembership != null) {
            for (User selectedUser : userList) {
                if (selectedUser.getId() == selectedMembership.getUserId()) {
                    holder.usernameTextView.setText(selectedUser.getUserName());
                    break;
                }
            }
        }
        holder.newsFeedTextTextView.setText(selectedPost.getPostText());
        holder.totalLikesTextView.setText(Integer.toString(housePostMetrics.getTotalLikes()));
        holder.totalDislikesTextView.setText(Integer.toString(housePostMetrics.getTotalDislikes()));
        holder.optionsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(currentActivity, holder.optionsImageView);
                popupMenu.inflate(R.menu.news_feed_post_options_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()) {
                            case R.id.FlagPostOption:
                                //Show a dialog with the post information


                                break;
                            case R.id.ViewProfileOption:
                                //Show the user's profile

                                break;
                            case R.id.ViewHouseProfileOption:
                                //Show the house profile


                                break;



                        }
                        return false;
                    }
                });
            }
        });
        holder.totalLikesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.totalDislikesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return housePostList.size();
    }

    class HomeNetFeedViewHolder extends RecyclerView.ViewHolder{

        CardView newsFeedCard;
        TextView usernameTextView;
        TextView newsFeedTextTextView;
        TextView totalLikesTextView;
        TextView totalDislikesTextView;
        ImageView newsFeedProfileImageView;
        ImageView optionsImageView;
        ImageView totalLikesImageView, totalDislikesImageView;

        public HomeNetFeedViewHolder(View itemView) {
            super(itemView);
            newsFeedCard = (CardView) itemView.findViewById(R.id.HomeNetNewsFeedCardView);
            usernameTextView = (TextView) itemView.findViewById(R.id.FeedItemUsernameTextView);
            newsFeedTextTextView = (TextView) itemView.findViewById(R.id.FeedItemPostTextView);
            totalLikesTextView = (TextView) itemView.findViewById(R.id.NewsFeedTotalLikesTextView);
            totalDislikesTextView = (TextView) itemView.findViewById(R.id.NewsFeedTotalDislikesTextView);
            optionsImageView = (ImageView) itemView.findViewById(R.id.FeedItemOptionsMenu);
            totalLikesImageView = (ImageView) itemView.findViewById(R.id.LikeImageView);
            totalDislikesImageView = (ImageView) itemView.findViewById(R.id.DislikeImageView);
        }
    }
}