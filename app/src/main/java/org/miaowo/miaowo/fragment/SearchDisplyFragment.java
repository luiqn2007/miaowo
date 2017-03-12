package org.miaowo.miaowo.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.Question;
import org.miaowo.miaowo.bean.data.User;
import org.miaowo.miaowo.impl.MsgImpl;
import org.miaowo.miaowo.impl.UsersImpl;
import org.miaowo.miaowo.impl.interfaces.Messages;
import org.miaowo.miaowo.impl.interfaces.Users;
import org.miaowo.miaowo.root.D;
import org.miaowo.miaowo.set.Exceptions;
import org.miaowo.miaowo.set.windows.MessageWindows;
import org.miaowo.miaowo.util.FormatUtil;
import org.miaowo.miaowo.util.ImageUtil;

import java.util.ArrayList;

public class SearchDisplyFragment extends Fragment {
    final private static String TAG_TYPE = "layout";
    final private static String TAG_RESULT = "result";

    final public static int TYPE_USER = 0;
    final public static int TYPE_TOPIC = 1;
    final public static int TYPE_QUESTION = 2;

    private ArrayList result;
    private BaseAdapter mAdapter;
    private int type;

    private Users mUsers;
    private Messages mMessages;
    private MessageWindows mMessageWindows;

    public SearchDisplyFragment() {
        // Required empty public constructor
    }

    public static SearchDisplyFragment newInstance(int type, ArrayList result) {
        SearchDisplyFragment fragment = new SearchDisplyFragment();
        Bundle args = new Bundle();
        args.putInt(TAG_TYPE, type);
        args.putSerializable(TAG_RESULT, result);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(TAG_TYPE);
            result = (ArrayList) getArguments().getSerializable(TAG_RESULT);
        }

        mMessages = new MsgImpl();
        mUsers = new UsersImpl();
        mMessageWindows = MessageWindows.windows();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        initView(view);
        return view;
    }

    private void initView(View v) {
        TextView tv_title = (TextView) v.findViewById(R.id.tv_title);
        ListView mList = (ListView) v.findViewById(R.id.lv_result);

        switch (type) {
            case TYPE_QUESTION:
                tv_title.setText("问题");
                break;
            case TYPE_TOPIC:
                tv_title.setText("话题");
                break;
            case TYPE_USER:
                tv_title.setText("用户");
                break;
        }

        mAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return result == null ? 0 : result.size();
            }

            @Override
            public Object getItem(int position) {
                return result.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                switch (type) {
                    case TYPE_QUESTION:
                        convertView = setQuestion((Question) getItem(position), convertView);
                        break;
                    case TYPE_TOPIC:
                        convertView = setTopic((Question) getItem(position), convertView);
                        break;
                    case TYPE_USER:
                        convertView = setUser((User) getItem(position), convertView);
                        break;
                    default:
                        convertView = null;
                        break;
                }
                return convertView;
            }
        };
        mList.setAdapter(mAdapter);
    }

    private View setQuestion(Question item, View view) {
        if (view == null) {
            view = View.inflate(getContext(), R.layout.list_question, null);
            view.setTag(new ViewHolder(view));
        }
        ViewHolder holder = (ViewHolder) view.getTag();

        User u = item.user;
        holder.setOnClickListener((v) -> mMessageWindows.showQuestion(item), R.id.rl_item);
        ImageUtil.utils().setUser(holder.getImageView(R.id.iv_user), u, true);
        holder.getTextView(R.id.tv_user).setText(u.username);
        holder.getTextView(R.id.tv_time).setText(FormatUtil.format().time(item.timestamp));
        holder.getTextView(R.id.tv_title).setText(item.titleRaw);
        holder.getTextView(R.id.tv_count).setText(item.postcount+ " 帖子, " + item.viewcount + " 浏览");

        return view;
    }

    private View setTopic(Question item, View view) {
        if (view == null) {
            view = View.inflate(getContext(), R.layout.list_topic, null);
            view.setTag(new ViewHolder(view));
        }
        ViewHolder holder = (ViewHolder) view.getTag();

        holder.getTextView(R.id.tv_title).setText(item.titleRaw);
        holder.getTextView(R.id.tv_count).setText(item.postcount + " 帖子, " + item.viewcount + " 浏览");
        holder.getTextView(R.id.tv_time).setText(FormatUtil.format().time(item.timestamp));
        view.setOnClickListener(v -> mMessageWindows.showTopic(item));
        return view;
    }

    private View setUser(User item, View view) {
        if (view == null) {
            view = View.inflate(getContext(), R.layout.list_chat, null);
            view.setTag(new ViewHolder(view));
        }
        ViewHolder holder = (ViewHolder) view.getTag();

        holder.getTextView(R.id.tv_user).setText(item.username);
        ImageUtil.utils().setUser(holder.getImageView(R.id.iv_user), item, true);
        return view;
    }

    private static class ViewHolder {
        private SparseArray<View> mViews;
        private View itemView;

        ViewHolder(@NonNull View view) {
            itemView = view;
            mViews = new SparseArray<>();
        }

        private View findViewById(int viewId) {
            View view;
            view = mViews.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return view;
        }

        TextView getTextView(int viewId) {
            return (TextView) findViewById(viewId);
        }

        ImageView getImageView(int viewId) {
            return (ImageView) findViewById(viewId);
        }

        public ViewHolder setOnClickListener(View.OnClickListener listener, int... viewId) {
            for (int i : viewId) {
                View view = findViewById(i);
                view.setOnClickListener(listener);
            }
            return this;
        }
    }

    public void search(String key) {
        new AsyncTask<String, Void, Exception>() {

            @Override
            protected Exception doInBackground(String... params) {
                try {
                    switch (type) {
                        case TYPE_QUESTION:
                            mMessages.searchQuestion(params[0]);
                            break;
                        case TYPE_TOPIC:
                            mMessages.searchTopic(params[0]);
                            break;
                        case TYPE_USER:
                            mUsers.searchUsers(params[0]);
                            break;
                        default:
                            throw Exceptions.E_NO_TYPE;
                    }
                } catch (Exception e) {
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception e) {
                if (e != null) {
                    D.getInstance().activeActivity.handleError(e);
                    return;
                }
                mAdapter.notifyDataSetChanged();
            }
        }.execute(key);
    }
}
