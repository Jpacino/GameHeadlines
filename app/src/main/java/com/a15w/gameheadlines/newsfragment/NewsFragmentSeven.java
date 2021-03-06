package com.a15w.gameheadlines.newsfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a15w.gameheadlines.R;
import com.a15w.gameheadlines.bean.NesOneDataBean;
import com.a15w.gameheadlines.presenter.PressenterIpmlNewsSeven;
import com.a15w.gameheadlines.presenter.PressenterIpmlNewsSix;
import com.a15w.gameheadlines.ui.NewsDetailsActivity;
import com.a15w.gameheadlines.utils.DoLog;
import com.a15w.gameheadlines.utils.Utils;
import com.a15w.gameheadlines.view.IViewNewsOne;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/9/6.
 */
public class NewsFragmentSeven extends Fragment implements IViewNewsOne{
    public static final int COLLECTION = 6;
    public static final int ORAGIN = 5;
    public static final int B_VIDEO = 4;
    public static final int S_VIDEO = 3;
    public static final int CONTEST = 2;
    public static final int NORMAL = 1;
    private int sType ;

    private static String lastDataId = 0+"" ;

    private static int lastTime;
    private Context mContext;
    private View headview;

    private List<NesOneDataBean.DataBean.ListBean> recyclerList = new ArrayList<>();
    private List<NesOneDataBean.DataBean.SliderBean> sliderList = new ArrayList<>();
    private List<NesOneDataBean.DataBean.ColumnsBean> columnsList = new ArrayList<>();

    public class ListOnClickLinsener implements View.OnClickListener {
        private NesOneDataBean.DataBean.ListBean listBean;
        public ListOnClickLinsener(NesOneDataBean.DataBean.ListBean listBean){
            this.listBean = listBean;
        }
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext,NewsDetailsActivity.class);
            String dataId = listBean.getDataId();
            intent.putExtra("dataId",dataId);
            mContext.startActivity(intent);
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mListView.setAdapter(new ListAdapter());
        }
    };
    @BindView(R.id.news_main_two_list_lv)
    RecyclerView mListView;

    @BindView(R.id.news_main_two_swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    public static NewsFragmentSeven newInstance(){
        return new NewsFragmentSeven();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_main_two_layout, container, false);
        ButterKnife.bind(this,view);
        mListView.setLayoutManager(new LinearLayoutManager(mContext));
        PressenterIpmlNewsSeven pressenterIpmlNewsTwo = new PressenterIpmlNewsSeven(this, lastDataId, sType, lastTime);
        pressenterIpmlNewsTwo.newsGetTabTitleFromModel();
        sType = sType + 1;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                PressenterIpmlNewsSeven pressenterIpmlNewsOne = new PressenterIpmlNewsSeven(NewsFragmentSeven.this,lastDataId,sType,lastTime);
                pressenterIpmlNewsOne.newsGetTabTitleFromModel();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    @Override
    public void newsOneGetData(NesOneDataBean dataBean) {
        NesOneDataBean.DataBean data = dataBean.getData();
        List<NesOneDataBean.DataBean.ListBean> list1 = data.getList();
        recyclerList.addAll(list1);
        NesOneDataBean.DataBean.ListBean listBean = list1.get(list1.size()-1);
        lastTime = listBean.getCreateTime();
        DoLog.doLog(lastTime+"");
        lastDataId = listBean.getDataId();
        List<NesOneDataBean.DataBean.SliderBean> list2 = data.getSlider();
        List<NesOneDataBean.DataBean.ColumnsBean> list3 = data.getColumns();
        if(list2 != null && list3 != null){
            sliderList.addAll(list2);
            columnsList.addAll(list3);

        }
        mHandler.sendEmptyMessage(1);
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.news_main_one_list_item_content_tv)
        TextView contentTv;
        @BindView(R.id.news_main_one_list_item_time_tv)
        TextView timeTv;
        @BindView(R.id.news_main_one_list_item_big_iv)
        ImageView bigIv;

        public ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class VideoListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.news_main_one_list_item_content_diff_tv)
        TextView contentTv;
        @BindView(R.id.news_main_one_list_item_time_diff_tv)
        TextView timeTv;
        @BindView(R.id.news_main_one_list_item_big_diff_iv)
        ImageView bigIv;
        @BindView(R.id.news_main_one_list_item_diff_type_tv)
        TextView typeTv;

        public VideoListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ContestListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.news_main_one_list_item_content_diff_contest_tv)
        TextView contentTv;
        @BindView(R.id.news_main_one_list_item_time_diff_contest_tv)
        TextView timeTv;
        @BindView(R.id.news_main_one_list_item_big_diff_contest_iv)
        ImageView bigIv;
        @BindView(R.id.news_main_one_list_item_diff_type_contest_tv)
        TextView typeTv;
        public ContestListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class CollectionListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.news_main_one_list_item_collection_time_tv)
        TextView timeTv;
        @BindView(R.id.news_main_one_list_item_collection_one_iv)
        ImageView oneIv;
        @BindView(R.id.news_main_one_list_item_collection_two_iv)
        ImageView twoIv;
        @BindView(R.id.news_main_one_list_item_collection_three_iv)
        ImageView threeIv;
        @BindView(R.id.news_main_one_list_item_collection_type_tv)
        TextView typeTv;
        @BindView(R.id.news_main_one_list_item_collection_title_tv)
        TextView titleTv;
        public CollectionListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class OraginListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.news_main_one_list_item_content_diff_oragin_tv)
        TextView contentTv;
        @BindView(R.id.news_main_one_list_item_time_diff_oragin_tv)
        TextView timeTv;
        @BindView(R.id.news_main_one_list_item_big_diff_oragin_iv)
        ImageView bigIv;
        @BindView(R.id.news_main_one_list_item_diff_type_oragin_tv)
        TextView typeTv;
        public OraginListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class BVideoListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.news_main_one_list_item_big_video_big_iv)
        ImageView bigIv;
        @BindView(R.id.news_main_one_list_item_big_video_content_tv)
        TextView contentTv;
        @BindView(R.id.news_main_one_list_item_big_video_name_tv)
        TextView nameTv;
        @BindView(R.id.news_main_one_list_item_big_video_small_iv)
        ImageView smallIv;
        @BindView(R.id.news_main_one_list_item_big_video_title_tv)
        TextView titleTv;
        @BindView(R.id.news_main_one_list_item_big_video_type_tv)
        TextView typeTv;
        public BVideoListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_news_main_one_list_item, parent, false);
            if(viewType == S_VIDEO){
                headview = LayoutInflater.from(mContext).
                        inflate(R.layout.fragment_news_main_one_list_item_video,parent,false);
                return new VideoListViewHolder(headview);
            }else if(viewType == B_VIDEO){
                headview = LayoutInflater.from(mContext).
                        inflate(R.layout.fragment_news_main_one_list_big_video_item,parent,false);
                return new BVideoListViewHolder(headview);
            }else if(viewType == ORAGIN){
                headview = LayoutInflater.from(mContext).
                        inflate(R.layout.fragment_news_main_one_list_item_oragin,parent,false);
                return new OraginListViewHolder(headview);
            }else if(viewType == CONTEST){
                headview = LayoutInflater.from(mContext).
                        inflate(R.layout.fragment_news_main_one_list_item_contest,parent,false);
                return new ContestListViewHolder(headview);
            }else if(viewType == COLLECTION){
                headview = LayoutInflater.from(mContext).
                        inflate(R.layout.fragment_news_main_one_list_item_collection,parent,false);
                return new CollectionListViewHolder(headview);
            }
            else {
                return new ListViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof VideoListViewHolder){
                VideoListViewHolder viewHolder = (VideoListViewHolder) holder;
                NesOneDataBean.DataBean.ListBean listBean = recyclerList.get(position);
                String thumbnail = listBean.getThumbnail();
                String title = listBean.getTitle();
                int createTime = listBean.getCreateTime();
                String s = Utils.chooseTime(createTime);
                viewHolder.timeTv.setText(s);
                viewHolder.typeTv.setText(listBean.getTagName());
                ImageView bigIv = viewHolder.bigIv;
                Picasso.with(mContext).load(thumbnail).into(bigIv);
                viewHolder.itemView.setOnClickListener(new ListOnClickLinsener(listBean));
            }else if(holder instanceof ContestListViewHolder){
                ContestListViewHolder viewHolder = (ContestListViewHolder) holder;
                NesOneDataBean.DataBean.ListBean listBean = recyclerList.get(position);
                String thumbnail = listBean.getThumbnail();
                String title = listBean.getTitle();
                viewHolder.typeTv.setText(listBean.getTagName());
                int createTime = listBean.getCreateTime();
                String s = Utils.chooseTime(createTime);
                viewHolder.contentTv.setText(title);
                viewHolder.timeTv.setText(s);
                ImageView bigIv = viewHolder.bigIv;
                Picasso.with(mContext).load(thumbnail).into(bigIv);
                viewHolder.itemView.setOnClickListener(new ListOnClickLinsener(listBean));
            }else if(holder instanceof OraginListViewHolder){
                OraginListViewHolder viewHolder = (OraginListViewHolder) holder;
                NesOneDataBean.DataBean.ListBean listBean = recyclerList.get(position);
                String thumbnail = listBean.getThumbnail();
                viewHolder.typeTv.setText(listBean.getTagName());
                String title = listBean.getTitle();
                int createTime = listBean.getCreateTime();
                String s = Utils.chooseTime(createTime);
                viewHolder.contentTv.setText(title);
                viewHolder.timeTv.setText(s);
                ImageView bigIv = viewHolder.bigIv;
                Picasso.with(mContext).load(thumbnail).into(bigIv);
                viewHolder.itemView.setOnClickListener(new ListOnClickLinsener(listBean));
            }else if(holder instanceof BVideoListViewHolder){
                BVideoListViewHolder viewHolder = (BVideoListViewHolder) holder;
                NesOneDataBean.DataBean.ListBean listBean = recyclerList.get(position);
                String thumbnail = listBean.getThumbnail();
                Picasso.with(mContext).load(thumbnail).into(viewHolder.bigIv);
                String avatar = listBean.getAvatar();
                Picasso.with(mContext).load(avatar).into(viewHolder.smallIv);
                String author = listBean.getAuthor();
                viewHolder.nameTv.setText(author);
                String tagName = listBean.getTagName();
                viewHolder.typeTv.setText(tagName);
                String title = listBean.getTitle();
                viewHolder.titleTv.setText(title);
                String excerpt = listBean.getExcerpt();
                viewHolder.contentTv.setText(excerpt);
                viewHolder.itemView.setOnClickListener(new ListOnClickLinsener(listBean));
            }else if (holder instanceof CollectionListViewHolder){
                CollectionListViewHolder viewHolder = (CollectionListViewHolder) holder;
                NesOneDataBean.DataBean.ListBean listBean = recyclerList.get(position );
                String[] images = listBean.getImages();
                Picasso.with(mContext).load(images[0]).into(viewHolder.oneIv);
                Picasso.with(mContext).load(images[1]).into(viewHolder.twoIv);
                Picasso.with(mContext).load(images[2]).into(viewHolder.threeIv);
                int createTime = listBean.getCreateTime();
                String s = Utils.chooseTime(createTime);
                viewHolder.timeTv.setText(s);
                String tagName = listBean.getTagName();
                viewHolder.typeTv.setText(tagName);
                String title = listBean.getTitle();
                viewHolder.titleTv.setText(title);
                viewHolder.itemView.setOnClickListener(new ListOnClickLinsener(listBean));
            }
            else{
                ListViewHolder viewHolder = (ListViewHolder) holder;
                NesOneDataBean.DataBean.ListBean listBean = recyclerList.get(position);
                String thumbnail = listBean.getThumbnail();
                String title = listBean.getTitle();
                int createTime = listBean.getCreateTime();
                String s = Utils.chooseTime(createTime);
                viewHolder.contentTv.setText(title);
                viewHolder.timeTv.setText(s);
                ImageView bigIv = viewHolder.bigIv;
                Picasso.with(mContext).load(thumbnail).into(bigIv);
                viewHolder.itemView.setOnClickListener(new ListOnClickLinsener(listBean));
            }

        }


        @Override
        public int getItemViewType(int position) {
            NesOneDataBean.DataBean.ListBean listBean = recyclerList.get(position);

            switch (listBean.getStyleType()){
                case 0:
                    if(listBean.getTagName().equals("赛事")){
                        return CONTEST;
                    }else if(listBean.getTagName().equals("原创")){
                        return ORAGIN;
                    }
                    return NORMAL;
                case 1:
                    if(listBean.getTagName().equals("视频")){
                        return S_VIDEO;
                    }
                    return S_VIDEO;
                case 2:
                    if(listBean.getTagName().equals("视频")){
                        return B_VIDEO;
                    }
                    return B_VIDEO;
                case 3:
                    if(listBean.getTagName().equals("图集")){
                        return COLLECTION;
                    }
                    return COLLECTION;
                default:
                    return NORMAL;
            }


        }

        @Override
        public int getItemCount() {
            return recyclerList == null ? 0 : recyclerList.size();
        }

    }
}
