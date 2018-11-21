package com.example.jinhui.datastorage.contentprovider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jinhui.datastorage.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jinhui on 2018/3/12.
 * Email:1004260403@qq.com
 * <p>
 * 内容提供者
 * <p>
 * 在清单文件中添加provider标签，
 * <p>
 * 设置authorities属性。该属性为共享数据库uri地址
 * 个人爱好：包名 + 类名
 */

public class ContentProviderActivity extends AppCompatActivity {

    private static final String TAG = "ContentProviderActivity";

    @BindView(R.id.bt_custom_contentprovider)
    Button btCustomContentprovider;
    @BindView(R.id.bt_local_contentprovider)
    Button btLocalContentprovider;
    @BindView(R.id.bt_readcontact)
    Button btReadcontact;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contentprovider);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_custom_contentprovider, R.id.bt_local_contentprovider, R.id.bt_readcontact})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_custom_contentprovider:
                String et_name = etName.getText().toString();
                String et_phone = etPhone.getText().toString();
                if (TextUtils.isEmpty(et_name) || TextUtils.isEmpty(et_phone)){
                    Toast.makeText(this, "名字、年龄不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    // Uri地址："content://package name component name"
                    Uri uri = Uri.parse("content://DataContentProvider");
                    // ContentResolver ----> 内容解析者，可以实现数据库的增删改查
                    ContentResolver resolver = this.getContentResolver();
                    ContentValues values = new ContentValues();
                    values.put("name", et_name);
                    values.put("phone", et_phone);
                    // 插入一条记录
                    resolver.insert(uri, values);
//                Log.e(TAG, "插入数据成功");
                    Toast.makeText(this, "插入数据成功, 插入内容为 " +
                            et_name + "--" +
                            et_phone, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_local_contentprovider:
                // 读取本机联系人
                readContacts();
                break;
            case R.id.bt_readcontact:
                // 查询自定义contentprovider插入的数据
                Uri uri2 = Uri.parse("content://DataContentProvider");
                ContentResolver resolver2 = this.getContentResolver();
                Cursor cursor2 = resolver2.query(uri2, null, null, null, null);
                assert cursor2 != null;
                String name2[] = cursor2.getColumnNames();
                for (int i = 0; i < name2.length; i++) {
                    Log.e(TAG, name2[i]);
                }
                for (cursor2.moveToFirst(); !cursor2.isAfterLast(); cursor2.moveToNext()) {
                    int id = cursor2.getInt(cursor2.getColumnIndex("id"));
                    String name = cursor2.getString(cursor2.getColumnIndex("name"));
                    String phone = cursor2.getString(cursor2.getColumnIndex("phone"));
                    Log.e(TAG, id + " " + name + " " + phone);
                    Toast.makeText(this, id + "--" + name + "--" + phone, Toast.LENGTH_SHORT).show();
                }
                cursor2.close();
                break;
        }
    }

    /**
     * 读取本地联系人，需要添加读取联系人权限
     * E/ContentProviderActivity: name = 朱小超
     * 03-12 12:47:31.949 6684-6684/? E/ContentProviderActivity: name = 爸
     * 03-12 12:47:31.949 6684-6684/? E/ContentProviderActivity: name = 龚代阳
     * 03-12 12:47:31.949 6684-6684/? E/ContentProviderActivity: name = 伍小梅
     * 03-12 12:47:31.949 6684-6684/? E/ContentProviderActivity: name = 张茜↔
     * 03-12 12:47:31.949 6684-6684/? E/ContentProviderActivity: name = 13王璐琪
     * 03-12 12:47:31.949 6684-6684/? E/ContentProviderActivity: name = 龙颜班主任
     * 03-12 12:47:31.949 6684-6684/? E/ContentProviderActivity: name = 张可
     * 03-12 12:47:31.949 6684-6684/? E/ContentProviderActivity: name = 13谭泽鹏
     * 03-12 12:47:31.949 6684-6684/? E/ContentProviderActivity: name = 李思清
     */
    private void readContacts() {
        // 访问系统联系人共享数据库
        //  "content://com.android.contacts"
        Uri uri1 = ContactsContract.Contacts.CONTENT_URI;
        ContentResolver resolver1 = this.getContentResolver();
        // 对联系人数据库进行操作（增删改查）
        Cursor cursor = resolver1.query(uri1, null, null, null, null);
        String names[] = new String[0];
        if (cursor != null) {
            names = cursor.getColumnNames();
        }
        for (String name1 : names) {
            Log.e(TAG, name1);
        }
        // 获取联系人姓名
        assert cursor != null;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("display_name"));
            Log.e(TAG, "获取联系人姓名 name = " + name);
        }
        cursor.close();
    }
}
