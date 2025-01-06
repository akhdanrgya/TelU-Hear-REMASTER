package com.tubes.teluhear;

import com.tubes.teluhear.database.MusicModel;

import java.util.List;

public interface MusicCardClickListener {
    void onMusicCardClicked(MusicModel music, List<MusicModel> musicList);
}
