package com.yibao.music.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yibao.music.R;
import com.yibao.music.adapter.SongAdapter;
import com.yibao.music.base.BaseFragment;
import com.yibao.music.fragment.dialogfrag.MusicBottomSheetDialog;
import com.yibao.music.model.MusicBean;
import com.yibao.music.util.ColorUtil;
import com.yibao.music.util.Constants;
import com.yibao.music.util.LogUtil;
import com.yibao.music.util.MusicListUtil;
import com.yibao.music.util.SharePrefrencesUtil;
import com.yibao.music.view.music.MusicView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @项目名： ArtisanMusic
 * @包名： com.yibao.music.artisanlist
 * @文件名: SongFragment
 * @author: Stran
 * @Email: www.strangermy@outlook.com / www.stranger98@gmail.com
 * @创建时间: 2018/2/4 21:45
 * @描述： {显示当前音乐列表}
 */

public class SongFragment extends BaseFragment {
    @BindView(R.id.iv_music_category_paly)
    ImageView mIvMusicCategoryPaly;
    @BindView(R.id.tv_music_category_songname)
    TextView mMusicCategorySongName;
    @BindView(R.id.tv_music_category_score)
    TextView mMusicCategoryScore;
    @BindView(R.id.tv_music_category_frequency)
    TextView mMusicCategoryFrequency;
    @BindView(R.id.tv_music_category_addtime)
    TextView mMusicCategoryAddtime;
    @BindView(R.id.musci_view)
    MusicView mMusciView;
    private Unbinder unbinder;
    private SongAdapter mSongAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.song_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData(true, Constants.NUMBER_ZOER);
        initListener();

        return view;
    }

    private void initListener() {
        mSongAdapter.setOnItemMenuListener(() -> MusicBottomSheetDialog.newInstance().getBottomDialog(getActivity()));
    }


    /**
     * 是否对歌曲列表按时间排序，按时间排序时，StickyView不显示
     *
     * @param isShowSlidebar   只有按歌曲名排列时，Slidebar才显示 。
     * @param isShowStickyView 控制列表的StickyView是否显示，0 显示 ，1 ：不显示
     */
    private void initData(boolean isShowSlidebar, int isShowStickyView) {
        if (isShowStickyView == Constants.NUMBER_ZOER) {
            mSongAdapter = new SongAdapter(mActivity, MusicListUtil.sortMusicAbc(mSongList), isShowStickyView);
        } else if (isShowStickyView == Constants.NUMBER_ONE) {
            List<MusicBean> musicAddtimeList = MusicListUtil.sortMusicAddTime(mMusicBeanDao.queryBuilder().list(), Constants.NUMBER_ONE);
            mSongAdapter = new SongAdapter(mActivity, musicAddtimeList, isShowStickyView);
        }
        mMusciView.setAdapter(mActivity, Constants.NUMBER_ONE, isShowSlidebar, mSongAdapter);
    }

    @OnClick({R.id.iv_music_category_paly,
            R.id.tv_music_category_songname, R.id.tv_music_category_score, R.id.tv_music_category_frequency, R.id.tv_music_category_addtime, R.id.musci_view})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_music_category_paly:
                randomPlayMusic();
                break;
            case R.id.tv_music_category_songname:
                switchListCategory(1);
                break;
            case R.id.tv_music_category_score:
                switchListCategory(2);
                break;
            case R.id.tv_music_category_frequency:
                switchListCategory(3);
                break;
            case R.id.tv_music_category_addtime:
                switchListCategory(4);
                break;
            case R.id.musci_view:
                break;
            default:
                break;
        }
    }


    private void switchListCategory(int flag) {
        switch (flag) {
            case 1:
                setAllCategoryNotNormal(Constants.NUMBER_ONE);
                mMusicCategorySongName.setTextColor(ColorUtil.wihtle);
                mMusicCategorySongName.setBackgroundResource(R.drawable.btn_category_songname_down_selector);
                initData(true, Constants.NUMBER_ZOER);
                break;
            case 2:
                LogUtil.d(" 119  songfragment mFlag " + SharePrefrencesUtil.getMusicDataListFlag(getActivity()));
                setAllCategoryNotNormal(Constants.NUMBER_TWO);
                mMusicCategoryScore.setTextColor(ColorUtil.wihtle);
                mMusicCategoryScore.setBackgroundResource(R.drawable.btn_category_score_down_selector);
                break;
            case 3:
                setAllCategoryNotNormal(Constants.NUMBER_THRRE);
                mMusicCategoryFrequency.setTextColor(ColorUtil.wihtle);
                mMusicCategoryFrequency.setBackgroundResource(R.drawable.btn_category_score_down_selector);
                break;
            case 4:
                setAllCategoryNotNormal(Constants.NUMBER_FOUR);
                mMusicCategoryAddtime.setBackgroundResource(R.drawable.btn_category_views_down_selector);
                mMusicCategoryAddtime.setTextColor(ColorUtil.wihtle);
                initData(false, Constants.NUMBER_ONE);
                break;
            default:
                break;
        }


    }


    private void setAllCategoryNotNormal(int playListFlag) {
        mMusicCategorySongName.setTextColor(ColorUtil.textName);
        mMusicCategorySongName.setBackgroundResource(R.drawable.btn_category_songname_selector);
        mMusicCategoryScore.setTextColor(ColorUtil.textName);
        mMusicCategoryScore.setBackgroundResource(R.drawable.btn_category_score_selector);
        mMusicCategoryFrequency.setTextColor(ColorUtil.textName);
        mMusicCategoryFrequency.setBackgroundResource(R.drawable.btn_category_score_selector);
        mMusicCategoryAddtime.setTextColor(ColorUtil.textName);
        mMusicCategoryAddtime.setBackgroundResource(R.drawable.btn_category_views_selector);
        SharePrefrencesUtil.setMusicDataListFlag(mActivity, playListFlag);
    }


    public static SongFragment newInstance() {

        return new SongFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
