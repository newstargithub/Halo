package com.gd.halo.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gd.halo.util.L;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {

    private static final String TAG = BaseFragment.class.getSimpleName();
    private View rootView;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        L.e(TAG, "onAttach");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        L.e(TAG, "onCreateView");
        rootView = inflater.inflate(layoutResID(), container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        L.e(TAG, "onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        L.e(TAG, "onActivityCreated");
        initViewsAndEvents();
        initData();
    }

    protected abstract int layoutResID();

    protected abstract void initViewsAndEvents();

    protected abstract void initData();

    protected <T> T findViewById(int id) {
        return (T) rootView.findViewById(id);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        L.e(TAG, "onAttach");
    }
}
