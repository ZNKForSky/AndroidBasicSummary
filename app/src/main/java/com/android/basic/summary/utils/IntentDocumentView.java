package com.android.basic.summary.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.hjq.toast.ToastUtils;

import java.io.File;

/**
 * @author Harris Luffy
 * @version 1.0
 * @des android Intent打开各种类型文件（(PDF、word、excel、ppt、chm）
 * e-mail : 744423651@qq.com
 * @updateDes 2019/9/12 16:54
 */
public class IntentDocumentView {

    public static void openFile(String path, Context mContext) {

        String format = path.substring(path.lastIndexOf(".") + 1);
        File file = new File(path);
        try {
            if (file.exists() /*|| FileUtil.fileAvailable(file)*/) {
                if (TextUtils.equals("doc", format) || TextUtils.equals("docx", format)) {
                    mContext.startActivity(IntentDocumentView.getWordFileIntent(path));
                } else if (TextUtils.equals("xls", format) || TextUtils.equals("xlsx", format)) {
                    mContext.startActivity(IntentDocumentView.getExcelFileIntent(path));
                } else if (TextUtils.equals("zip", format) || TextUtils.equals("rar", format)) {
                    mContext.startActivity(IntentDocumentView.getZipRarFileIntent(path));
                } else if (TextUtils.equals("pdf", format) || TextUtils.equals("PDF", format)) {
                    mContext.startActivity(IntentDocumentView.getPdfFileIntent(path));
                } else if (TextUtils.equals("ppt", format) || TextUtils.equals("PDF", format)) {
                    mContext.startActivity(IntentDocumentView.getPptFileIntent(path));
                }

                {
                    ToastUtils.show("新增文件类型，请联系软件开发商");
                }
            } else {
                ToastUtils.show("请先下载文件");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.show("请先安装可以查看" + format + "格式的软件");
        }
    }

    // android获取一个用于打开PPT文件的intent
    public static Intent getPptFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    // android获取一个用于打开Excel文件的intent
    public static Intent getExcelFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    // android获取一个用于打开Word文件的intent
    public static Intent getWordFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    // android获取一个用于打开CHM文件的intent
    public static Intent getChmFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    // android获取一个用于打开文本文件的intent
    public static Intent getTextFileIntent(String param, boolean paramBoolean) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (paramBoolean) {
            Uri uri1 = Uri.parse(param);
            intent.setDataAndType(uri1, "text/plain");
        } else {
            Uri uri2 = Uri.fromFile(new File(param));
            intent.setDataAndType(uri2, "text/plain");
        }
        return intent;
    }

    // android获取一个用于打开PDF文件的intent
    public static Intent getPdfFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    // android获取一个用于打开图片文件的intent
    public static Intent getPicturefFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    // android获取一个用于打开压缩包的intent （手机需安装能打开压缩文件的相关软件）
    public static Intent getZipRarFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/x-gzip");
        return intent;
    }

}
