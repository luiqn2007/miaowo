package org.miaowo.miaowo.fragment;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.adapter.TitleListAdapter;
import org.miaowo.miaowo.bean.data.web.Title;
import org.miaowo.miaowo.bean.data.web.TitleList;
import org.miaowo.miaowo.root.BaseActivity;
import org.miaowo.miaowo.set.Exceptions;
import org.miaowo.miaowo.set.windows.MessageWindows;
import org.miaowo.miaowo.util.BeanUtil;
import org.miaowo.miaowo.util.HttpUtil;
import org.miaowo.miaowo.view.load_more_list.ItemRecyclerAdapter;
import org.miaowo.miaowo.view.load_more_list.LoadMoreList;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ListFragment extends Fragment implements Parcelable {
    public static String TAG_URL = "url";

    private LoadMoreList mList;
    private ItemRecyclerAdapter<Title> mAdapter;
    private ArrayList<Title> mItems;
    private BaseActivity mContext;

    private String url;

    public ListFragment() {}

    protected ListFragment(Parcel in) {
        mItems = in.createTypedArrayList(Title.CREATOR);
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mAdapter, flags);
        dest.writeTypedList(mItems);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ListFragment> CREATOR = new Creator<ListFragment>() {
        @Override
        public ListFragment createFromParcel(Parcel in) {
            return new ListFragment(in);
        }

        @Override
        public ListFragment[] newArray(int size) {
            return new ListFragment[size];
        }
    };

    public static ListFragment newInstance(@StringRes int urlId) {
        Bundle args = new Bundle();
        args.putInt(TAG_URL, urlId);

        ListFragment fragment = new ListFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getString(getArguments().getInt(TAG_URL));
        mAdapter = new TitleListAdapter((BaseActivity) getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        mList = (LoadMoreList) v.findViewById(R.id.list_item);
        mItems = new ArrayList<>();
        mContext = (BaseActivity) getActivity();
        MessageWindows mMessageWindows = MessageWindows.windows((BaseActivity) getActivity());
        initList();

        return v;
    }
    private void initList() {
        mList.setAdapter(mAdapter);
        mList.setPullRefresher(() -> HttpUtil.utils().post(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mContext.handleError(Exceptions.E_WEB);
                mContext.updateFragment(ListFragment.this, () -> mList.loadOver());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                TitleList page = BeanUtil.utils().buildFromLastJson(response, TitleList.class);
                if (page == null) {
                    mItems = new ArrayList<>();
                } else {
                    mItems.clear();
                    mItems.addAll(page.getTitles());
                }
                mContext.updateFragment(ListFragment.this, () -> {
                    mAdapter.updateDate(mItems);
                    mList.loadOver();
                });
            }
        }));
        mList.setPushRefresher(() -> {
            mContext.handleError(Exceptions.E_NONE);
            mList.loadOver();
        });
        HttpUtil.utils().post(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mContext.handleError(Exceptions.E_WEB);
                mContext.updateFragment(ListFragment.this, () -> mList.loadOver());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                TitleList page = BeanUtil.utils().buildFromLastJson(response, TitleList.class);
                if (page == null) {
                    mItems = new ArrayList<>();
                } else {
                    mItems.clear();
                    mItems.addAll(page.getTitles());
                }
                mContext.updateFragment(ListFragment.this, () -> {
                    mAdapter.updateDate(mItems);
                    mList.loadOver();
                });
            }
        });
    }

    public enum FragmentGetter {
        ANNOUNCEMENT {
            @Override
            public ListFragment get() {
                return ListFragment.newInstance(R.string.url_announce);
            }
        }, DAILY {
            @Override
            public ListFragment get() {
                return ListFragment.newInstance(R.string.url_daily);
            }
        }, QUESTION {
            @Override
            public ListFragment get() {
                return ListFragment.newInstance(R.string.url_question);
            }
        }, WATER {
            @Override
            public ListFragment get() {
                return ListFragment.newInstance(R.string.url_water);
            }
        }, UNREAD {
            @Override
            public ListFragment get() {
                return ListFragment.newInstance(R.string.url_unread);
            }
        };

        public ListFragment get() {return null;}
    }

}
