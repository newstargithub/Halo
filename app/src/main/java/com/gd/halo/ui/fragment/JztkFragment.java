package com.gd.halo.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.gd.halo.R;
import com.gd.halo.base.GMVPBaseFragment;
import com.gd.halo.bean.Jztk;
import com.gd.halo.bean.data.JztkData;
import com.gd.halo.presenter.JztkPresenter;
import com.gd.halo.util.Constant;
import com.gd.halo.util.CountDownTask;
import com.gd.halo.util.ResUtil;
import com.gd.halo.view.JztkView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhouxin on 2016/11/11.
 * Description:
 * 待做功能：选项样式，指示栏标记答题正确错误未答，时间到答完结果展示，保存答题记录（保存答题时间，错误）
 * 同步所有题目到本地，随机选择未答题目，回顾错误题目
 */
public class JztkFragment extends GMVPBaseFragment<JztkView, JztkPresenter> implements JztkView {
    private static final String[] ITEM_FLAG = {"A", "B", "C", "D"};
    private static final int[] ITEM_ID = {0, 1, 2, 3};
    private static final String[] ITEM_VALUE = {"1", "2", "3", "4"};
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.tv_explains)
    TextView tvExplains;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.bt_previous)
    Button btPrevious;
    @BindView(R.id.bt_next)
    Button btNext;
    @BindView(R.id.tv_question)
    TextView tvQuestion;
    @BindView(R.id.iv_url)
    ImageView ivUrl;
    @BindView(R.id.rg_answer)
    RadioGroup rgAnswer;
    @BindView(R.id.tv_time)
    TextView tvTime;
    private int currentItem = -1;
    private List<Jztk> mList;
    private Jztk item;
    private float mScreenDensity;
    private int mScreenHeight;
    private int mScreenWidth;
    private JztkListAdapter mAdapter;
    private CountDownTask mCountDownTask;

    @Override
    protected JztkPresenter createPresenter() {
        return new JztkPresenter();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_jztk;
    }

    @Override
    protected void initViewsAndEvents() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        mScreenDensity = displayMetrics.density;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void SimpleOnItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                setCurrentItem(position);
            }
        });
        mAdapter = new JztkListAdapter(null);
        recyclerView.setAdapter(mAdapter);
        initData();
    }

    @Override
    protected void initData() {
        mPresenter.getRandJztk(JztkData.SUBJECT_1, JztkData.MODEL_C1);
    }

    @Override
    protected View getLoadingTargetView() {
        return findViewById(R.id.ll_content);
    }

    @Override
    protected boolean isRegisterEvent() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.bt_previous, R.id.bt_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_previous:
                postCurrentItemAndSetPosition(currentItem - 1);
                break;
            case R.id.bt_next:
                postCurrentItemAndSetPosition(currentItem + 1);
                break;
        }
    }

    private void postCurrentItemAndSetPosition(int position) {
        if (TextUtils.isEmpty(item.myAnswer)) {
            int id = rgAnswer.getCheckedRadioButtonId();
            if (id != -1) {
                item.checkPosition = id;
                item.myAnswer = ITEM_VALUE[id];
            }
        }
        setCurrentItem(position);
    }

    private void setCurrentItem(int position) {
        if (position >= 0 && mList != null && position < mList.size()) {
            if (currentItem != position) {
                currentItem = position;
                item = mList.get(currentItem);
                setItemUI();
            }
        }
    }

    private void setItemUI() {
        StringBuilder sb = new StringBuilder();
        sb.append("(").
                append(currentItem + 1)
                .append("/")
                .append(mList.size())
                .append(") ")
                .append(item.getQuestion());
        tvQuestion.setText(sb.toString());
        Glide.with(mContext)
                .load(item.getUrl())
                .fitCenter()
                .into(ivUrl);
        rgAnswer.removeAllViews();
        List<String> list = getAnswers(item);
        for (int i = 0; i < list.size(); i++) {
            addRadioButton(rgAnswer, ITEM_ID[i], TextUtils.concat(ITEM_FLAG[i], ".", list.get(i)).toString());
        }
        rgAnswer.clearCheck();
        boolean hasAnswer = !TextUtils.isEmpty(item.myAnswer);
        if (hasAnswer) {
//            rgAnswer.setEnabled(false);
            rgAnswer.check(item.checkPosition);
            /*与rgAnswer.check(id);一样
            RadioButton rb = findViewById(item.checkPosition);
            rb.setChecked(true);*/
            tvResult.setVisibility(View.VISIBLE);
            tvExplains.setVisibility(View.VISIBLE);
            tvExplains.setText(item.getExplains());
            boolean isRight = item.getAnswer().equals(item.myAnswer);
            String result;
            if (isRight) {
                result = "回答正确";
            } else {
                result = "回答错误";
            }
            //改变字体颜色
            //先构造SpannableString
            SpannableStringBuilder spanString = new SpannableStringBuilder(result);
            //再构造一个改变字体颜色的Span
            ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
            //将这个Span应用于指定范围的字体
            spanString.setSpan(span, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanString.append("；");
            SpannableStringBuilder subString = new SpannableStringBuilder("您的答案是" + ITEM_FLAG[item.checkPosition]);
            subString.setSpan(new ForegroundColorSpan(Color.GREEN), subString.length() - 1, subString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            subString.append("；");
            int position = Arrays.asList(ITEM_VALUE).indexOf(item.getAnswer());
            SpannableStringBuilder subString2 = new SpannableStringBuilder("正确答案是" + ITEM_FLAG[position]);
            subString2.setSpan(new ForegroundColorSpan(Color.RED), subString2.length() - 1, subString2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            subString2.append("。");
            spanString.append(subString);
            spanString.append(subString2);
            tvResult.setText(spanString);
        } else {
//            rgAnswer.setEnabled(true);
            tvResult.setVisibility(View.GONE);
            tvExplains.setVisibility(View.GONE);
        }
        int count = mList.size();
        boolean hasPrevious = currentItem > 0;
        btPrevious.setEnabled(hasPrevious);
        boolean hasNext = currentItem != count && count > 1;
        btNext.setEnabled(hasNext);
    }

    private List<String> getAnswers(Jztk item) {
        List<String> list = new ArrayList<>();
        list.add(item.getItem1());
        list.add(item.getItem2());
        if (!TextUtils.isEmpty(item.getItem3())) {
            list.add(item.getItem3());
        }
        if (!TextUtils.isEmpty(item.getItem4())) {
            list.add(item.getItem4());
        }
        return list;
    }

    private void addRadioButton(RadioGroup radioGroup, int id, String flag) {
        RadioButton rb = (RadioButton) LayoutInflater.from(mContext).inflate(R.layout.item_jktk_radiobutton, radioGroup, false);
        rb.setText(flag);
        rb.setId(id);
        radioGroup.addView(rb);
    }

    @Override
    public void showData(List<Jztk> list) {
        mList = list;
        mAdapter.setNewData(list);
        mCountDownTask = CountDownTask.create();
        startCountDown(60 * 60 * 1000, tvTime);
        setCurrentItem(0);
    }

    private void startCountDown(long countDownTime, final TextView textView) {
        if (mCountDownTask != null) {
            CountDownTask.IntervalTask task = mCountDownTask.getIntervalTask(Constant.COUNT_DOWN_INTERVAL);
            task.remove(textView);
            if (countDownTime <= 0) {
                //超过倒计时
                textView.setText(R.string.has_timed_out);
            } else {
                //加入倒计时(信息：计时的文本，计时时间，计时间隔)
                task.add(textView, countDownTime, new CountDownTask.OnCountDownListener() {
                    @Override
                    public void onTick(View view, long millisUntilFinished) {
                        String format = ResUtil.getString(R.string.count_down_time, CountDownTask.transferToTime(millisUntilFinished / 1000));
                        textView.setText(format);
                    }

                    @Override
                    public void onFinish(View view) {
                        textView.setText(R.string.has_timed_out);
                    }
                });
            }
        }
    }

    class JztkListAdapter extends BaseQuickAdapter<Jztk> {

        public JztkListAdapter(List<Jztk> data) {
            super(R.layout.item_index, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, Jztk item) {
            int position = baseViewHolder.getAdapterPosition();
            String text = String.valueOf(position + 1);
            baseViewHolder.setText(R.id.text1, text)
                    .addOnClickListener(R.id.text1);
        }
    }
}
