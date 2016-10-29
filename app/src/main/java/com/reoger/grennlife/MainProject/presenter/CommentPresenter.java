package com.reoger.grennlife.MainProject.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.reoger.grennlife.MainProject.model.Comment;
import com.reoger.grennlife.MainProject.model.Dynamic;
import com.reoger.grennlife.MainProject.view.ICommentView;
import com.reoger.grennlife.loginMVP.model.UserMode;
import com.reoger.grennlife.utils.log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 24540 on 2016/10/16.
 */
public class CommentPresenter implements ICommentPresenter{

    private ICommentView iCommentView;
    private Context mContext;


    public CommentPresenter(ICommentView iCommentView, Context mContext) {
        this.iCommentView = iCommentView;
        this.mContext = mContext;
    }


    @Override
    public void doPublishAdapter(Dynamic dynamic, String content) {
        showProgressBar();
        UserMode use = BmobUser.getCurrentUser(UserMode.class);
        final Comment comment = new Comment();
        comment.setContent(content);
        comment.setDynamic(dynamic);
        comment.setUser(use);
        comment.setPostAuthor(dynamic.getAuthor().getUsername());
        comment.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null){
                    log.d("TAG","评论成功");
                    dialog.dismiss();
                    iCommentView.onResultPublishComment(true,s);
                }else{
                    dialog.dismiss();
                    iCommentView.onResultPublishComment(false,e.toString());
                }
            }
        });
    }


    //獲取該記錄的評論列表
    @Override
    public void doGetComment(Dynamic dynamic) {
        BmobQuery<Comment> query = new BmobQuery<>();
        log.d("TAG","進入獲取評論的方法");
        log.d("TAE","動態裏面的聶榮是"+dynamic.getContent()+"  :"+dynamic.getObjectId());
        query.addWhereEqualTo("dynamic",new BmobPointer(dynamic));
        //希望同时查询该記錄的发布者的信息，以及该記錄的作者的信息
        query.include("user,dynamic.author");
        query.order("-createdAt");
        query.setLimit(8);
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if(e == null){
                    log.d("TAG","獲取評論成功");
                    for (Comment c :
                            list) {
                        log.d("TAG","品論"+c.getContent());
                    }
                    iCommentView.onResultGetComment(true,list);
                }else{
                    log.d("TAG","獲取評論失敗"+e.toString());
                    iCommentView.onResultGetComment(false,null);
                }
            }
        });
    }
    private ProgressDialog dialog;
    private void showProgressBar(){
        dialog = new ProgressDialog(mContext);
        dialog.setTitle("提示");
        dialog.setMessage("評論中");
        dialog.setCancelable(false);
        dialog.show();

    }
}
