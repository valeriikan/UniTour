package fi.oulu.unitour.helpers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import fi.oulu.unitour.R;
import fi.oulu.unitour.activities.YoutubeActivity;

import static com.google.android.youtube.player.YouTubePlayer.PlayerStyle.DEFAULT;

public class YoutubeFragment extends Fragment {

    private static String API_KEY ="AIzaSyCvEl8lB5YoPIW6PE4Lql7C-cE1qfvoquY";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_youtube,container,false);
        YouTubePlayerSupportFragment youTubeFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_layout,youTubeFragment).commit();

        youTubeFragment.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean Restored) {
                if(!Restored){
                    player.setPlayerStyle(DEFAULT);
                    player.loadVideo(YoutubeActivity.videoID);
                    player.play();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
                String errorMessage = error.toString();
                Toast.makeText(getActivity(),errorMessage,Toast.LENGTH_SHORT).show();
                Log.d("errorMessage: ", errorMessage);
            }
        });
        return view;
    }
}