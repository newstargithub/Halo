package com.gd.halo.view;

import com.gd.halo.bean.Post;

import java.util.List;

/**
 * Created by zhouxin on 2016/11/7.
 * Description: 博客文章View接口
 */
public interface PostsView extends BaseView{
    void showPosts(List<Post> postsList);
}
